package org.openmrs.eip.dbsync.entity.light;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "patient")
@PrimaryKeyJoinColumn(name = "patient_id")
public class PatientLight extends PersonLight {

    @NotNull
    @Column(name = "allergy_status")
    private String allergyStatus;

    @NotNull
    @Column(name = "creator")
    private Long patientCreator;

    @NotNull
    @Column(name = "date_created")
    private LocalDateTime patientDateCreated;

    @NotNull
    @Column(name = "voided")
    private boolean patientVoided;

    @Column(name = "voided_by")
    private Long patientVoidedBy;

    @Column(name = "date_voided")
    private LocalDateTime patientDateVoided;

    @Column(name = "void_reason")
    private String patientVoidReason;
    
}
