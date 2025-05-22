package org.openmrs.eip.dbsync.entity.light;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "location")
@AttributeOverride(name = "id", column = @Column(name = "location_id"))
public class LocationLight extends RetireableLightEntity {

    @NotNull
    @Column(name = "name")
    private String name;
}
