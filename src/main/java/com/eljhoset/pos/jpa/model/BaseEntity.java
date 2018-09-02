package com.eljhoset.pos.jpa.model;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@FilterDef(name = "ACTIVE_FILTER", defaultCondition = "active='ACTIVE'")
@Filter(name = "ACTIVE_FILTER")
public class BaseEntity {

    @CreatedBy
    private String createdBy;
    @CreationTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedBy
    private String updateBy;
    @UpdateTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updatedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @Column(unique = true)
    private String uuid;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date deletedAt;
    private String deleteBy;

    @PrePersist
    protected void prePersist() {
        status = EntityStatus.ACTIVE;
        uuid = ("".equals(uuid) || null == uuid) ? UUID.randomUUID().toString() : uuid;
    }

    public void markAsDeleted(Date deletedAt, String userId) {
        this.deletedAt = deletedAt;
        this.deleteBy = userId;
        this.status = EntityStatus.DELECTED;
    }

    public void markAsCancelled(String username) {
        status = EntityStatus.CANCELLED;
        deletedAt = new Date();
        deleteBy = username;
    }

    public boolean isActive() {
        return status.equals(EntityStatus.ACTIVE);
    }

}
