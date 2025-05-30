package org.openmrs.eip.dbsync.service.light;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.MockedLightEntity;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

public class AbstractLightServiceTest {
	
	@Mock
	private OpenmrsRepository<MockedLightEntity> repository;
	
	private MockedLightService service;
	
	private static final String UUID = "uuid";
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		service = new MockedLightService(repository);
	}
	
	@Test
	public void getOrInitEntity_should_return_null() {
		// Given
		
		// When
		MockedLightEntity result = service.getOrInitEntity(null);
		
		// Then
		assertNull(result);
	}
	
	@Test
	public void getOrInitEntity_should_create_entity() {
		// Given
		when(repository.findByUuid(UUID)).thenReturn(null);
		MockedLightEntity expectedEntity = getExpectedEntity(UUID);
		when(repository.save(expectedEntity)).thenReturn(expectedEntity);
		
		// When
		MockedLightEntity result = service.getOrInitEntity(UUID);
		
		// Then
		assertEquals(expectedEntity, result);
		verify(repository).save(expectedEntity);
	}
	
	@Test
	public void getOrInitEntity_should_return_entity() {
		// Given
		MockedLightEntity userEty = getExpectedEntity(UUID);
		when(repository.findByUuid(UUID)).thenReturn(userEty);
		when(repository.save(userEty)).thenReturn(userEty);
		
		// When
		MockedLightEntity result = service.getOrInitEntity(UUID);
		
		// Then
		verify(repository, never()).save(any());
		assertEquals(userEty, result);
	}
	
	@Test
	public void getOrInitPlaceholderEntity_should_create_placeholder() {
		// Given
		when(repository.findByUuid(UUID)).thenReturn(null);
		when(repository.findByUuid("PLACEHOLDER_MOCKED_LIGHT_ENTITY")).thenReturn(null);
		MockedLightEntity expectedEntity = getExpectedEntity("PLACEHOLDER_MOCKED_LIGHT_ENTITY");
		when(repository.save(expectedEntity)).thenReturn(expectedEntity);
		
		// When
		MockedLightEntity result = service.getOrInitPlaceholderEntity();
		
		// Then
		assertEquals(expectedEntity, result);
		verify(repository).save(expectedEntity);
	}
	
	@Test
	public void getOrInitPlaceholderEntity_should_return_entity() {
		// Given
		MockedLightEntity userEty = getExpectedEntity("PLACEHOLDER_MOCKED_LIGHT_ENTITY");
		when(repository.findByUuid("PLACEHOLDER_MOCKED_LIGHT_ENTITY")).thenReturn(userEty);
		when(repository.save(userEty)).thenReturn(userEty);
		
		// When
		MockedLightEntity result = service.getOrInitPlaceholderEntity();
		
		// Then
		verify(repository, never()).save(any());
		assertEquals(userEty, result);
	}
	
	private MockedLightEntity getExpectedEntity(final String uuid) {
		MockedLightEntity mockedLightEntity = new MockedLightEntity(1L, uuid);
		mockedLightEntity.setVoided(true);
		mockedLightEntity.setVoidReason(AbstractLightService.DEFAULT_VOID_REASON);
		mockedLightEntity.setDateVoided(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
		mockedLightEntity.setVoidedBy(1L);
		return mockedLightEntity;
	}
}
