package com.eljhoset.pos.item.model.jpa;

import java.io.Serializable;
import javax.persistence.Column;
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
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOptionValue implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String name;
}
