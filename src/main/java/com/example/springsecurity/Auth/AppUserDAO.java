package com.example.springsecurity.Auth;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserDAO {

    Optional<AppUser> selectAppUserByName(String username);
}
