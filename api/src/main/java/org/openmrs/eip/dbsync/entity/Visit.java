package org.openmrs.eip.dbsync.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.LocationLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.VisitTypeLight;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "visit")
@AttributeOverride(name = "id", column = @Column(name = "visit_id"))
public class Visit extends BaseChangeableDataEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientLight patient;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "visit_type_id")
    private VisitTypeLight visitType;

    @NotNull
    @Column(name = "date_started")
    private LocalDateTime dateStarted;

    @Column(name = "date_stopped")
    private LocalDateTime dateStopped;

    @ManyToOne
    @JoinColumn(name = "indication_concept_id")
    private ConceptLight indicationConcept;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationLight location;
}
