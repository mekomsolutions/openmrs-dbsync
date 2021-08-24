package org.openmrs.eip.dbsync.utils;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Custom {@link com.fasterxml.jackson.databind.JsonSerializer} from {@link LocalTime} to String
 */
public class LocalTimeSerializer extends StdSerializer<LocalTime> {
	
	public LocalTimeSerializer() {
		this(LocalTime.class);
	}
	
	public LocalTimeSerializer(Class<LocalTime> ldt) {
		super(ldt);
	}
	
	@Override
	public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_TIME));
	}
	
}
