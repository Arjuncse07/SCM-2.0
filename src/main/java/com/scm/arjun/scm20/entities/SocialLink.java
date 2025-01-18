package com.scm.arjun.scm20.entities;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity(name = "social_link")
@Table(name = "SOCIAL_LINK")
public class SocialLink {

    @Id
    private Long Id;
    private String link;
    private String title;

    @ManyToOne
    private Contacts contact;


}
