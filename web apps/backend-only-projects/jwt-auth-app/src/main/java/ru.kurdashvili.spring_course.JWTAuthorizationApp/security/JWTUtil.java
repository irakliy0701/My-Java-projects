package ru.kurdashvili.spring_course.JWTAuthorizationApp.security;

// Класс, который будет работать с JWT токенами.
// В этом классе будем генерировать токен (чтобы отдать пользователю)
// и валидировать его (то, что клиент присылает в запросе, какой токен)

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("{jwt_secret}")
    private String secret;

    public String generateToken(String username){// будем генерить токен
                // на основании username (могли написать больше аргументов)

        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        // срок хранения токена на сервере будет составлять 60 мин

        return JWT.create()
                .withSubject("Uer Details") // будем хранить данные токена (указываем как комент)
                .withClaim("username", username) // указываем пары ключ-значение, которые будут храниться в теле токена
                .withIssuedAt(new Date()) // указываем время на сервере, когда был создан токен
                .withIssuer("kurdashvili")// указываем, кто выдал токен (в реальном приложении может лежать название приожения)
                .withExpiresAt(expirationDate) // указываем когда токен будет удален
                .sign(Algorithm.HMAC256(secret)); // должны "подписать" токен, те передать какой-то секрет (см. презентацию)
    }

    // Метод, который будем вызывать, когда клиент будет слать запрос,
    // те клиент будет слать запрос с JWT токеном, который мы ему выдали
    // и мы должны этот токен валидировать. Еще в этом методе должны извлекать
    // из присланного токена Сlaim - username в нашем случае, чтобы пользователя
    // найти по базе (по имени) и чтоб провести аутентификацию
    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("kurdashvili")
                .build(); // только токен с таким Subject, Issuer, secret
                        // будет проходить нашу валидаиию

        DecodedJWT jwt = verifier.verify(token);// валидация токена
        // jwt - получили уже декодированный токен и можем получать Claim:
        return jwt.getClaim("username").asString();
    }
}
