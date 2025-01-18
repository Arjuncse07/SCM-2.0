package com.scm.arjun.scm20.config;

import com.scm.arjun.scm20.entities.User;
import com.scm.arjun.scm20.repositories.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    /**
     * author: Arjun
     * This class will log the login user IP Address and User details on every login.
     */


    private final HttpServletRequest request;
    private final UserRepo userRepo;

    public LoginSuccessListener(HttpServletRequest request, UserRepo userRepo) {
        this.request = request;
        this.userRepo = userRepo;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        String email = "";

        if (authentication.getPrincipal() instanceof UserDetails) {
            email = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            email = authentication.getPrincipal().toString();
        }

        String ipAddress = request.getRemoteAddr();
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String userId = user.getUserId();
            System.out.println("UserID:: " + userId + " Logged in User IP Address:: " + ipAddress);
        } else {
            System.out.println("Login attempt with email: " + email + "failed - User not found!! ");
        }
    }
}
