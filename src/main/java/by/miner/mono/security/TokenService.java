package by.miner.mono.security;

import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.model.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TokenService {
    private TokenProperties properties;
    private String issuer;
    private Algorithm algorithm;
    private JWTVerifier verifier;

    @Autowired
    public TokenService(TokenProperties properties, @Value("${spring.application.name}") String issuer) throws UnsupportedEncodingException {
        this.properties = properties;
        this.issuer = issuer;
        this.algorithm = Algorithm.HMAC256(properties.getSecret());
        this.verifier = JWT.require(algorithm).acceptExpiresAt(0).build();
    }

    public DecodedJWT decode(String token) {
        return verifier.verify(token);
    }

    public String encode(ApplicationUser user) {
        LocalDateTime now = LocalDateTime.now();
        try {
            return JWT.create()
                    .withIssuer(issuer)
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
                            .map(Role::getName)
                            .map(Enum::name)
                            .toArray(String[]::new))
                    .withClaim(Claims.USERNAME.name(), user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new TokenCreationException("Cannot properly create token", ex);
        }
    }
}
