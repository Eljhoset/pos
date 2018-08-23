package com.eljhoset.pos.item.model.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class ItemVariant implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @NotNull
    private Item item;
    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ItemVariantOption> options;

    @NotBlank
    @NotNull
    private String sku;
    @NotBlank
    @NotNull
    private BigDecimal price;

}
