package com.scm.arjun.scm20.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.scm.arjun.scm20.entities.PasswordResetToken;
import com.scm.arjun.scm20.repositories.PasswordResetTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.scm.arjun.scm20.entities.User;
import com.scm.arjun.scm20.exceptions.ResourceNotFoundException;
import com.scm.arjun.scm20.repositories.UserRepo;
import com.scm.arjun.scm20.services.UserServices;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserServices {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);

    }

    @Override
    public Optional<User> getUserByUserId(String id) {
     return userRepo.findById(id);

    }

    @Override
    public Optional<User> updateUser(User user) {
      
      User optionalUser =  userRepo.findById(user.getUserId()).orElseThrow(ResourceNotFoundException::new);
      optionalUser.setName(user.getName());
      optionalUser.setEmail(user.getEmail());
      optionalUser.setPassword(user.getPassword());
      optionalUser.setAbout(user.getAbout());
      optionalUser.setPhoneNumber(user.getPhoneNumber());
      optionalUser.setProfilePic(user.getProfilePic());
      optionalUser.setEnable(user.isEnable());
      optionalUser.setEmailVerified(user.isEmailVerified());
      optionalUser.setPhoneNumberVerified(user.isPhoneNumberVerified());
      optionalUser.setProviderUserId(user.getProviderUserId());

      User userSaved =userRepo.save(optionalUser);
      return Optional.of(userSaved);
      
    }

    @Override
    public void deleteUser(String id) {
     User optionalUser =
             userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
     userRepo.delete(optionalUser);

    }

    @Override
    public Optional<User> isUserExistByEmail(String emailId) {
       return userRepo.findByEmail(emailId);

    }

    @Override
    public boolean isUserExistByUserId(String userId) {
        User user =  userRepo.findById(userId).orElse(null);
        return user!= null ? true : false;

    }

    @Override
    public List<User> getAllUser() {
        return  userRepo.findAll();
    }

    @Override
    public void createPwdResetToken(User user, String token){
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }
    @Override
    public void updatePassword(User user, String newPassword ){
        user.setPassword(newPassword);
        userRepo.save(user);
    }

    @Override
    public Optional<User> findByPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token).map(PasswordResetToken::getUser);
    }


}
