package com.eljhoset.pos.item.repository;

import com.eljhoset.pos.item.model.jpa.ItemOption;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository
public interface ItemOptionRepository extends PagingAndSortingRepository<ItemOption, Long> {

    Optional<ItemOption> findByName(String name);
}
