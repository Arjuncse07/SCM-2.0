package com.scm.arjun.scm20.services;

import java.util.List;
import java.util.Optional;

import com.scm.arjun.scm20.entities.User;
import org.springframework.stereotype.Service;



public interface UserServices {

    User saveUser(User user);
    Optional<User> getUserByUserId(String id);

    Optional<User> getUserByEmail(String email);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    Optional<User> isUserExistByEmail(String emailId);
    boolean isUserExistByUserId(String userId);
    List<User> getAllUser();

    void createPwdResetToken(User user, String token);

    void updatePassword(User user, String newPassword);

    Optional<User> findByPasswordResetToken(String token);

    Optional<User> findByUsername(String userName);

    boolean areEmailsAndPhoneNumbersUnique(List<String> emails, List<String> phoneNumbers);



}
