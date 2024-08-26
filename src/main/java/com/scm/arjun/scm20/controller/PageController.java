package com.scm.arjun.scm20.controller;

import com.scm.arjun.scm20.entities.User;
import com.scm.arjun.scm20.services.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.arjun.scm20.forms.SignUpForm;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.Option;
import java.security.PublicKey;
import java.util.Optional;

@Controller
public class PageController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/login")
    public String login()
    {
        return "login";
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
    public String register(Model model){
       try{
       System.out.println("Register Controller");
       }catch(Exception e){
        e.printStackTrace();
       }
        
        return "login/signUp";
    }

    //  -------------------------------------------- REGISTER PAGE --------------------------------------------------------------------------------
    @PostMapping("/do-register")
    public String registerProcess(@ModelAttribute SignUpForm userForm, RedirectAttributes redirectAttributes) {

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
    }
 // ---------------------------------------------------------------------------------------------------------------------------------------------------




}
