package org.openmrs.eip.app.db.sync;

import org.openmrs.eip.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(scanBasePackages = "org.openmrs.eip")
public class Application {
	
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	public PropertySource getReceiverPropertySource(ConfigurableEnvironment env) {
		Map<String, Object> props = new HashMap();
		props.put(Constants.PROP_PACKAGES_TO_SCAN, new String[] { "org.openmrs.eip.mysql.watcher.management.entity",
		        "org.apache.camel.processor.idempotent.jpa", "org.openmrs.eip.app.db.sync.receiver.management.entity" });
		
		PropertySource customPropSource = new MapPropertySource("dbSyncPropSource", props);
		
		env.getPropertySources().addLast(customPropSource);
		
		return customPropSource;
	}
	
}
