package com.eljhoset.pos.jpa.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Aspect
public class TenantAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Before("execution(* org.springframework.data.repository.Repository+.*(..))")
    public void before(JoinPoint joinPoint) throws IOException, ClassNotFoundException, InvocationTargetException, Exception {
        Session session = entityManager.unwrap(Session.class);
        if (TenantContext.getCurrentTenant() != null) {
            session.enableFilter("accountFilter").setParameter("account", TenantContext.getCurrentTenant().getId());
        }
    }

}
