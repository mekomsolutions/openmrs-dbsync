package org.openmrs.eip.dbsync.receiver.management;

import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.openmrs.eip.dbsync.management.hash.entity.BaseHashEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import jakarta.persistence.Table;
import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

public class ModifyDateDatatypeChangeSet implements CustomTaskChange {
	
	private static final Logger LOG = LoggerFactory.getLogger(ModifyDateDatatypeChangeSet.class);
	
	@Override
	public void execute(Database database) throws CustomChangeException {
		LOG.info("Running {}", getClass().getSimpleName());
		
		try {
			JdbcConnection conn = (JdbcConnection) database.getConnection();
			for (String tableName : getHashTableNames()) {
				updateColumnDataTypes(tableName, conn);
			}
		}
		catch (Exception e) {
			throw new CustomChangeException(
			        "Failed to update data types for the date_created and date_changed columns in the tables used to "
			                + "store hashes",
			        e);
		}
	}
	
	private void updateColumnDataTypes(String table, JdbcConnection conn) throws Exception {
		try (Statement s = conn.createStatement()) {
			LOG.info("Changing {}.date_created column datatype to DATETIME(3)", table);
			
			s.executeUpdate("ALTER TABLE " + table + " CHANGE COLUMN date_created date_created DATETIME(3) NOT NULL");
			
			LOG.info("Changing {}.date_changed column datatype to DATETIME(3)", table);
			
			s.executeUpdate(
			    "ALTER TABLE " + table + " CHANGE COLUMN date_changed date_changed DATETIME(3) NULL DEFAULT NULL");
		}
	}
	
	protected Set<String> getHashTableNames() throws Exception {
		PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
		String locationPattern = "classpath:" + BaseHashEntity.class.getPackage().getName().replace(".", "/")
		        + "/*Hash.class";
		
		LOG.info("Loading all subclasses of {} matching the location pattern {}", BaseHashEntity.class.getName(),
		    locationPattern);
		
		Resource[] resources = scanner.getResources(locationPattern);
		TypeFilter filter = new AssignableTypeFilter(BaseHashEntity.class);
		final MetadataReaderFactory readerFactory = new SimpleMetadataReaderFactory();
		Set<String> tableNames = new HashSet();
		for (Resource resource : resources) {
			final MetadataReader reader = readerFactory.getMetadataReader(resource);
			if (filter.match(reader, readerFactory)) {
				final String table = reader.getAnnotationMetadata().getAnnotationAttributes(Table.class.getName())
				        .get("name").toString();
				tableNames.add(table);
			}
		}
		
		return tableNames;
	}
	
	@Override
	public String getConfirmationMessage() {
		return getClass().getSimpleName() + " completed successfully";
	}
	
	@Override
	public void setUp() throws SetupException {
	}
	
	@Override
	public void setFileOpener(ResourceAccessor resourceAccessor) {
	}
	
	@Override
	public ValidationErrors validate(Database database) {
		return null;
	}
	
}
