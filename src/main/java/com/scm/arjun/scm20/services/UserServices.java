package com.scm.arjun.scm20.services;

import java.util.List;
import java.util.Optional;

import com.scm.arjun.scm20.entities.User;
import org.springframework.stereotype.Service;



public interface UserServices {

    User saveUser(User user);
    Optional<User> getUserByUserId(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExistByEmail(String emailId);
    boolean isUserExistByUserId(String userId);
    List<User> getAllUser();




}
