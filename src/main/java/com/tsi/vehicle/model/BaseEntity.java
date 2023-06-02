package com.tsi.vehicle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    protected Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    protected Date lastUpdated;

    protected Boolean activeFlag = Boolean.TRUE;

    @Version
    protected Integer version;
}
