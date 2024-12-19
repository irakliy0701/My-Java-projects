package ru.kurdashvili.spring_course.JWTAuthorizationApp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.security.PersonDetails;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.services.AdminService;

@Controller
public class HelloController {
    private final AdminService adminService;

    @Autowired
    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    // Получим инфу и аутентиф пользователе в консоли:
    // (Могли это сделать и в методе hello())
    @GetMapping("/showUserInfo")
    @ResponseBody
    public String showUserInfo(){
        // Здесь имеем доступ к объекту Authentification

        // Из потока для текущего пользователя, мы достаем объект
        // Authentification, который вернули в методе authenticate
        // класса AuthProviderImpl, и кладем его в сессию. Из сессии
        // его Spring Security достает, помещает в SecurityContextHolder для
        // этого пользователя в этом потоке и мы получаем его с помощью
        // getAuthentication()
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());

        return personDetails.getUsername();
    }

    // cтраница, на которую может попасть только админ.
    // Чтобы ограничить доступ к этой странице и дать доступ к
    // ней только админу -> cм. класс SecurityConfig (настройка авторизации)
    @GetMapping("/admin")
    public String adminPage(){
        adminService.doAdminStuff();
        return "admin";
    }
}
