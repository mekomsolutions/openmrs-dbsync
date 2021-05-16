package org.openmrs.eip.app.db.sync.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncModel {

    private Class<? extends BaseModel> tableToSyncModelClass;

    private BaseModel model;
}
