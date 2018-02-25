package by.miner.mono.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final AuthenticationProperties authenticationProperties;
    private final JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationProperties authenticationProperties, JwtService jwtService) {
        this.authenticationProperties = authenticationProperties;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String authToken = req.getHeader(authenticationProperties.getHeaderString());

        if (authToken == null || !authToken.startsWith(authenticationProperties.getTokenPrefix())) {
            chain.doFilter(req, res);
            return;
        }

        String rawToken = authToken.substring(authenticationProperties.getTokenPrefix().length());
        DecodedJWT token = jwtService.verify(rawToken);

        String username = token.getClaim(Claims.USERNAME.name()).asString();
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("Username not found in the auth token");
        }
        List<String> roleNames = token.getClaim(Claims.ROLES.name()).asList(String.class);
        if (CollectionUtils.isEmpty(roleNames)) {
            throw new UsernameNotFoundException("Role not found in the auth token");
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null,
                            roleNames.stream().map(SimpleGrantedAuthority::new).collect(toList()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(req, res);
    }
}
