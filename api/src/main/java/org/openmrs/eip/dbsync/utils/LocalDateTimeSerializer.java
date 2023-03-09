package org.openmrs.eip.dbsync.utils;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Custom {@link com.fasterxml.jackson.databind.JsonSerializer} from {@link LocalDateTime} to String
 */
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
	
	public LocalDateTimeSerializer() {
		this(LocalDateTime.class);
	}
	
	public LocalDateTimeSerializer(Class<LocalDateTime> ldt) {
		super(ldt);
	}
	
	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(DateUtils.serialize(value));
	}
	
}
