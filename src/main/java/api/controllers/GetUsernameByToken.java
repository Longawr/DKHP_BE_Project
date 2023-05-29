package api.controllers;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class GetUsernameByToken {

    public static String getusername(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Author ")) {
            try {
                String access_token = authorizationHeader.substring("Author ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(access_token);
                return decodedJWT.getSubject();
            } catch (Exception exception) {
                return exception.getMessage();
            }
        } else {
            throw new RuntimeException("Access token is missing");
        }
    }
}
