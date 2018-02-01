package by.miner.mono.web.controller;

import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.security.Credentials;
import by.miner.mono.web.dto.AuthDetailsDto;
import by.miner.mono.web.dto.TokenDto;
import by.miner.mono.web.dto.UserDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static by.miner.mono.security.SecurityConstants.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-in")
    @ResponseBody
    public AuthDetailsDto signIn(@RequestBody Credentials credentials) {
        try {
            String token = JWT.create()
                    .withSubject(credentials.getPassword())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(Algorithm.HMAC256(SECRET_KEY));
            return new AuthDetailsDto(
                    new TokenDto(HEADER_STRING, TOKEN_PREFIX + token),
                    new UserDto(credentials.getUsername()));
        } catch (UnsupportedEncodingException e) {
            throw new AuthenticationServiceException("Cannot authenticate", e);
        }
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Credentials credentials) {
        credentials.setPassword(bCryptPasswordEncoder.encode(credentials.getPassword()));
        applicationUserRepository.save(new ApplicationUser(credentials.getUsername(), credentials.getPassword()));
    }
}
