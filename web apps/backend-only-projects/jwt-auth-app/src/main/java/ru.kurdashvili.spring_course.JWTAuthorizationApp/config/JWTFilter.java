package ru.kurdashvili.spring_course.JWTAuthorizationApp.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.security.JWTUtil;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.services.PersonDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Наша задача в реализации JWT атуентификации, состоит в том, чтобы
// для всех запросов к нашему Spring приложению отлавливать все запросы
// и во всех запросах смотерть на  заголовок, где будет лежать наш JWT токен,
// который получили

// Класс для отлова запросов
@Component
public class JWTFilter extends OncePerRequestFilter { // должны отлавливать один раз
            // на зпрос (для каждого запроса должны 1 раз посмотреть на этот запрос и чекнуть
            // header-ы этого запроса -> для этого есть спец класс OncePerRequestFilter

    private final JWTUtil jwtUtil;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, PersonDetailsService personDetailsService) {
        this.jwtUtil = jwtUtil;
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Здесь получаем доступ к HTTP запросу

        String authHeader = request.getHeader("Authorization");// JWT токены принято передавать под
        // ключом Authorization

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            // Если true, то значит передается какой-то токен в запросе,
            // должны этот токен получить и посмотреть валидный он или нет:
            String jwt = authHeader.substring(7); // сам токен начинается после Bearer ,поэтому 7 индекс

            if (jwt.isBlank()){
                response.sendError(response.SC_BAD_REQUEST,
                        "Invalid JWT token in Bearer Header");
            }
            else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    UserDetails userDetails = personDetailsService.loadUserByUsername(username);

                    // Помещаем даннеы токена в Security контекст
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    if(SecurityContextHolder.getContext().getAuthentication() == null){
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
                catch (JWTVerificationException exc){
                    response.sendError(response.SC_BAD_REQUEST,
                            "Invalid JWT Token");
                }

            }
        }
        filterChain.doFilter(request, response); // передаем дальше в цепочку фильтров
        // Spring Security
    }

}
