package com.scm.arjun.scm20.forms;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginForm {

    private String username;
    private String password;

}
