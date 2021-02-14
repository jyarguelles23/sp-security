package com.example.springsecurity.Security;
import com.google.common.collect.Sets;
import java.util.Set;

import static com.example.springsecurity.Security.AppUserPermission.*;

public enum AppUserRoles {
   STUDENT (Sets.newHashSet()),
   ADMIN (Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE));

   private final Set<AppUserPermission> permissions;
   AppUserRoles(Set<AppUserPermission> permissions){
      this.permissions=permissions;
   }
}
