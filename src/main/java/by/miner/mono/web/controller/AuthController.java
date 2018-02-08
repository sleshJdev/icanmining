package by.miner.mono.web.controller;

import by.miner.mono.dto.AuthDetailsDto;
import by.miner.mono.dto.TokenDto;
import by.miner.mono.dto.UserDto;
import by.miner.mono.enums.RoleName;
import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.model.Role;
import by.miner.mono.security.Credentials;
import by.miner.mono.security.TokenService;
import by.miner.mono.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static by.miner.mono.security.SecurityConstants.HEADER_STRING;
import static by.miner.mono.security.SecurityConstants.TOKEN_PREFIX;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final ApplicationUserService applicationUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthController(ApplicationUserService applicationUserService,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          AuthenticationManager authenticationManager, TokenService tokenService) {
        this.applicationUserService = applicationUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/sign-in")
    @ResponseBody
    public AuthDetailsDto signIn(@RequestBody Credentials credentials) {
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
        return new AuthDetailsDto(
                new TokenDto(HEADER_STRING, TOKEN_PREFIX + tokenService.encode(user)),
                new UserDto(user.getUsername(), roleNames.stream().map(RoleName::valueOf).collect(toList())));
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Credentials credentials) {
        credentials.setPassword(bCryptPasswordEncoder.encode(credentials.getPassword()));
        applicationUserService.save(credentials);
    }
}
