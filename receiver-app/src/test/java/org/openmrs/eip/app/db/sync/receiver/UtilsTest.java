package org.openmrs.eip.app.db.sync.receiver;

import static java.util.Arrays.stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.openmrs.eip.app.db.sync.model.DrugOrderModel;
import org.openmrs.eip.app.db.sync.model.OrderModel;
import org.openmrs.eip.app.db.sync.model.PatientModel;
import org.openmrs.eip.app.db.sync.model.PersonModel;
import org.openmrs.eip.app.db.sync.model.TestOrderModel;
import org.openmrs.eip.app.db.sync.model.VisitModel;

public class UtilsTest {
	
	@Test
	public void getListOfModelClassHierarchy_shouldReturnSubclassAndSuperClassNames() {
		String className = VisitModel.class.getName();
		List<String> classes = Utils.getListOfModelClassHierarchy(className);
		assertEquals(1, classes.size());
		assertTrue(classes.contains(className));
		
		className = PatientModel.class.getName();
		classes = Utils.getListOfModelClassHierarchy(className);
		assertEquals(2, classes.size());
		assertTrue(classes.contains(className));
		assertTrue(classes.contains(PersonModel.class.getName()));
		
		className = PersonModel.class.getName();
		classes = Utils.getListOfModelClassHierarchy(className);
		assertEquals(2, classes.size());
		assertTrue(classes.contains(className));
		assertTrue(classes.contains(PatientModel.class.getName()));
		
		className = OrderModel.class.getName();
		classes = Utils.getListOfModelClassHierarchy(className);
		assertEquals(3, classes.size());
		assertTrue(classes.contains(className));
		assertTrue(classes.contains(TestOrderModel.class.getName()));
		assertTrue(classes.contains(DrugOrderModel.class.getName()));
		
		className = TestOrderModel.class.getName();
		classes = Utils.getListOfModelClassHierarchy(className);
		assertEquals(2, classes.size());
		assertTrue(classes.contains(className));
		assertTrue(classes.contains(OrderModel.class.getName()));
		
		className = DrugOrderModel.class.getName();
		classes = Utils.getListOfModelClassHierarchy(className);
		assertEquals(2, classes.size());
		assertTrue(classes.contains(className));
		assertTrue(classes.contains(OrderModel.class.getName()));
	}
	
	@Test
	public void getModelClassesInHierarchy_shouldReturnCommaSeparatedListOfSubclassAndSuperClassNames() {
		String className = VisitModel.class.getName();
		List<String> classes = stream(Utils.getModelClassesInHierarchy(className).split(",")).collect(Collectors.toList());
		assertEquals(1, classes.size());
		assertTrue(classes.contains("'" + className + "'"));
		
		className = PatientModel.class.getName();
		classes = stream(Utils.getModelClassesInHierarchy(className).split(",")).collect(Collectors.toList());
		assertEquals(2, classes.size());
		assertTrue(classes.contains("'" + className + "'"));
		assertTrue(classes.contains("'" + PersonModel.class.getName() + "'"));
		
		className = PersonModel.class.getName();
		classes = stream(Utils.getModelClassesInHierarchy(className).split(",")).collect(Collectors.toList());
		assertEquals(2, classes.size());
		assertTrue(classes.contains("'" + className + "'"));
		assertTrue(classes.contains("'" + PatientModel.class.getName() + "'"));
		
		className = OrderModel.class.getName();
		classes = stream(Utils.getModelClassesInHierarchy(className).split(",")).collect(Collectors.toList());
		assertEquals(3, classes.size());
		assertTrue(classes.contains("'" + className + "'"));
		assertTrue(classes.contains("'" + TestOrderModel.class.getName() + "'"));
		assertTrue(classes.contains("'" + DrugOrderModel.class.getName() + "'"));
		
		className = TestOrderModel.class.getName();
		classes = stream(Utils.getModelClassesInHierarchy(className).split(",")).collect(Collectors.toList());
		assertEquals(2, classes.size());
		assertTrue(classes.contains("'" + className + "'"));
		assertTrue(classes.contains("'" + OrderModel.class.getName() + "'"));
		
		className = DrugOrderModel.class.getName();
		classes = stream(Utils.getModelClassesInHierarchy(className).split(",")).collect(Collectors.toList());
		assertEquals(2, classes.size());
		assertTrue(classes.contains("'" + className + "'"));
		assertTrue(classes.contains("'" + OrderModel.class.getName() + "'"));
	}
	
}
