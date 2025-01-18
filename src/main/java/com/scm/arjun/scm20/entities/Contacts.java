package com.scm.arjun.scm20.entities;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity(name ="CONTACTS")
public class Contacts {

    @Id
    private String contactId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    private String description;
    private boolean favorite = false;

    //private List<Contacts> contacts = new ArrayList<>();

    @ManyToOne
    private User user;


    @OneToMany(mappedBy = "contact",
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER)
    private List<SocialLink> socialLinks = new ArrayList<>();
 
}
