package com.eljhoset.pos.account.model.jpa;

import com.eljhoset.pos.jpa.model.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author Daniel
 */
@Entity
@Data
public class Account extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

}
