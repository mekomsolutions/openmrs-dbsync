package org.openmrs.eip.dbsync.entity.light;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openmrs.eip.dbsync.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class LightEntity extends BaseEntity {

    @NotNull
    @Column(name = "creator")
    private Long creator;

    @NotNull
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    public abstract void setMuted(boolean mute);

    public abstract void setDateMuted(LocalDateTime dateMuted);

    public abstract void setMuteReason(String muteReason);

    public abstract void setMutedBy(Long mutedBy);

    @Override
    public boolean wasModifiedAfter(final BaseEntity model) {
        return false;
    }
}
