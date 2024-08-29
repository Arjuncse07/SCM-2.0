package com.scm.arjun.scm20.controller;

import com.scm.arjun.scm20.entities.User;
import com.scm.arjun.scm20.services.EmailService;
import com.scm.arjun.scm20.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
public class PasswordResetController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private EmailService emailService;

    @RequestMapping("/forgotPassword")
    public String showForgotPasswordPage() {
        System.out.println("Forgot Password Controller");
        return "login/forgetPwdPage";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        try {
        Optional<User> userOptional = userServices.isUserExistByEmail(email);
        if (!userOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "Email Address not found");
            return "redirect:/forgotPassword";
        }

        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        userServices.createPwdResetToken(user, token);

        String resetLink = "http://localhost:8081/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(user.getEmail(), resetLink);

        redirectAttributes.addFlashAttribute("message", "Password reset link has been sent to registered email.");
        return "redirect:/forgotPassword";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/forgotPassword";
    }


}
