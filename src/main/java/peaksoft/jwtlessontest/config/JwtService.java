package peaksoft.jwtlessontest.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import peaksoft.jwtlessontest.enitity.User;

import java.time.ZonedDateTime;

@Component
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    public String createToken(User user) {

        Algorithm algorithm = Algorithm.HMAC512(secretKey);

        return JWT.create()
                .withClaim("email", user.getUsername())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withExpiresAt(ZonedDateTime.now().plusHours(1).toInstant())
                .sign(algorithm);
    }

    //verify token(decode)
    public String verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaim("email").asString();
    }
}
