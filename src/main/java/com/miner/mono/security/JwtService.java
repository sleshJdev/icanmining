package com.miner.mono.security;

import com.miner.mono.dto.ApplicationUserDto;
import com.miner.mono.dto.RoleDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {
    private final AuthenticationProperties properties;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtService(AuthenticationProperties properties) throws UnsupportedEncodingException {
        this.properties = properties;
        this.algorithm = Algorithm.HMAC256(properties.getSecret());
        this.verifier = JWT.require(algorithm).acceptExpiresAt(0).build();
    }

    public DecodedJWT verify(String token) {
        return verifier.verify(token);
    }

    public String generateToken(ApplicationUserDto user) {
        LocalDateTime now = LocalDateTime.now();
        return JWT.create()
                .withIssuer(properties.getIssuer())
                .withIssuedAt(Date
                        .from(now.atZone(ZoneId.systemDefault())
                                .toInstant()))
                .withExpiresAt(Date
                        .from(now.plusSeconds(properties.getMaxAgeSeconds())
                                .atZone(ZoneId.systemDefault())
                                .toInstant()))
                .withArrayClaim(Claims.ROLES.name(), user
                        .getRoles()
                        .stream()
                        .map(RoleDto::getName)
                        .map(Enum::name)
                        .toArray(String[]::new))
                .withClaim(Claims.USERNAME.name(), user.getUsername())
                .sign(algorithm);
    }
}
