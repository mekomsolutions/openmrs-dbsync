package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.SyncConstants.PLACEHOLDER_CLASS;
import static org.openmrs.eip.dbsync.SyncConstants.QUERY_SAVE_HASH;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.io.IOUtils;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.exception.ConflictsFoundException;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.management.hash.entity.BaseHashEntity;
import org.openmrs.eip.dbsync.management.hash.entity.ComplexObsHash;
import org.openmrs.eip.dbsync.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("complexObsProcessor")
public class ComplexObsProcessor implements Processor {
	
	protected static final Logger log = LoggerFactory.getLogger(ComplexObsProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		InputStream inputStream = exchange.getIn().getBody(InputStream.class);
		String filename = exchange.getMessage().getHeader(Exchange.FILE_NAME_ONLY, String.class);
		File complexObsFile = HashUtils.getComplexObsFile(filename);
		ProducerTemplate producerTemplate = SyncContext.getBean(ProducerTemplate.class);
		BaseHashEntity storedHash = HashUtils.getStoredHash(filename, ComplexObsHash.class, producerTemplate);
		Environment env = SyncContext.getBean(Environment.class);
		String complexObsDir = env.getProperty(SyncConstants.PROP_COMPLEX_OBS_DIR);
		
		if (complexObsFile == null) {
			if (log.isDebugEnabled()) {
				log.debug("Handling new complex obs file -> " + filename);
			}
			
			if (storedHash == null) {
				if (log.isDebugEnabled()) {
					log.debug("Inserting new hash for the incoming complex obs file contents");
				}
				
				try {
					storedHash = HashUtils.instantiateHashEntity(ComplexObsHash.class);
				}
				catch (Exception e) {
					throw new SyncException("Failed to create an instance of " + ComplexObsHash.class, e);
				}
				
				storedHash.setIdentifier(filename);
				storedHash.setDateCreated(LocalDateTime.now());
				
				if (log.isDebugEnabled()) {
					log.debug("Saving hash for the incoming complex obs file state");
				}
			} else {
				//This will typically happen if we inserted the hash but something went wrong before or during
				//saving of the complex obs and the message comes back from activemq
				log.info("Found existing hash for a new complex obs file, this could be a reattempt to save the new "
				        + "complex obs file where the hash was created but the save previously failed");
				
				storedHash.setDateChanged(LocalDateTime.now());
				
				if (log.isDebugEnabled()) {
					log.debug("Updating hash for the incoming complex obs file contents");
				}
			}
			
			byte[] contents = IOUtils.toByteArray(inputStream);
			storedHash.setHash(HashUtils.computeHashForBytes(contents));
			
			producerTemplate.sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, ComplexObsHash.class.getSimpleName()),
			    storedHash);
			
			if (log.isDebugEnabled()) {
				log.debug("Successfully saved the hash for the incoming complex obs file contents");
			}
			
			log.info("Saving complex obs file: " + filename);
			
			exchange.getMessage().setBody(new ByteArrayInputStream(contents));
			producerTemplate.send("file:" + complexObsDir, exchange);
			
		} else {
			if (log.isDebugEnabled()) {
				log.debug("Handling existing complex obs file -> " + complexObsFile);
			}
			
			if (storedHash == null) {
				//TODO Don't fail if hashes of the db and incoming payloads match
				throw new SyncException("Failed to find the existing hash for an existing complex obs file");
			}
			
			byte[] contents = IOUtils.toByteArray(inputStream);
			String newHash = HashUtils.computeHashForBytes(contents);
			String currentHash = HashUtils.computeHashForFile(complexObsFile);
			if (!currentHash.equals(storedHash.getHash())) {
				log.info("Payload: " + new String(contents));
				if (currentHash.equals(newHash)) {
					//This will typically happen if we update the entity but something goes wrong before or during
					//update of the hash and the event comes back as a retry item
					log.info("Stored hash differs from that of the existing complex obs file, ignoring this because the "
					        + "incoming and saved file contents match");
				} else {
					throw new ConflictsFoundException();
				}
			}
			
			log.info("Saving complex obs file: " + filename);
			
			exchange.getMessage().setBody(new ByteArrayInputStream(contents));
			producerTemplate.send("file:" + complexObsDir, exchange);
			
			storedHash.setHash(newHash);
			storedHash.setDateChanged(LocalDateTime.now());
			
			if (log.isDebugEnabled()) {
				log.debug("Updating hash for the incoming complex obs file contents");
			}
			
			producerTemplate.sendBody("jpa:" + ComplexObsHash.class.getSimpleName(), storedHash);
			
			if (log.isDebugEnabled()) {
				log.debug("Successfully updated the hash for the incoming complex obs file contents");
			}
		}
	}
	
}
