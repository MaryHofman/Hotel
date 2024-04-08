package com.example.demo.services;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.Coordinates;
import com.example.demo.DTO.HotelCard;
import com.example.demo.DTO.HotelCoordinates;
import com.example.demo.DTO.HotelDTO;
import com.example.demo.DTO.InformationAboutHotel;
import com.example.demo.DTO.MainInformationAboutHotel;
import com.example.demo.DTO.PartsOfArticle;
import com.example.demo.DTO.RoomDTO;
import com.example.demo.configurations.JWTprovider;
import com.example.demo.enteies.Extras;
import com.example.demo.enteies.Hotel;
import com.example.demo.enteies.Room;
import com.example.demo.enteies.Users;
import com.example.demo.reposytories.HotelRepository;
import com.example.demo.utils.ImageUtil;


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

    public HotelCard convertToHotelCard(Hotel hotel) {
        HotelCard hotelCard = new HotelCard();
        hotelCard.setId(hotel.getHotelId());
        hotelCard.setName(hotel.getName());
        hotelCard.setAddress(hotel.getAddress());
        hotelCard.setImgUrl(hotel.getImgUrl());
        hotelCard.setPrice(hotel.getPrice());   
        Long countRating = hotel.getRatingsCount();
        Long totalRating = hotel.getTotalRating();  
    
        double rating = 0.0;
        if (countRating != null && totalRating != null && countRating != 0) {
            rating = ((double) totalRating / countRating);
        }
    
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
        hotel.setTotalRating(Long.valueOf(0));
        hotel.setRatingsCount(Long.valueOf(0));
        hotel.setPrice(informationAboutHotel.getRooms().get(0).getPrice());
    
        System.out.println("Token " + jwtToken);
        
        String email = jwTprovider.getAccessClaims(jwtToken).get("email").toString();
        Users user = userService.findByUsername(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    
        hotel.setUserId(user.getId());
        String mainPath = "/img/hotelIMG";

        if (informationAboutHotel.getMainImg()!= null) {

            String mainURL = imageUtil.saveMainImage(informationAboutHotel.getMainImg());
            hotel.setImgUrl(mainURL);
        }
        hotelRepository.save(hotel);
        Long hotelId = hotel.getHotelId();
    
        // Сохранение дополнительных изображений, если они не null
        
        if (informationAboutHotel.getAllImgs() != null) {
            for (String file : informationAboutHotel.getAllImgs()) {
                String url = imageUtil.saveAllImage(file);
                hotelImageService.saveImg(hotelId, url);
            }
        }
    
        // Сохранение информации о дополнительных услугах
        if (informationAboutHotel.getExtras() != null) {

            Extras extras = new Extras();
            extras.setHotelId(hotel);
            List<String> extrasList = Arrays.asList(informationAboutHotel.getExtras());
            extras.setStringArray(extrasList);
            extrasService.saveExtras(extras);
        }
    

        
    
        // Сохранение информации о номерах
        if (informationAboutHotel.getRooms() != null) {
            for (RoomDTO r : informationAboutHotel.getRooms()) {
                Room room = new Room();
                room.setPrice(r.getPrice());
                room.setDescription(r.getDescription());
                room.setRoomType(r.getRoomType());
                room.setHotelId(hotel);
                roomService.saveRoom(room);
            }
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

    
    

    public List<HotelCard> takeTheNearest(Coordinates geo) throws IOException {
        
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

    @Transactional
    public ResponseEntity<?> deleteInformationAboutHotel(Long id_hotel, String jwtToken) throws IOException {
        hotelImageService.deleteImg(id_hotel);
        Hotel hotel=hotelRepository.findById(id_hotel).get();
        imageUtil.deleteImage("/img/hotelIMG", hotel.getImgUrl());
        hotelRepository.deleteById(id_hotel);
        
       
        return ResponseEntity.ok("Запись об отеле удалена");
    }

    public Hotel findHotelById(Long hotelId) {
        Hotel hotel=hotelRepository.findById(hotelId).get();
        return hotel;
    }

    public float changeInformationAboutRaiting(Long hotelId, long l, long m) {
            hotelRepository.updateHotelRatingsById(hotelId, l, m);
            return m/l+m%l;
    }

    public MainInformationAboutHotel getHotelById(Long id) {
        MainInformationAboutHotel information = new MainInformationAboutHotel();
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            List<Room> rooms = roomService.findAllByHotelId(hotel);
                
    
            // // Проверка на null перед установкой комнат
             if (rooms != null && !rooms.isEmpty()) {
                 information.setRooms(rooms);
             }
            List<String> imgsURL = hotelImageService.getAllImagesURL(id);
            Coordinates geo = new Coordinates();
            geo.setLatitude(hotel.getLatitude());
            geo.setLongitude(hotel.getLongitude());
    
           // information.setRooms(null);
            information.setId(hotel.getHotelId());
            information.setName(hotel.getName());
            information.setDescription(hotel.getDescription());
            information.setAddress(hotel.getAddress());
            information.setMainImgURL(hotel.getImgUrl());
            information.setAllImgsURL(imgsURL);
            information.setGeo(geo);
            
            List<String> extras = extrasService.getAllByHotelId(hotel);
            // Проверка на null перед установкой дополнительных услуг
            if (extras != null && !extras.isEmpty()) {
                information.setExtras(extras);
            } else {
                information.setExtras(new ArrayList<>());
            }

            List<String> AllUrl=hotelImageService.getAllImagesURL(id);
            System.out.println("Строки");
            if( AllUrl!=null){
            for(String s: AllUrl){
                System.out.println(s);
            }}
            information.setAllImgsURL(AllUrl);
        }
        return information;
    }
    

    public List<HotelCard> takeTheBest() {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        Map<Long, Hotel> mapOfHotels = new HashMap<>();
    
        for (Hotel h : hotels) {
            Long totalRating = h.getTotalRating();
            Long ratingsCount = h.getRatingsCount();
            if (totalRating != null && ratingsCount != null && ratingsCount != 0) {
                mapOfHotels.put((totalRating / ratingsCount + totalRating % ratingsCount), h);
            }
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

    public List<HotelCard> getHotelByUserId(Long id) {
        List<Hotel> hotels = hotelRepository.findHotelsByUserId(id);
        List<HotelCard> hotelsCard = new ArrayList<>();

        for (int i = 0; i < hotels.size(); i++) {
            hotelsCard.add(convertToHotelCard(hotels.get(i)));
        }
       

        return hotelsCard;
    }


    public List<HotelCard> listHotelsOfUser(String token) {
        String email = jwTprovider.getAccessClaims(token).get("email").toString();
        Users user = userService.findByUsername(email).get();
        List<Hotel> hotels = hotelRepository.findHotelsByUserId(user.getId());
        List<HotelCard> hotelsCard = new ArrayList<>();
        for (int i = 0; i < hotels.size(); i++) {
            hotelsCard.add(convertToHotelCard(hotels.get(i)));
        }
        return hotelsCard;
    }
    
}
