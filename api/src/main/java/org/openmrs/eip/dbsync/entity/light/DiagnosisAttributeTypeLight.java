package org.openmrs.eip.dbsync.entity.light;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "diagnosis_attribute_type")
@AttributeOverride(name = "id", column = @Column(name = "diagnosis_attribute_type_id"))
public class DiagnosisAttributeTypeLight extends AttributeTypeLight {}
