package com.eljhoset.pos;

import com.eljhoset.pos.account.model.jpa.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.AbstractJpaQuery;
import org.springframework.data.jpa.repository.query.JpaQueryExecution;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }

    @Aspect
    @Component
    public class AccountAspect {

        @Before("execution(* org.springframework.data.jpa.repository.query.JpaQueryExecution.PagedExecution.doExecute*(..)) && args(query, values)")
        public void aroundExecution(JoinPoint pjp, AbstractJpaQuery query, Object[] values) throws Throwable {
            JpaQueryExecution target = (JpaQueryExecution) pjp.getTarget();
            System.out.println("here2");
//            org.hibernate.Filter filter = entityManager.unwrap(Session.class).enableFilter("tenantFilter");
//            filter.setParameter("tenantId", TenantContext.getCurrentTenant());
//            filter.validate();
        }

        @Before("execution(* org.hibernate.SessionFactory.openSession(..))")
        public void aaa(JoinPoint joinPoint) throws Throwable {
            System.out.println("here3");
        }

        @Before("execution(* com.eljhoset.pos.item.repository.ItemRepository.findAll*(..)) && args(account, pageable)")
        public void aroundExecution(JoinPoint pjp, Account account, Pageable pageable) throws Throwable {
            System.out.println("here");
//            org.hibernate.Filter filter = entityManager.unwrap(Session.class).enableFilter("tenantFilter");
//            filter.setParameter("tenantId", TenantContext.getCurrentTenant());
//            filter.validate();
        }
    }

}
