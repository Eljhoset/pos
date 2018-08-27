package com.eljhoset.pos.category.model.jpa;

import com.eljhoset.pos.category.model.http.CategoryResponse;
import com.eljhoset.pos.jpa.model.AccountBaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class Category extends AccountBaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotBlank
    private String name;

    public CategoryResponse toCategoryResponse() {
        return new CategoryResponse(this);
    }

}
