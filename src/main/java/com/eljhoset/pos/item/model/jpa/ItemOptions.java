package com.eljhoset.pos.item.model.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class ItemOptions implements Serializable {

    @Id
    @ManyToOne
    private Item item;
    @Id
    @ManyToOne
    private ItemOption itemOption;
    @OneToMany(cascade = CascadeType.ALL)
    @NotNull
    @Size(min = 1)
    private List<ItemOptionValue> allowedValues;

}
