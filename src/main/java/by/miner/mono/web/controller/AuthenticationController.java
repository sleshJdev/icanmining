package by.miner.mono.web.controller;

import by.miner.mono.dto.AuthInfo;
import by.miner.mono.enums.RoleName;
import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.model.Role;
import by.miner.mono.security.AuthenticationProperties;
import by.miner.mono.security.Credentials;
import by.miner.mono.security.JwtService;
import by.miner.mono.service.ApplicationUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class AuthenticationController {
    private final ApplicationUserService applicationUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthenticationProperties authenticationProperties;

    public AuthenticationController(ApplicationUserService applicationUserService,
                                    BCryptPasswordEncoder bCryptPasswordEncoder,
                                    AuthenticationManager authenticationManager,
                                    JwtService jwtService, AuthenticationProperties authenticationProperties) {
        this.applicationUserService = applicationUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authenticationProperties = authenticationProperties;
    }

    @PostMapping("${auth.signInUrl}")
    @ResponseBody
    public AuthInfo signIn(@RequestBody Credentials credentials) {
        ApplicationUser user = applicationUserService.findByUsername(credentials.getUsername());
        if (user == null) {
            throw new AuthenticationServiceException("User not found");
        }
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName).map(Enum::toString).collect(toList());

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        roleNames.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(toList())));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String rawToken = jwtService.generateToken(user);
        return new AuthInfo(
                new AuthInfo.Token(authenticationProperties.getHeaderString(), authenticationProperties.getTokenPrefix() + rawToken),
                new AuthInfo.User(user.getUsername(), roleNames.stream().map(RoleName::valueOf).collect(toList())));
    }

    @PostMapping("${auth.signUpUrl}")
    public void signUp(@RequestBody Credentials credentials) {
        credentials.setPassword(bCryptPasswordEncoder.encode(credentials.getPassword()));
        applicationUserService.save(credentials);
    }
}
