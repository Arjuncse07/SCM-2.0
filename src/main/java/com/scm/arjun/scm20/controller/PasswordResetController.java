package com.scm.arjun.scm20.controller;

import com.scm.arjun.scm20.entities.User;
import com.scm.arjun.scm20.services.EmailService;
import com.scm.arjun.scm20.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/forgotPassword";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        Optional<User> userOptional = userServices.findByPasswordResetToken(token);
        if (!userOptional.isPresent()) {
            model.addAttribute("message", "Invalid or expired password reset token.");
            return "login/forgetPwdPage";
        }

        model.addAttribute("token", token);

        return "login/resetPassword";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("token") String token,
                                      @RequestParam("password") String password,
                                      @RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("message", "Passwords do not match.");
            return "redirect:/reset-password?token=" + token;
        }

        Optional<User> userOptional = userServices.findByPasswordResetToken(token);
        if (!userOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "Invalid and expired token.");
            return "redirect:/reset-password";
        }

        User user = userOptional.get();
        userServices.updatePassword(user, password);
        redirectAttributes.addFlashAttribute("message", "Password reset successfully.You can now login");
        return "redirect:/login";
    }

}
