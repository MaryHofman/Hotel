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
import com.example.demo.DTO.HotelCoordinates;
import com.example.demo.DTO.HotelDTO;
import com.example.demo.DTO.InformationAboutHotel;
import com.example.demo.DTO.MainInformationAboutHotel;
import com.example.demo.DTO.PartsOfArticle;
import com.example.demo.configurations.JWTprovider;
import com.example.demo.enteies.Extras;
import com.example.demo.enteies.Hotel;
import com.example.demo.enteies.Room;
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
    private RoomService roomService;
    
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
        hotelCard.setId(hotel.getHotelId());
        hotelCard.setName(hotel.getName());
        hotelCard.setAddress(hotel.getAddress());
        hotelCard.setImgUrl(hotel.getImgUrl());
        hotelCard.setPrice(hotel.getPrice());   
        Long countRating = hotel.getRatingsCount();
        Long totalRating = hotel.getTotalRating();  
        Double rating = ((double) totalRating / countRating)+(totalRating % countRating);
        hotelCard.setRating(rating);
        hotelCard.setLatitude(hotel.getLatitude());
        hotelCard.setLongitude(hotel.getLongitude());
        return hotelCard;
    }


    public ResponseEntity<String> createCard(InformationAboutHotel informationAboutHotel, String jwtToken) throws IOException {
        Hotel hotel = new Hotel();
        hotel.setName(informationAboutHotel.getName());
        hotel.setAddress(informationAboutHotel.getAddress());
        hotel.setDescription(informationAboutHotel.getDescription());
        hotel.setLatitude(informationAboutHotel.getGeo().getLatitude());
        hotel.setLongitude(informationAboutHotel.getGeo().getLongitude());

        String email = jwTprovider.getAccessClaims(jwtToken).get("firstName").toString();
        Users user=userService.findByUsername(email).get();

        hotel.setUserId(user.getId());
        String mainPath="/img/hotelIMG";
        String mainURL= imageUtil.saveImage(informationAboutHotel.getMainImg(),mainPath);

        hotel.setImgUrl(mainURL);

        for(MultipartFile file: informationAboutHotel.getAllImgs()){
            String url=imageUtil.saveImage(file, mainPath);
            hotelImageService.saveImg(hotel.getHotelId(), url);
        }

        Extras extras=new Extras();
        extras.setHotelId(hotel.getHotelId());
        extras.setStringArray(informationAboutHotel.getExtras());
        extrasService.saveExtras(extras);
        List <Room> rooms=informationAboutHotel.getRooms();
        for(Room r:rooms){
            roomService.saveRoom(r);
        }

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
        
        List<HotelCoordinates> coordinates=hotelRepository.findLatitudeAndLongitudeById();
        
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
        roomService.deleteRoom(id_hotel);
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

    public MainInformationAboutHotel getHotelById(Long id) {
        MainInformationAboutHotel information = new MainInformationAboutHotel();
        Hotel hotel= hotelRepository.findById(id).get();
        List<Room> roms=roomService.findAllByHotelId(id);
        String[] extra=extrasService.getAllByHotelId(id);
        List<String> imgsURL=hotelImageService.getAllImagesURL(id);
        GeoIP geo=new GeoIP();
        geo.setLatitude(hotel.getLatitude());
        geo.setLongitude(hotel.getLongitude());

        information.setName(hotel.getName());
        information.setDescription(hotel.getDescription());
        information.setAddress(hotel.getAddress());
        information.setMainImgURL(hotel.getImgUrl());
        information.setAllImgsURL(imgsURL);
        information.setRooms(roms);
        information.setGeo(geo);
        information.setExtras(extra);
        return information;
    }

    public List<HotelCard> takeTheBest() {
        Iterable <Hotel> hotels=hotelRepository.findAll();
        Map<Long,Hotel> mapOfHotels=new HashMap<>();

        for( Hotel h: hotels){
            mapOfHotels.put((h.getTotalRating()/h.getRatingsCount()+h.getTotalRating()%h.getRatingsCount()), h);
        }

    Map<Long, Hotel> sortedByRating = mapOfHotels.entrySet().stream()
    .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())) 
    .limit(10) 
    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    List<HotelCard> hotelCard = new ArrayList<>();

    for (Map.Entry<Long, Hotel> entry : sortedByRating.entrySet()) {
        Hotel hotel = entry.getValue();
        HotelCard card = convertToHotelCard(hotel);
        hotelCard.add(card);
    }

        return hotelCard;
    }

    
}
