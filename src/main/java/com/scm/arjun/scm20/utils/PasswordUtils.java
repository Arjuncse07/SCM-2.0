package com.scm.arjun.scm20.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

   //Method to verify the raw password against the hashed password
   public static boolean verifyPassword(String rawPassword, String hashedPassword){
        return passwordEncoder.matches(rawPassword,hashedPassword);
   }
}
