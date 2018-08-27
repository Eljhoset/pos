package com.eljhoset.pos.item.repository;

import com.eljhoset.pos.account.model.jpa.Account;
import com.eljhoset.pos.item.model.jpa.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    @Query("Select e From Item e where e.status='ACTIVE' and e.account=:account")
    Page<Item> findAll(@Param("account") Account account, Pageable pageable);
}
