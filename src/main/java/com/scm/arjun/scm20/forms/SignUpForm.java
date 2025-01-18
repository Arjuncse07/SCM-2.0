package com.scm.arjun.scm20.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpForm {

   @NotBlank(message = "Name is Required")
   @Size(min = 3,max = 20,message = "Minimum 3 characters is required")
   private String name;
   @NotBlank(message = "Password is required")
   @Size(min = 6,message = "Min 6 character is required")
   private String password;
   @Email(message = "Invalid Email Address")
   @NotBlank(message = "Email is required")
   private String email;
   @NotBlank(message = "Gender is Required")
   private String gender;
   @NotBlank(message = "Phone number is required")
   private String phoneNumber;
   private String about;


}
