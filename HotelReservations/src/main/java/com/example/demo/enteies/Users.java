package com.example.demo.enteies;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="firstname")
    private String firstName;

    @Column(name="secondname")
    private String secondName;

    @Column(name="password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    @Column(name="email")
    private String email;

    @Column(name="imgURL")
    private String imgURL;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Favourite> favourites;
    

    
}
