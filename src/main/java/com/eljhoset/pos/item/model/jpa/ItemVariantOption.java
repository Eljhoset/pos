package com.eljhoset.pos.item.model.jpa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class ItemVariantOption implements Serializable {

    @Id
    @ManyToOne
    private Item item;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private ItemOption options;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private ItemOptionValue optionValue;

}
