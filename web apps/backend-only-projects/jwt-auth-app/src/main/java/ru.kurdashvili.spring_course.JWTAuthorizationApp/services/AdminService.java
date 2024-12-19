package ru.kurdashvili.spring_course.JWTAuthorizationApp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('SOME_OTHER_ROLE')")// можем писать более сложные
    // выражения: "hasRole('ROLE_ADMIN') or hasRole('SOME_OTHER_ROLE')" или использовать and
    public void doAdminStuff(){
        System.out.println("Only admin here");
    }
}
