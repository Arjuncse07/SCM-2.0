package com.scm.arjun.scm20.entities;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.scm.arjun.scm20.utils.PasswordUtils;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Entity(name = "USER")
@Table(name = "USER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.scm.arjun.scm20.utils.CustomIdGenerator")
    @Column(name = "userId", updatable = false, nullable = false, length = 8)
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String gender;

    @Column(length = 1000)
    private String about;

    @Column(length = 1000)
    private String profilePic;  // not user will be used


    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean enable = false;
    private boolean emailVerified = false;
    private boolean phoneNumberVerified = false;
    private LocalDateTime creationTime;

    //SignUp >> Google, Facebook, Tweet
    @Enumerated(value = EnumType.STRING)   //enables user to update the string enum in database
    private Providers provider = Providers.SELF;
    private String providerUserId;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Contacts> contacts = new ArrayList<>();


    @PrePersist
    @PreUpdate
    private void encryptPassword() {
        //hash the password before saving or updating the entity
        this.password = PasswordUtils.hashPassword(this.password);

        if (this.creationTime == null) {
            this.creationTime = LocalDateTime.now();
        }
    }

}
