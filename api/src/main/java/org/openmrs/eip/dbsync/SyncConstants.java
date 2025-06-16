package org.openmrs.eip.dbsync;

import org.openmrs.eip.dbsync.utils.SyncUtils;

public class SyncConstants {
	
	public static final String SYNC_MODE_BEAN_NAME = "dbSyncMode";
	
	public static final String OPENMRS_DATASOURCE_NAME = "openmrsDataSource";
	
	public static final String VALUE_SITE_SEPARATOR = "|";
	
	public static final String DEFAULT_RETIRE_REASON = "Retired because it was deleted in the site of origin";
	
	public static final String PROP_SYNC_EXCLUDE = "db-sync.excludedEntities";
	
	public static final String PROP_OPENMRS_USER = "openmrs.username";
	
	public static final String PROP_COMPLEX_OBS_DIR = "openmrs.complex.obs.data.directory";
	
	public static final String DAEMON_USER_UUID = "A4F30A1B-5EB9-11DF-A648-37A07F9C90FB";
	
	public static final String PLACEHOLDER_CLASS = "[class]";
	
	public static final String QUERY_SAVE_HASH = "jpa:" + PLACEHOLDER_CLASS;
	
	public static final String HASH_DELETED = "DELETED";
	
	public static final String OPENMRS_ROOT_PGK = "org.openmrs";
	
	public static final String VERSION = SyncUtils.getDbSyncVersion();
	
}
