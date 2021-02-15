package com.example.springsecurity.Security;

import lombok.Getter;
public enum AppUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String permission;

    AppUserPermission(String permission){
        this.permission=permission;
    }

    public String getPermission() {
        return permission;
    }
}
