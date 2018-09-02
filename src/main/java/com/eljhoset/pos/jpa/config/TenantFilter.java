package com.eljhoset.pos.jpa.config;

import com.eljhoset.pos.account.model.jpa.Account;
import com.eljhoset.pos.user.model.jpa.Users;
import com.eljhoset.pos.user.repository.UserRepository;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
@Component
public class TenantFilter implements Filter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    @Transactional
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Account account = userRepository.findByUsername(request.getUserPrincipal().getName()).map(Users::getAccount)
                .get();
        TenantContext.setCurrentTenant(account);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
