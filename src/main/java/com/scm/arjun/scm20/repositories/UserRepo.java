package com.scm.arjun.scm20.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.arjun.scm20.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByName(String userName);

    @Query("SELECT u FROM USER u WHERE u.email IN :emails OR u.phoneNumber IN :phoneNumbers")
    List<User> findByEmailInOrPhoneNumberIn(@Param("emails") List<String> emails, @Param("phoneNumbers") List<String> phoneNumbers );

}
