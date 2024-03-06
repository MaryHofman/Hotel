package com.example.demo.enteies;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table(name="extras")
public class Extras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private Hotel hotelId;

    @Column(name = "string_array", columnDefinition = "text[]")
    private String[] stringArray;

}
