package org.openmrs.eip.dbsync.entity.light;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
