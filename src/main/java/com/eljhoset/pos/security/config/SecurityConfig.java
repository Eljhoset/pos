package com.eljhoset.pos.security.config;

import com.eljhoset.pos.user.model.jpa.Users;
import com.eljhoset.pos.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Daniel
 */
@Configuration
@EnableWebSecurity(debug = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .anonymous().disable()
                .authorizeRequests().anyRequest().denyAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = authentication.getCredentials().toString();
                Users user = userService.login(username, password);
                return new UsernamePasswordAuthenticationToken(
                        username, password, user.getAuthorities());
            }

            @Override
            public boolean supports(Class<?> type) {
                return type.equals(UsernamePasswordAuthenticationToken.class);
            }
        });
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Users.passwordEncoder;
    }
}
