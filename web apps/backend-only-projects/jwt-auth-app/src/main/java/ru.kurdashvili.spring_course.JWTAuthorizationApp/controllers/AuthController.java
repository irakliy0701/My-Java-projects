package ru.kurdashvili.spring_course.JWTAuthorizationApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.dto.AuthentificationDTO;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.dto.PersonDTO;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.models.Person;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.security.JWTUtil;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.services.RegistrationService;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.util.PersonValidator;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;

    private final RegistrationService registrationService;

    private final JWTUtil jwtUtil;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

//    @GetMapping("/login")
//    public String loginPage(){
//        return "auth/login";
//    }
//
//    // Мы не реализуем еще один метод для приема данных с формы auth/login (Spring Security
//    // делает это за нас), то есть мы аутентификацию сами не реализуем, мы Spring Security даем только
//    // наш PersonDetailsService, а все остальное делает Spring Security
//
//    // Cоздадим контроллер и страницу для создания(регистрации) нового пользователя
//    @GetMapping("/registration")
//    public String registrationPage(@ModelAttribute("person") Person person){
//        return "auth/registration";
//    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                      BindingResult bindingResult){

        Person person = convertToPerson(personDTO);

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            return Map.of("message", "Ошибка!");
        }

        registrationService.register(person);

        // Должны сгенерировать токен и отправить его клиенту в виде JSON:
        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthentificationDTO authentificationDTO){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authentificationDTO.getUsername(),
                        authentificationDTO.getPassword());

        try{
            authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e) { // если переданы неправильные пароль и username
            return Map.of("message", "Incorrect credentials");
        }

        // Аутентификация прошла успешно, назаначим новые токен уже с обновленным сроком годности:
        String token = jwtUtil.generateToken(authentificationDTO.getUsername());

        return Map.of("jwt-token", token);

    }

    public Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }
}
