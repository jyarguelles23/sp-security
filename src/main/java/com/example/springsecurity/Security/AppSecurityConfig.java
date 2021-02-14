package com.example.springsecurity.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/* This class We set all the security configurations for our application */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     // Authorize any request, must be authenticated and mecanism is basic authentication.
       http
               .authorizeRequests()
               .antMatchers("/","index","/css/*","/js/*","/ts/*")
               .permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();
    }

    /*This is how u retrive your users from database*/
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
      UserDetails anna =  User.builder()
                .username("annasmith")
                .password(passwordEncoder.encode("slipknot"))
                .roles("STUDENT") // role student
                .build();
      // for in memory database user
      return new InMemoryUserDetailsManager(
              anna
      );
    }
}
