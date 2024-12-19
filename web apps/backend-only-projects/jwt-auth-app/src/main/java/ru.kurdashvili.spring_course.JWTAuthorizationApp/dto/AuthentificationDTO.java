package ru.kurdashvili.spring_course.JWTAuthorizationApp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// В этом DTO будут посылваться данные для аутентификации
public class AuthentificationDTO {

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым") // TODO: try to comment this annotation
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
