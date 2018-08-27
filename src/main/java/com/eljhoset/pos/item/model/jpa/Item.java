/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljhoset.pos.item.model.jpa;

import com.eljhoset.pos.category.model.jpa.Category;
import com.eljhoset.pos.item.model.http.ItemResponse;
import com.eljhoset.pos.jpa.model.AccountBaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends AccountBaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @NotNull
    private transient Category category;
    @NotNull
    @NotBlank
    private String name;
    @Size(min = 1)
    @NotNull
    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private List<ItemVariant> variants;
    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private List<ItemVariantOption> options;

    public void addOption(ItemVariantOption itemVariantOption) {
        if (options == null) {
            options = new ArrayList<>();
        }
        options.add(itemVariantOption);
    }

    public void addVariant(ItemVariant itemVariant) {
        if (variants == null) {
            variants = new ArrayList<>();
        }
        variants.add(itemVariant);
    }

    public ItemResponse toItemResponse() {
        return new ItemResponse(Item.this);
    }
}
