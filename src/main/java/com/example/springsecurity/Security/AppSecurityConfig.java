package com.example.springsecurity.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.springsecurity.Security.AppUserPermission.*;
import static com.example.springsecurity.Security.AppUserRoles.*;

/* This class We set all the security configurations for our application */
@Configuration
@EnableWebSecurity
// for Enable Preauthorize
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     // Authorize any request, must be authenticated and mecanism is basic authentication.
        //order does matter in this conditions
       http
               .csrf().disable() //Later In detail
               .authorizeRequests()
               .antMatchers("/","index","/css/*","/js/*","/ts/*")
               .permitAll()
               .antMatchers("/api/**").hasRole(STUDENT.name())
               .antMatchers(HttpMethod.DELETE,"managament/api/**").hasAuthority(COURSE_WRITE.getPermission())
               .antMatchers(HttpMethod.POST,"managament/api/**").hasAuthority(COURSE_WRITE.getPermission())
               .antMatchers(HttpMethod.PUT,"managament/api/**").hasAuthority(COURSE_WRITE.getPermission())
               .antMatchers(HttpMethod.GET,"managament/api/**").hasAnyRole(ADMIN.name(),ADMIN_TRAINEE.name())
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();
    }

    /*This is how u retrieve your users from database*/
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
      UserDetails anna =  User.builder()
                .username("annasmith")
                .password(passwordEncoder.encode("slipknot"))
                //.roles(STUDENT.name()) // role student
               .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails yasser=  User.builder()
                .username("yasser")
                .password(passwordEncoder.encode("123"))
                //.roles(ADMIN.name()) // role student
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails tom=  User.builder()
                .username("tom")
                .password(passwordEncoder.encode("123"))
                //.roles(ADMIN_TRAINEE.name()) // role student
                .authorities(ADMIN_TRAINEE.getGrantedAuthorities())
                .build();

      // for in memory database user
      return new InMemoryUserDetailsManager(
              anna,
              yasser,
              tom
      );
    }
}
