package org.openmrs.eip.dbsync.receiver;

import static java.util.Collections.singletonMap;
import static org.apache.commons.lang3.reflect.MethodUtils.invokeMethod;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.openmrs.eip.DatabaseOperation;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.receiver.management.entity.ReceiverRetryQueueItem;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncMessage;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.utils.DateUtils;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReceiverUtilsTest {
	
	@Test
	public void createSyncedMessage_shouldCreateASyncedMessageFromASyncMessage() throws Exception {
		PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(SyncMessage.class);
		SyncMessage syncMessage = new SyncMessage();
		syncMessage.setId(1L);
		syncMessage.setDateCreated(new Date());
		syncMessage.setIdentifier("uuid");
		syncMessage.setEntityPayload("payload");
		syncMessage.setModelClassName(PersonModel.class.getName());
		syncMessage.setDbSyncVersion("1.0");
		DatabaseOperation op = DatabaseOperation.c;
		LocalDateTime date = LocalDateTime.now();
		String dateSent = DateUtils.serialize(date);
		Map<String, Object> metadata = new HashMap();
		metadata.put("operation", op);
		metadata.put("dateSent", dateSent);
		syncMessage.setEntityPayload(new ObjectMapper().writeValueAsString(singletonMap("metadata", metadata)));
		long timestamp = System.currentTimeMillis();
		
		SyncedMessage msg = ReceiverUtils.createSyncedMessage(syncMessage);
		
		assertNull(msg.getId());
		assertTrue(msg.getDateCreated().getTime() == timestamp || msg.getDateCreated().getTime() > timestamp);
		assertEquals(syncMessage.getDateCreated(), msg.getDateReceived());
		assertEquals(op, msg.getOperation());
		assertEquals(date, msg.getDateSent());
		Set<String> ignored = new HashSet();
		ignored.add("id");
		ignored.add("class");
		ignored.add("dateCreated");
		for (PropertyDescriptor descriptor : descriptors) {
			if (ignored.contains(descriptor.getName())) {
				continue;
			}
			
			String getter = descriptor.getReadMethod().getName();
			assertEquals(invokeMethod(syncMessage, getter), invokeMethod(msg, getter));
		}
	}
	
	@Test
	public void createSyncedMessageFromRetry_shouldCreateASyncedMessageFromARetryQueueItem() throws Exception {
		PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(ReceiverRetryQueueItem.class);
		ReceiverRetryQueueItem retry = new ReceiverRetryQueueItem();
		retry.setId(1L);
		retry.setDateCreated(new Date());
		retry.setIdentifier("uuid");
		retry.setEntityPayload("payload");
		retry.setModelClassName(PersonModel.class.getName());
		retry.setExceptionType(EIPException.class.getName());
		retry.setMessage(EIPException.class.getName());
		retry.setAttemptCount(1);
		retry.setDateChanged(new Date());
		DatabaseOperation op = DatabaseOperation.c;
		LocalDateTime date = LocalDateTime.now();
		String dateSent = DateUtils.serialize(date);
		Map<String, Object> metadata = new HashMap();
		metadata.put("operation", op);
		metadata.put("dateSent", dateSent);
		retry.setEntityPayload(new ObjectMapper().writeValueAsString(singletonMap("metadata", metadata)));
		long timestamp = System.currentTimeMillis();
		
		SyncedMessage msg = null;//ReceiverUtils.createSyncedMessageFromRetry(retry);
		
		assertNull(msg.getId());
		assertTrue(msg.getDateCreated().getTime() == timestamp || msg.getDateCreated().getTime() > timestamp);
		assertEquals(op, msg.getOperation());
		assertEquals(date, msg.getDateSent());
		Set<String> ignored = new HashSet();
		ignored.add("id");
		ignored.add("class");
		ignored.add("dateCreated");
		ignored.add("exceptionType");
		ignored.add("message");
		ignored.add("attemptCount");
		ignored.add("dateChanged");
		for (PropertyDescriptor descriptor : descriptors) {
			if (ignored.contains(descriptor.getName())) {
				continue;
			}
			
			String getter = descriptor.getReadMethod().getName();
			assertEquals(invokeMethod(retry, getter), invokeMethod(msg, getter));
		}
	}
	
}
