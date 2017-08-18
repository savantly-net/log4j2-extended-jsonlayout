package org.apache.logging.log4j.core.layout;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ExtendedJsonSerializer.class)
public class ExtendedJsonWrapper {

    private final Object origin;
    private final Map<String, String> mixed = new HashMap<>();

    @JsonCreator
    public ExtendedJsonWrapper(@JsonProperty("origin") Object origin) {
        this.origin = origin;
    }

    public void add(String key, String value) {
        this.mixed.put(key, value);
    }

    public Map<String, String> getMixed() {
        return mixed;
    }

    public Object getOrigin() {
        return origin;
    }

}