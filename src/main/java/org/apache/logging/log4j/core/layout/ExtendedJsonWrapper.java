package org.apache.logging.log4j.core.layout;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.core.LogEvent;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ExtendedJsonSerializer.class)
public class ExtendedJsonWrapper {

    private final LogEvent origin;
    private final Map<String, Object> mixed;

    public ExtendedJsonWrapper(LogEvent origin) {
        this(origin, new HashMap<String, Object>());
    }
    
    public ExtendedJsonWrapper(LogEvent origin, Map<String, Object> mixedIn) {
        this.origin = origin;
        this.mixed = mixedIn;
    }

    public void add(String key, Object value) {
        this.mixed.put(key, value);
    }

    public Map<String, Object> getMixed() {
        return mixed;
    }

    public LogEvent getOrigin() {
        return origin;
    }

}