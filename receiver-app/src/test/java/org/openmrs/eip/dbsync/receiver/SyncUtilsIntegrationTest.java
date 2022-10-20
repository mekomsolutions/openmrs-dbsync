package org.openmrs.eip.dbsync.receiver;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.eip.dbsync.management.hash.repository.BaseHashRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.SyncUtils;

/**
 * TODO Move this test class to the correct package once we move HashUtils to receiver-app sub
 * project
 */
public class SyncUtilsIntegrationTest extends BaseReceiverDbDrivenTest {
	
	@Test
	public void checkForHashRepositories() {
		for (TableToSyncEnum tableToSyncEnum : TableToSyncEnum.values()) {
			if (!SyncUtils.getSyncedTableToSyncEnums().contains(tableToSyncEnum)) {
				continue;
			}
			Assert.assertNotNull(SyncUtils.getJpaRepository(tableToSyncEnum.getHashClass(), BaseHashRepository.class));
		}
	}
	
}
