package com.eljhoset.pos.jpa.model;

import com.eljhoset.pos.account.model.jpa.Account;
import com.eljhoset.pos.jpa.config.TenantContext;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

/**
 *
 * @author Daniel
 */
@Data
@MappedSuperclass
@FilterDef(name = "accountFilter", parameters = {
    @ParamDef(name = "account", type = "long")})
@Filter(name = "accountFilter", condition = "account_id = :account")
public class AccountBaseEntity extends BaseEntity {

    @ManyToOne
    @NotNull
    protected Account account;

    @PrePersist
    @Override
    protected void prePersist() {
        super.prePersist();
        account = TenantContext.getCurrentTenant();
    }

}
