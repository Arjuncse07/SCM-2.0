package com.scm.arjun.scm20.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebMvc
public class SecurityConfig{

    /*
     author: arjun
     Spring Security Configuration Class
     */



    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc)throws Exception{

        httpSecurity
                .formLogin(form->form.loginPage("/login").permitAll())
                .authorizeRequests(auth -> auth.requestMatchers(
                                                                    mvc.pattern("/"),
                                                                    mvc.pattern("/css/**"),
                                                                    mvc.pattern("/js/**"),
                                                                    mvc.pattern("/static/**"),
                                                                    mvc.pattern("/accessDenied"))
                                                                    .permitAll()

                )
                .exceptionHandling(ex->ex.accessDeniedPage("/logout"));
        return httpSecurity.build();
    }*/


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // ✅ Allow all requests without authentication
                )
                .csrf(csrf -> csrf.disable()) // ✅ Disable CSRF (useful for development)
                .formLogin(login -> login.disable()) // ✅ Disable login page
                .httpBasic(basic -> basic.disable()); // ✅ Disable Basic Auth

        return http.build();
    }


    @Bean
    MvcRequestMatcher.Builder mvc (HandlerMappingIntrospector introspector){
        return new MvcRequestMatcher.Builder(introspector);
    }

}
