package com.eljhoset.pos.category.repository;

import com.eljhoset.pos.category.model.jpa.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
