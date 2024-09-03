package com.scm.arjun.scm20.controller;

import com.scm.arjun.scm20.entities.User;
import com.scm.arjun.scm20.exceptions.DuplicateUserException;
import com.scm.arjun.scm20.forms.LoginForm;
import com.scm.arjun.scm20.repositories.UserRepo;
import com.scm.arjun.scm20.services.CaptchService;
import com.scm.arjun.scm20.services.UserServices;
import com.scm.arjun.scm20.utils.PasswordUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.scm.arjun.scm20.forms.SignUpForm;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.model.IModel;

import javax.swing.text.html.Option;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Calendar;
import java.util.Optional;

@Controller
public class PageController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private CaptchService captchService;


    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @PostMapping("/do-login")
    public String havingLogin(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                              @RequestParam(name = "g-recaptcha-response") String recaptchaResponse,
                              RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Login Error");
            return "redirect:/login";
        }

        boolean isCaptchaValid = captchService.verifyCaptcha(recaptchaResponse);
        if (!isCaptchaValid) {
            redirectAttributes.addFlashAttribute("error", "Login error");
            return "redirect:/login";
        }

        String email = loginForm.getEmail();
        String rawPassword = loginForm.getPassword();
        Optional<User> userOptional = userServices.getUserByEmail(email);

        if (!userOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "User not found!!");
        }

        User user = userOptional.get();

        if (!PasswordUtils.verifyPassword(rawPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials!!");
            return "redirect:/login";
        }

        System.out.println("Inside login::::::::::::::::::::");
        System.out.println("User ID :: " + user.getUserId() + " IP Address:: " + request.getRemoteAddr() + " Date Time:: "+ Instant.now());

        return "redirect:/home";
    }


    @RequestMapping("/forgetPassword")
    public String forgetPage(Model model){

        return "forgetPwdPage";
    }


    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("name", "This is testing name");
        model.addAttribute("text", " abc testing text");
        System.out.println("On the home page >>>>>>");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("isLogin",true);
        model.addAttribute("aboutUs", "Demo");
        System.out.println("Under Services Page");
        return "about";
    }

    @RequestMapping("/services")
    public String services(){  
        System.out.println("Under Services Page");
        return "services";
    }


    @GetMapping("/contact")
    public String contact(){
        System.out.println("Contact Html Page");
        return "contact";
    }


    @GetMapping("/register")
    public String register(Model model) {
        try {
            System.out.println("Register Controller");
            // Binding an empty Form Object
            model.addAttribute("userForm", new SignUpForm());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "login/signUp";
    }

    //  -------------------------------------------- REGISTER PAGE --------------------------------------------------------------------------------
    @PostMapping("/do-register")
    public String registerProcess(@Valid @ModelAttribute("userForm") SignUpForm userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "redirect:/register";
        }

        try {
            User user = User.builder()
                    .name(userForm.getName())
                    .password(userForm.getPassword())
                    .email(userForm.getEmail())
                    .gender(userForm.getGender())
                    .phoneNumber(userForm.getPhoneNumber())
                    .about(userForm.getAbout())
                    .build();

            Optional<User> savedUserOptional = Optional.ofNullable(userServices.saveUser(user));
            if (savedUserOptional.isPresent()) {
                User savedUser = savedUserOptional.get();
                redirectAttributes.addFlashAttribute("message", "User Register Successfully !! User_ID : " + savedUser.getUserId() + " Name : " + savedUser.getName());
            } else {
                redirectAttributes.addFlashAttribute("message", "User unable to register... Try Again");
            }
            return "redirect:/register";
        } catch (DuplicateUserException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception:: " + e);
        }
        return "redirect:/register";
    }
 // ---------------------------------------------------------------------------------------------------------------------------------------------------




}
