package org.openmrs.eip.dbsync.entity.light;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "patient_identifier_type")
@AttributeOverride(name = "id", column = @Column(name = "patient_identifier_type_id"))
public class PatientIdentifierTypeLight extends RetireableLightEntity {

    @NotNull
    private String name;

    @NotNull
    private Boolean required;

    @NotNull
    @Column(name="check_digit")
    private Boolean checkDigit;

}
