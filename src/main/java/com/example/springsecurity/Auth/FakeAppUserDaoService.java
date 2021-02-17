package com.example.springsecurity.Auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.springsecurity.Security.AppUserRoles.*;

@Repository("fake")
public class FakeAppUserDaoService implements AppUserDAO {

    private final PasswordEncoder passwordEncoder;

    @Autowired()
    public FakeAppUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<AppUser> selectAppUserByName(String username) {
        return getAppUsers()
                .stream()
                .filter(appUser -> username.equals(appUser.getUsername()))
                .findFirst();
    }

    private List<AppUser> getAppUsers(){
        List<AppUser> appUsers= Lists.newArrayList(
                new AppUser(
                        STUDENT.getGrantedAuthorities(),
                        passwordEncoder.encode("slipknot"),
                        "annasmith",
                        true,
                        true,
                        true,
                        true
                ),
                new AppUser(
                        ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("slipknot"),
                        "yasser",
                        true,
                        true,
                        true,
                        true
                ),
                new AppUser(
                        ADMIN_TRAINEE.getGrantedAuthorities(),
                        passwordEncoder.encode("slipknot"),
                        "tom",
                        true,
                        true,
                        true,
                        true
                )
        );
        return appUsers;
    }
}
