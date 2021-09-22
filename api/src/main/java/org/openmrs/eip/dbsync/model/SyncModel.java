package org.openmrs.eip.dbsync.model;

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
	
	private SyncMetadata metadata;
	
	@Override
	public String toString() {
		return "{tableToSyncModelClass=" + tableToSyncModelClass + ", identifier=" + model.getUuid() + ", metadata="
		        + metadata + "}";
	}
	
}
