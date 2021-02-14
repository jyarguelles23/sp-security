package com.example.springsecurity.Security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/* This class We set all the security configurations for our application */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
     // Authorize any request, must be authenticated and mecanism is basic authentication.
       http
               .authorizeRequests()
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();
    }
}
