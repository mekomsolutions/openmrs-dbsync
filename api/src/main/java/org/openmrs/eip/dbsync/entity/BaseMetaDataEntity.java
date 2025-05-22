package org.openmrs.eip.dbsync.entity;

import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.utils.DateUtils;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static java.util.Collections.singleton;

/**
 * OpenMRS data model distinguishes between data and metadata, please refer to the javadocs in OpenMRS on
 * BaseOpenmrsData and BaseOpenmrsMetadata classes, this is the superclass for classes in this project that represent
 * metadata entities.
 */
@MappedSuperclass
public abstract class BaseMetaDataEntity extends BaseCreatableEntity {

    @ManyToOne
    @JoinColumn(name = "retired_by")
    private UserLight retiredBy;

    @Column(name = "date_retired")
    private LocalDateTime dateRetired;

    @Column(name = "retire_reason")
    private String retireReason;

    @NotNull
    @Column(name = "retired")
    private boolean retired;

    @Override
    public boolean wasModifiedAfter(BaseEntity entity) {
        BaseMetaDataEntity other = (BaseMetaDataEntity) entity;
        return DateUtils.containsLatestDate(singleton(getDateRetired()), singleton(other.getDateRetired()));
    }

    /**
     * Gets the retiredBy
     *
     * @return the retiredBy
     */
    public UserLight getRetiredBy() {
        return retiredBy;
    }

    /**
     * Sets the retiredBy
     *
     * @param retiredBy the retiredBy to set
     */
    public void setRetiredBy(UserLight retiredBy) {
        this.retiredBy = retiredBy;
    }

    /**
     * Gets the dateRetired
     *
     * @return the dateRetired
     */
    public LocalDateTime getDateRetired() {
        return dateRetired;
    }

    /**
     * Sets the dateRetired
     *
     * @param dateRetired the dateRetired to set
     */
    public void setDateRetired(LocalDateTime dateRetired) {
        this.dateRetired = dateRetired;
    }

    /**
     * Gets the retireReason
     *
     * @return the retireReason
     */
    public String getRetireReason() {
        return retireReason;
    }

    /**
     * Sets the retireReason
     *
     * @param retireReason the retireReason to set
     */
    public void setRetireReason(String retireReason) {
        this.retireReason = retireReason;
    }

    /**
     * Gets the retired
     *
     * @return the retired
     */
    public boolean isRetired() {
        return retired;
    }

    /**
     * Sets the retired
     *
     * @param retired the retired to set
     */
    public void setRetired(boolean retired) {
        this.retired = retired;
    }

}
