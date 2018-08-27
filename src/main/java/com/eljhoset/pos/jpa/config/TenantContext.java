package com.eljhoset.pos.jpa.config;

import com.eljhoset.pos.account.model.jpa.Account;

/**
 *
 * @author Daniel
 */
public class TenantContext {

    private static ThreadLocal<Account> accountTenant = new ThreadLocal<>();

    public static Account getCurrentTenant() {
        return accountTenant.get();
    }

    public static void setCurrentTenant(Account account) {
        accountTenant.set(account);
    }

    public static void clear() {
        accountTenant.set(null);
    }
}
