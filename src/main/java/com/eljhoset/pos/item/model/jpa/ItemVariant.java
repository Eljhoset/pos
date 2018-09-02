/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljhoset.pos.item.model.jpa;

import com.eljhoset.pos.jpa.model.AccountBaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author jd-jd
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemVariant extends AccountBaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Item item;
    @Column(unique = true)
    @NotNull
    @NotBlank
    private String sku;
    @NotNull
    private BigDecimal price;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(referencedColumnName = "item_id", name = "item_id")})
    @Size(min = 1)
    @NotNull
    private List<ItemVariantOptionValue> values;

    public void addValue(ItemVariantOptionValue itemVariantOptionValue) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(itemVariantOptionValue);
    }
}
