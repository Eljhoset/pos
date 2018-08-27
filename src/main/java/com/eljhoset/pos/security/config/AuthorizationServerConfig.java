package com.eljhoset.pos.security.config;

import com.eljhoset.pos.security.client.service.ClientService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 *
 * @author Daniel
 */
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ApplicationContext applicationContext;
    private OAuth2RequestFactory oAuth2RequestFactory;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        this.oAuth2RequestFactory = endpoints.getOAuth2RequestFactory();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientService);

    }

    @Configuration
    protected class AuthorizationServerSecurityConfig extends AuthorizationServerSecurityConfiguration {

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            super.configure(auth);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);
            final TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter = new TokenEndpointAuthenticationFilter(authenticationManager, oAuth2RequestFactory);
            tokenEndpointAuthenticationFilter.setAuthenticationEntryPoint(new AuthenticationEntryPoint() {
                private final ExceptionHandlerExceptionResolver resolver;

                {
                    this.resolver = new ExceptionHandlerExceptionResolver();
                    List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
                    messageConverters.add(new MappingJackson2HttpMessageConverter(Jackson2ObjectMapperBuilder.json().applicationContext(applicationContext).build()));
                    this.resolver.setMessageConverters(messageConverters);
                    this.resolver.setApplicationContext(applicationContext);
                    this.resolver.afterPropertiesSet();
                }

                @Override
                public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                    resolver.resolveException(request, response, null, authException);
                }
            });
            http.addFilterAfter(tokenEndpointAuthenticationFilter, BasicAuthenticationFilter.class);
        }
    }
}
