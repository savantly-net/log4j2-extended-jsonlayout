package com.savantly.log4j2;

import java.io.IOException;

import org.apache.logging.log4j.Level;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LevelDeserializer extends StdDeserializer<Level> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LevelDeserializer() {
		this(null);
	}
	
    public LevelDeserializer(Class<?> vc) { 
        super(vc); 
    }

	@Override
	public Level deserialize(JsonParser jp, DeserializationContext ctx) throws IOException, JsonProcessingException {
		String value = jp.getCodec().readValue(jp, String.class);
 
        return Level.getLevel(value);
	}
}
