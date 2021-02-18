package com.example.springsecurity.JWT;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTTokenVerifier extends OncePerRequestFilter {
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JWTTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader= httpServletRequest.getHeader(jwtConfig.getAuthorizationHeader());

        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        final String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(),"");
        //See this in a new example
        try{
                    Jws<Claims> claimsJws =   Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

          Claims body =claimsJws.getBody();

          String username =body.getSubject();

          var authorities = (List<Map<String,String>>) body.get("authorities");

         Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet= authorities.stream()
                  .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                  .collect(Collectors.toSet());

          Authentication authentication= new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthoritySet);
          SecurityContextHolder.getContext().setAuthentication(authentication);


        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted"+token));
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
