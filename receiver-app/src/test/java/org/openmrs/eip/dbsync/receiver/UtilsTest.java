package org.openmrs.eip.dbsync.receiver;

import static java.util.Arrays.stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.openmrs.eip.dbsync.SyncConstants.PROP_SYNC_EXCLUDE;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.model.DrugOrderModel;
import org.openmrs.eip.dbsync.model.OrderModel;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.PersonNameModel;
import org.openmrs.eip.dbsync.model.TestOrderModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.model.VisitModel;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.core.env.Environment;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SyncContext.class)
public class UtilsTest {
	
	@Before
	public void setup() {
		Whitebox.setInternalState(Utils.class, "typeAndIdsToExcludeMap", (Object) null);
	}
	
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
	
	@Test
	public void skipSync_shouldReturnFalseForANonExcludedEntity() {
		Environment mockEnv = Mockito.mock(Environment.class);
		PowerMockito.mockStatic(SyncContext.class);
		when(SyncContext.getBean(Environment.class)).thenReturn(mockEnv);
		when(mockEnv.getProperty(PROP_SYNC_EXCLUDE)).thenReturn(null);
		assertFalse(Utils.skipSync(UserModel.class.getName(), "some-uuid"));
	}
	
	@Test
	public void skipSync_shouldReturnTrueForAnExcludedEntity() {
		Environment mockEnv = Mockito.mock(Environment.class);
		PowerMockito.mockStatic(SyncContext.class);
		when(SyncContext.getBean(Environment.class)).thenReturn(mockEnv);
		final String id1 = "entity-uuid-1";
		final String id2 = "entity-uuid-2";
		final String id3 = "entity-uuid-3";
		final String toExclude = "person:" + id1 + "," + "person:" + id2 + ",person_name:" + id3;
		when(mockEnv.getProperty(PROP_SYNC_EXCLUDE)).thenReturn(toExclude);
		assertTrue(Utils.skipSync(UserModel.class.getName(), SyncConstants.DAEMON_USER_UUID));
		assertTrue(Utils.skipSync(PersonModel.class.getName(), id1));
		assertTrue(Utils.skipSync(PersonModel.class.getName(), id2));
		assertTrue(Utils.skipSync(PersonNameModel.class.getName(), id3));
	}
	
}
