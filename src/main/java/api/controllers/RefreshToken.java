package api.controllers;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.DAO.AccountDAO;
import api.DAO.RoleDAO;
import api.DAO.RoleDAOImpl;
import api.POJO.Account;
import api.POJO.Role;

@RestController
@RequestMapping("/token/refresh")
public class RefreshToken {

    public static Map<String, Long> time = new HashMap<>();
    public final long tokenTime = 30*60*1000;
    @Autowired
    private AccountDAO accountDAO;

    @GetMapping
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Author ")) {
            try {
                String refresh_token = authorizationHeader.substring("Author ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                if(System.currentTimeMillis() - time.get(username) <= tokenTime) {
                    Account user = accountDAO.getAccountByID(username);
                    Collection<Role> roles = new ArrayList<>();
                    RoleDAO rDAO = new RoleDAOImpl();
                    roles.add(rDAO.getById(user.getRole()));
                    String access_token = JWT.create()
                            .withSubject(user.getAccountId())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                            .withIssuer(request.getRequestURL().toString())
                            .withClaim("role", roles.stream().map(Role::getId).collect(Collectors.toList()))
                            .sign(algorithm);
                    time.replace(username, System.currentTimeMillis());
                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", access_token);
                    tokens.put("refresh_token", refresh_token);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }else{
                    time.remove(username);
                    throw new RuntimeException("Login Session Timeout");
                }
            }catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(UNAUTHORIZED.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}
