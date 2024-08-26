package com.scm.arjun.scm20.forms;

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

   private String name;
   private String password;
   private String email;
   private String gender;
   private String phoneNumber;
   private String about;


}
