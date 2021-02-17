package com.example.springsecurity.Security;


import com.example.springsecurity.Auth.AppUserService;
import com.example.springsecurity.JWT.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

import static com.example.springsecurity.Security.AppUserRoles.*;

/* This class We set all the security configurations for our application */
@Configuration
@EnableWebSecurity
// for Enable Preauthorize
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final AppUserService appUserService;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder, AppUserService appUserService){
        this.passwordEncoder=passwordEncoder;
        this.appUserService = appUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     // Authorize any request, must be authenticated and mecanism is basic authentication.
        //order does matter in this conditions
       http
               //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
               //.and()
               .csrf().disable()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
               .authorizeRequests()
               .antMatchers("/","index","/css/*","/js/*","/ts/*")
               .permitAll()
               .antMatchers("/api/**").hasRole(STUDENT.name())
               .anyRequest()
               .authenticated();

    }

    //Using Custom class for login this how wire things up
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /*This is how u retrieve your users from database*/
    @Bean
    public  DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        //this line allows password to be decoded
        provider.setPasswordEncoder(passwordEncoder);
        //import userservice class
        provider.setUserDetailsService(appUserService);
        return provider;

    }

  /*  @Override
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
    }*/
}
