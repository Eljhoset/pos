/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljhoset.pos.item.model.jpa;

import com.eljhoset.pos.jpa.model.AccountBaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Table(name = "options")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option extends AccountBaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotBlank
    private String name;
}
