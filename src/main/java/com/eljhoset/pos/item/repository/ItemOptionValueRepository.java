package com.eljhoset.pos.item.repository;

import com.eljhoset.pos.item.model.jpa.ItemOptionValue;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository
public interface ItemOptionValueRepository extends PagingAndSortingRepository<ItemOptionValue, Long> {

    Optional<ItemOptionValue> findByName(String name);
}
