package by.miner.mono.config;

import by.miner.mono.security.AuthenticationProperties;
import by.miner.mono.security.JwtAuthenticationEntryPoint;
import by.miner.mono.security.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final int strength;
    private final AuthenticationProperties authenticationProperties;
    private final UserDetailsService userDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public WebSecurityConfig(@Value("${security.password.strength}") int strength,
                             AuthenticationProperties authenticationProperties,
                             UserDetailsService userDetailsService,
                             JwtAuthorizationFilter jwtAuthorizationFilter,
                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.strength = strength;
        this.authenticationProperties = authenticationProperties;
        this.userDetailsService = userDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers(HttpMethod.POST, authenticationProperties.getSignInUrl()).permitAll()
                .antMatchers(HttpMethod.POST, authenticationProperties.getSignInUrl()).permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl(authenticationProperties.getSignOutSuccessRedirectUrl())
                .logoutUrl(authenticationProperties.getSignOutUrl())
                .and()
                .addFilterAt(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                // this disables session creation on Spring Security
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(strength);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


}
