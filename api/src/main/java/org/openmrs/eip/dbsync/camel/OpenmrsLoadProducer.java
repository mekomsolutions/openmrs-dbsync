package org.openmrs.eip.dbsync.camel;

import static org.openmrs.eip.dbsync.SyncConstants.USERNAME_SITE_SEPARATOR;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.service.facade.EntityServiceFacade;
import org.springframework.context.ApplicationContext;

public class OpenmrsLoadProducer extends AbstractOpenmrsProducer {
	
	public OpenmrsLoadProducer(final OpenmrsEndpoint endpoint, final ApplicationContext applicationContext,
	    final ProducerParams params) {
		super(endpoint, applicationContext, params);
	}
	
	@Override
	public void process(final Exchange exchange) {
		EntityServiceFacade entityServiceFacade = (EntityServiceFacade) applicationContext.getBean("entityServiceFacade");
		SyncModel syncModel = exchange.getIn().getBody(SyncModel.class);
		TableToSyncEnum tableToSyncEnum = TableToSyncEnum.getTableToSyncEnum(syncModel.getTableToSyncModelClass());
		
		if ("d".equals(syncModel.getMetadata().getOperation())) {
			entityServiceFacade.delete(tableToSyncEnum, syncModel.getModel().getUuid());
		} else {
			String siteId = syncModel.getMetadata().getSourceIdentifier();
			if (syncModel.getModel() instanceof UserModel) {
				UserModel userModel = (UserModel) syncModel.getModel();
				userModel.setUsername(userModel.getUsername() + USERNAME_SITE_SEPARATOR + siteId);
			} else if (syncModel.getModel() instanceof ProviderModel) {
				ProviderModel providerModel = (ProviderModel) syncModel.getModel();
				if (StringUtils.isNotBlank(providerModel.getIdentifier())) {
					providerModel.setIdentifier(providerModel.getIdentifier() + USERNAME_SITE_SEPARATOR + siteId);
				}
			}
			
			entityServiceFacade.saveModel(tableToSyncEnum, syncModel.getModel());
		}
	}
	
}
