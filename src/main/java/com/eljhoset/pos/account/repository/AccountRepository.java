package com.eljhoset.pos.account.repository;

import com.eljhoset.pos.account.model.jpa.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

}
