package com.example.springsecurity.Security;
import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.springsecurity.Security.AppUserPermission.*;

public enum AppUserRoles {
   STUDENT (Sets.newHashSet()),
   ADMIN (Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE)),
   ADMIN_TRAINEE (Sets.newHashSet(COURSE_READ,STUDENT_READ));

   private final Set<AppUserPermission> permissions;
   AppUserRoles(Set<AppUserPermission> permissions){
      this.permissions=permissions;
   }

   public Set<AppUserPermission> getPermissions(){
      return permissions;
   }

   public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
       Set<SimpleGrantedAuthority> permision= getPermissions().stream()
              .map(permissions -> new SimpleGrantedAuthority(permissions.getPermission()))
              .collect(Collectors.toSet());
       permision.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
       return permision;
   }
}
