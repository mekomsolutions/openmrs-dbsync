package org.openmrs.eip.app.db.sync.repository;

import org.openmrs.eip.app.db.sync.entity.EncounterDiagnosis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EncounterDiagnosisRepository extends SyncEntityRepository<EncounterDiagnosis> {

    @Override
    @Query("select e from EncounterDiagnosis e " +
            "where e.dateChanged is null and e.dateCreated >= :lastSyncDate " +
            "or e.dateChanged >= :lastSyncDate")
    List<EncounterDiagnosis> findModelsChangedAfterDate(@Param("lastSyncDate") LocalDateTime lastSyncDate);
}
