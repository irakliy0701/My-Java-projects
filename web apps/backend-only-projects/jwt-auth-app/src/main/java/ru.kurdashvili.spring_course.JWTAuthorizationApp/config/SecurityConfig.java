package ru.kurdashvili.spring_course.JWTAuthorizationApp.config;

// Главный класс, где настравиваем Spring Security.
// Здесь будем настраивать аутентификацию и авторизацию (и вообще все, что
// касается Spring security - настраивается в этом классе)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.services.PersonDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // теперь можем использовать @PreAuthorize, ее можем
// вешать куда-угодно в коде и Spring Security будет проверять, что у пользователя есть та роль, которую укажем
// прежде чем выполнять этот метод. Обычно эту аннотацию не используют в контроллерах (см. AdminService)
public class SecurityConfig extends WebSecurityConfigurerAdapter {// обязвтельно должны унаследоваться

    private final PersonDetailsService personDetailsService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, JWTFilter jwtFilter) {
        this.personDetailsService = personDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        // Скажем Spring Security, что хотим использовать свою страницу аутентиф:
        http.
                csrf().disable().
                authorizeRequests().
                antMatchers("/admin").hasRole("ADMIN").
                antMatchers("/auth/login","/auth/registration", "/error").permitAll().
                anyRequest().hasAnyRole("USER", "ADMIN").

                and(). // вызываем, чтобы перейти к настройке страницы аутентиф

                formLogin().
                loginPage("/auth/login").
                loginProcessingUrl("/process_login").
                defaultSuccessUrl("/hello", true).
                failureUrl("/auth/login?error").

                and().

                logout().logoutUrl("/logout").
                logoutSuccessUrl("/auth/login").

                and().

                sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS); // даем понять Spring Security, что теперь будет
                        // STATELESS подход к аутентификации (никакая сессия теперь на сервере не хранится, до этого было
                        // AlWAYS - всегда сохранялась сессия на сервере)

        // Добавляем наш собственный фильтр, чтоб он был добавлен в цепочку фильтров в Spring Security
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


    // метод для конфигурации нашей аутентификации (настраивает аутентификацию)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService).
                passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        // Возвращаем тот алгоритм шифрования, который используем:
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

}
