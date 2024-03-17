package com.example.demo.reposytories;




import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enteies.Extras;
import com.example.demo.enteies.Hotel;

@Repository
public interface ExtrasRepository extends CrudRepository<Extras, Long> {

    Extras findByHotelId(Hotel hotel);
    void deleteByHotelId(Hotel hotel);
}
