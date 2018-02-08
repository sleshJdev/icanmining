package by.miner.mono.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.miner.mono.security.SecurityConstants.*;
import static java.util.stream.Collectors.toList;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String rawToken = req.getHeader(HEADER_STRING);

        if (rawToken == null || !rawToken.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        DecodedJWT token = verifier.verify(rawToken.replace(TOKEN_PREFIX, ""));

        String username = token.getClaim(Claims.USERNAME.name()).asString();
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("Username not found in the auth token");
        }
        List<String> roleNames = token.getClaim(Claims.ROLES.name()).asList(String.class);
        if (CollectionUtils.isEmpty(roleNames)) {
            throw new UsernameNotFoundException("Role not found in the auth token");
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null,
                        roleNames.stream().map(SimpleGrantedAuthority::new).collect(toList()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
    }
}
