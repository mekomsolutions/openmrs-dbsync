package org.openmrs.eip.dbsync.receiver.management;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModifyDateDatatypeChangeSetTest {
	
	@Test
	public void shouldReturnFalseForANonSubclassTable() throws Exception {
		Set<String> tables = new ModifyDateDatatypeChangeSet().getHashTableNames();
		Assertions.assertEquals(30, tables.size());
		for (String tableName : tables) {
			Assertions.assertTrue(tableName.endsWith("_hash"));
		}
	}
}
