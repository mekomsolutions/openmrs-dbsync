package org.openmrs.eip.dbsync.management;

import org.openmrs.eip.Constants;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base class for management services.
 */
@Transactional(readOnly = true, transactionManager = Constants.MGT_TX_MGR_NAME)
public abstract class BaseManagementService implements Service {}
