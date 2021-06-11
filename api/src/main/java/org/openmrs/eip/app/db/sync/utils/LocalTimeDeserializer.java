package org.openmrs.eip.app.db.sync.utils;

import java.io.IOException;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Custom {@link com.fasterxml.jackson.databind.JsonDeserializer} from String {@link LocalTime}
 */
public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {
	
	public LocalTimeDeserializer() {
		this(LocalTime.class);
	}
	
	public LocalTimeDeserializer(Class<LocalTime> ldt) {
		super(ldt);
	}
	
	@Override
	public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return OffsetTime.parse(p.getText(), DateTimeFormatter.ISO_OFFSET_TIME)
		        .withOffsetSameInstant(OffsetTime.now().getOffset()).toLocalTime();
	}
	
}
