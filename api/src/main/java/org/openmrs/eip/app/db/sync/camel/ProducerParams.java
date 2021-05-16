package org.openmrs.eip.app.db.sync.camel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
public class ProducerParams {

    private TableToSyncEnum tableToSync;

    private LocalDateTime lastSyncDate;

    private String uuid;

    private Long id;
}
