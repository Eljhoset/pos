package com.eljhoset.pos.category.model.http;

import com.eljhoset.pos.category.model.jpa.Category;
import lombok.Getter;

/**
 *
 * @author Daniel
 */
@Getter
public class CategoryResponse {

    private final Long id;
    private final String name;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

}
