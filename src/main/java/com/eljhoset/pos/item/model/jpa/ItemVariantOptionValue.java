/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljhoset.pos.item.model.jpa;

import com.eljhoset.pos.jpa.model.AccountBaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class ItemVariantOptionValue extends AccountBaseEntity implements Serializable {

    @Id
    @ManyToOne
    private Item item;
    @Id
    @ManyToOne
    private ItemOption options;
    @Id
    @ManyToOne
    private ItemOptionValue optionValue;

}
