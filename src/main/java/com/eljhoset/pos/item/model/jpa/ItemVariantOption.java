/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljhoset.pos.item.model.jpa;

import com.eljhoset.pos.jpa.model.AccountBaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author jd-jd
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemVariantOption extends AccountBaseEntity implements Serializable {

    @Id
    @ManyToOne
    private Item item;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private Option options;
    @OneToMany(orphanRemoval = true)
    @JoinColumns({
        @JoinColumn(referencedColumnName = "item_id", name = "item_id")
        ,@JoinColumn(referencedColumnName = "options_id", name = "options_id")})
    private List<ItemVariantOptionAllowedValue> allowedValues;

    public void addAllowedValue(ItemVariantOptionAllowedValue itemVariantOptionAllowedValue) {
        if (allowedValues == null) {
            allowedValues = new ArrayList<>();
        }
        allowedValues.add(itemVariantOptionAllowedValue);
    }

}
