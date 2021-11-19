package org.openmrs.eip.dbsync.sender;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Test;

public class PersonSenderTest extends BaseSenderTest {
	
	@Test
	public void shouldProcessAPersonInsertEvent() throws Exception {
		producerTemplate.sendBody("openmrs-watcher:odoo", null);
		try (Connection c = openmrsDataSource.getConnection(); Statement s = c.createStatement()) {
			s.executeUpdate(
			    "INSERT INTO person (gender,creator,date_created,birthdate,birthtime,birthdate_estimated,dead,voided,deathdate_estimated,uuid) "
			            + "VALUES('M', 1, now(), now(), now(), 0, 0, 0, 0,'test-uuid')");
		}
		
	}
	
	@Test
	public void shouldProcessAPersonUpdateEvent() {
		
	}
	
	@Test
	public void shouldProcessAPersonDeleteEvent() {
		
	}
	
}
