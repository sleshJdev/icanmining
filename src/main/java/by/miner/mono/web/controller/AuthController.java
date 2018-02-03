package by.miner.mono.web.controller;

import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.security.Credentials;
import by.miner.mono.service.ApplicationUserService;
import by.miner.mono.web.dto.AuthDetailsDto;
import by.miner.mono.web.dto.TokenDto;
import by.miner.mono.web.dto.UserDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static by.miner.mono.security.SecurityConstants.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final ApplicationUserService applicationUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthController(ApplicationUserService applicationUserService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserService = applicationUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-in")
    @ResponseBody
    public AuthDetailsDto signIn(@RequestBody Credentials credentials) {
        ApplicationUser user = applicationUserService.findByUsername(credentials.getUsername());
        if (user == null) {
             throw new AuthenticationServiceException("User not found");
        }
        try {
            String token = JWT.create()
                    .withSubject(user.getPassword())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(Algorithm.HMAC256(SECRET_KEY));
            return new AuthDetailsDto(
                    new TokenDto(HEADER_STRING, TOKEN_PREFIX + token),
                    new UserDto(user.getUsername()));
        } catch (UnsupportedEncodingException e) {
            throw new AuthenticationServiceException("Cannot authenticate", e);
        }
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Credentials credentials) {
        credentials.setPassword(bCryptPasswordEncoder.encode(credentials.getPassword()));
        applicationUserService.save(credentials);
    }
}
