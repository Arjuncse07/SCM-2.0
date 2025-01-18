package com.scm.arjun.scm20.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

@Entity
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)  //Map the fields to the DB based on date and time
    private Date expiryDate;

    //Default Constructor
    public PasswordResetToken(){}

    public PasswordResetToken(String token, User user){
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(60 * 24); //Expiry time: 24 hours
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes){
        return new Date(System.currentTimeMillis() + expiryTimeInMinutes * 60 * 1000);
    }

}
