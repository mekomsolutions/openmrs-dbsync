package org.openmrs.eip.app.db.sync.receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openmrs.eip.app.db.sync.model.DrugOrderModel;
import org.openmrs.eip.app.db.sync.model.OrderModel;
import org.openmrs.eip.app.db.sync.model.PatientModel;
import org.openmrs.eip.app.db.sync.model.PersonModel;
import org.openmrs.eip.app.db.sync.model.TestOrderModel;

public class Utils {
	
	/**
	 * Gets comma-separated list of model class names surrounded with apostrophes that are subclasses or
	 * superclasses of the specified class name.
	 *
	 * @param modelClass the model class to inspect
	 * @return a list of model class names
	 */
	public static List<String> getListOfModelClassHierarchy(String modelClass) {
		//TODO This logic should be extensible
		List<String> tables = new ArrayList();
		tables.add(modelClass);
		if (PersonModel.class.getName().equals(modelClass) || PatientModel.class.getName().equals(modelClass)) {
			tables.add(
			    PersonModel.class.getName().equals(modelClass) ? PatientModel.class.getName() : PersonModel.class.getName());
		} else if (OrderModel.class.getName().equals(modelClass)) {
			tables.add(TestOrderModel.class.getName());
			tables.add(DrugOrderModel.class.getName());
		} else if (TestOrderModel.class.getName().equals(modelClass) || DrugOrderModel.class.getName().equals(modelClass)) {
			tables.add(OrderModel.class.getName());
		}
		
		return tables;
	}
	
	/**
	 * Gets all the model classes that are subclasses or superclass of the specified class name.
	 *
	 * @param modelClass the model class to inspect
	 * @return a comma-separated list of model class names
	 */
	public static String getModelClassesInHierarchy(String modelClass) {
		List<String> classes = getListOfModelClassHierarchy(modelClass);
		return String.join(",", classes.stream().map(clazz -> "'" + clazz + "'").collect(Collectors.toList()));
	}
	
}
