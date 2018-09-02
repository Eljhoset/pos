package com.eljhoset.pos.item.repository;

import com.eljhoset.pos.item.model.jpa.Item;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

}
