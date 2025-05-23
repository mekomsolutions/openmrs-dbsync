package org.openmrs.eip.dbsync.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openmrs.eip.dbsync.entity.light.PersonLight;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "person_name")
@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "person_name_id"))
public class PersonName extends BaseChangeableDataEntity {

    @NotNull
    @Column(name = "preferred")
    private boolean preferred;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonLight person;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "given_name")
    private String givenName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "family_name_prefix")
    private String familyNamePrefix;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "family_name2")
    private String familyName2;

    @Column(name = "family_name_suffix")
    private String familyNameSuffix2;

    @Column(name = "degree")
    private String degree;
}
