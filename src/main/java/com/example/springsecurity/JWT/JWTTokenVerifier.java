package com.example.springsecurity.JWT;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

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
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader= httpServletRequest.getHeader("Authorization");

        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        final String token = authorizationHeader.replace("Bearer ","");
        //See this in a new example
        try{


            final String secretKey="securemyapisjyarguelles23elmaskbronqueescribe";

          Jws<Claims> claimsJws =   Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
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
    }
}
