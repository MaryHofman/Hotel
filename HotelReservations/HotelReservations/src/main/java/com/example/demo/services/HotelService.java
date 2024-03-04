package com.example.demo.services;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.GeoIP;
import com.example.demo.DTO.HotelCard;
import com.example.demo.DTO.HotelDTO;
import com.example.demo.DTO.InformationAboutHotel;
import com.example.demo.DTO.PartsOfArticle;
import com.example.demo.configurations.JWTprovider;
import com.example.demo.enteies.Extras;
import com.example.demo.enteies.Hotel;
import com.example.demo.enteies.HotelCoordinates;
import com.example.demo.enteies.Users;
import com.example.demo.reposytories.HotelRepository;
import com.example.demo.utils.ImageUtil;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class HotelService {

    @Autowired
    private  JWTprovider jwTprovider;
    @Autowired
    private UserService userService;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ImageUtil imageUtil;
    @Autowired
    private HotelImageService hotelImageService;
    @Autowired
    private ExtrasService extrasService;
    @Autowired
    private HotelCoordinatesService hotelCoordinatesService;
    
    private static final double EARTH_RADIUS = 6371.0;
   


    @Transactional
    public List<HotelCard> GetInformationAboutAllHotels() {
        Iterable<Hotel> hotelsIterable = hotelRepository.findAll(); 
        List<Hotel> hotels = new ArrayList<>();
        hotelsIterable.forEach(hotels::add);
        return hotels.stream().map(this::convertToHotelCard).collect(Collectors.toList());
    }

    private HotelCard convertToHotelCard(Hotel hotel) {
        HotelCard hotelCard = new HotelCard();
        hotelCard.setId(hotel.getHotel_id());
        hotelCard.setName(hotel.getName());
        hotelCard.setAddress(hotel.getAddress());
        hotelCard.setImgUrl(hotel.getImgUrl());
        hotelCard.setPrice(hotel.getPrice());
        
        Long countRating = hotel.getRatings_count();
        Long totalRating = hotel.getTotal_rating();
        
        Double rating = ((double) totalRating / countRating)+(totalRating % countRating);
        hotelCard.setRating(rating);
        hotelCard.setGeography(hotel.getGeography());
    
        return hotelCard;
    }


    public ResponseEntity<String> createCard(InformationAboutHotel informationAboutHotel, String jwtToken) throws IOException {
        Hotel hotel = new Hotel();
        hotel.setName(informationAboutHotel.getName());
        hotel.setAddress(informationAboutHotel.getAddress());
        hotel.setDescription(informationAboutHotel.getDescription());
        hotel.setGeography(informationAboutHotel.getGeography());
        hotel.setPrice(informationAboutHotel.getPrice());

        HotelCoordinates coordinates=new HotelCoordinates();
        coordinates.setHotelId(hotel.getHotel_id());
        coordinates.setLatitude(informationAboutHotel.getGeo().getLatitude());
        coordinates.setLongitude(informationAboutHotel.getGeo().getLongitude());

        String email = jwTprovider.getAccessClaims(jwtToken).get("firstName").toString();
        Users user=userService.findByUsername(email).get();

        hotel.setUser_id(user.getId());
        String mainPath="/img/hotelIMG";
        String mainURL= imageUtil.saveImage(informationAboutHotel.getMainImg(),mainPath);

        hotel.setImgUrl(mainURL);

        for(MultipartFile file: informationAboutHotel.getAllImgs()){
            String url=imageUtil.saveImage(file, mainPath);
            hotelImageService.saveImg(hotel.getHotel_id(), url);
        }

        Extras extras=new Extras();
        extras.setHotelId(hotel.getHotel_id());
        extras.setStringArray(informationAboutHotel.getExtras());

        extrasService.saveExtras(extras);

        hotelCoordinatesService.save(coordinates);


        return ResponseEntity.ok("Данные об отеле успешно сохранены!");
    }


     private static double toRadians(double degrees) {
        return degrees * Math.PI / 180.0;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        lat1 = toRadians(lat1);
        lon1 = toRadians(lon1);

        lat2 = toRadians(lat2);
        lon2 = toRadians(lon2);

        double dLon = lon2 - lon1;
        double dLat = lat2 - lat1;

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance;
    }

    
    

    public List<HotelCard> takeTheNearest(GeoIP geo, HttpServletRequest request) throws IOException, GeoIp2Exception {
        List<HotelCoordinates> coordinates = hotelCoordinatesService.getAllCoordinatesOfHotels();
        Map<Long, Double> mapCoordinates=new HashMap<>();
        
        for(HotelCoordinates c: coordinates){
            double distance = calculateDistance(geo.getLatitude(), geo.getLongitude(), c.getLatitude(), c.getLongitude());
            mapCoordinates.put(c.getHotelId(), distance);       
        }

        Map<Long, Double> sortedMap = sortByValueAscending(mapCoordinates);

        List<HotelCard> hotelCards = sortedMap.entrySet()
            .stream()
            .limit(10)
            .map(entry -> {
                Hotel hotel = hotelRepository.findById(entry.getKey()).get();
                return convertToHotelCard(hotel);
            })
            .collect(Collectors.toList());

        return hotelCards;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueAscending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        Map<K,V> result = new LinkedHashMap<>();
        for (Map.Entry<K,V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public ResponseEntity<?> deleteInformationAboutHotel(Long id_hotel, String jwtToken) throws IOException {
        hotelImageService.deleteImg(id_hotel);
        Hotel hotel=hotelRepository.findById(id_hotel).get();
        imageUtil.deleteImage("/img/hotelIMG", hotel.getImgUrl());
        hotelRepository.delete(hotel);
        return ResponseEntity.ok("Запись об отеле удалена");
    }

    public Hotel findHotelById(Long hotelId) {
        Hotel hotel=hotelRepository.findById(hotelId).get();
        return hotel;
    }

    public float changeInformationAboutRaiting(Long hotelId, long l, long m) {
            hotelRepository.updateHotelRaitingsById(hotelId, l, m);
            return m/l+m%l;
    }


    
}
