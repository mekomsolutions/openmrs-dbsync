package org.openmrs.eip.app.db.sync.utils;

import java.io.IOException;
import java.time.LocalTime;
import java.time.OffsetTime;
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
		gen.writeString(value.atOffset(OffsetTime.now().getOffset()).format(DateTimeFormatter.ISO_OFFSET_TIME));
	}
	
}
