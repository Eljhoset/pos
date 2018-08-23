package com.eljhoset.pos.category.model.jpa;

import com.eljhoset.pos.category.model.http.CategoryResponse;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Daniel
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public CategoryResponse toCategoryResponse() {
        return new CategoryResponse(this);
    }

}
