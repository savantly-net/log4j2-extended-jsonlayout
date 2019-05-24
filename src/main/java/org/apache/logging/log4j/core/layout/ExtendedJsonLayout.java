/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.logging.log4j.core.layout;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.impl.MutableLogEvent;

import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Appends a series of JSON events as strings serialized as bytes.
 *
 * <h3>Complete well-formed JSON vs. fragment JSON</h3>
 * <p>
 * If you configure {@code complete="true"}, the appender outputs a well-formed JSON document. By default, with
 * {@code complete="false"}, you should include the output as an <em>external file</em> in a separate file to form a
 * well-formed JSON document.
 * </p>
 * <p>
 * A well-formed JSON event follows this pattern:
 * </p>
 *
 * <pre>
 * {
  "timeMillis": 1,
  "thread": "MyThreadName",
  "level": "DEBUG",
  "loggerName": "a.B",
  "marker": {
    "name": "Marker1",
    "parents": [{
      "name": "ParentMarker1",
      "parents": [{
        "name": "GrandMotherMarker"
      }, {
        "name": "GrandFatherMarker"
      }]
    }, {
      "name": "GrandFatherMarker"
    }]
  },
  "message": "Msg",
  "thrown": {
    "cause": {
      "commonElementCount": 27,
      "extendedStackTrace": [{
        "class": "org.apache.logging.log4j.core.layout.LogEventFixtures",
        "method": "createLogEvent",
        "file": "LogEventFixtures.java",
        "line": 53,
        "exact": false,
        "location": "test-classes/",
        "version": "?"
      }],
      "localizedMessage": "testNPEx",
      "message": "testNPEx",
      "name": "java.lang.NullPointerException"
    },
    "commonElementCount": 0,
    "extendedStackTrace": [{
      "class": "org.apache.logging.log4j.core.layout.LogEventFixtures",
      "method": "createLogEvent",
      "file": "LogEventFixtures.java",
      "line": 56,
      "exact": true,
      "location": "test-classes/",
      "version": "?"
    }, {
      "class": "org.apache.logging.log4j.core.layout.JsonLayoutTest",
      "method": "testAllFeatures",
      "file": "JsonLayoutTest.java",
      "line": 105,
      "exact": true,
      "location": "test-classes/",
      "version": "?"
    }, {
      "class": "org.apache.logging.log4j.core.layout.JsonLayoutTest",
      "method": "testLocationOnCompactOnMdcOn",
      "file": "JsonLayoutTest.java",
      "line": 268,
      "exact": true,
      "location": "test-classes/",
      "version": "?"
    }, {
      "class": "sun.reflect.NativeMethodAccessorImpl",
      "method": "invoke",
      "line": -1,
      "exact": false,
      "location": "?",
      "version": "1.7.0_55"
    }],
    "localizedMessage": "testIOEx",
    "message": "testIOEx",
    "name": "java.io.IOException",
    "suppressed": [{
      "commonElementCount": 0,
      "extendedStackTrace": [{
        "class": "org.apache.logging.log4j.core.layout.LogEventFixtures",
        "method": "createLogEvent",
        "file": "LogEventFixtures.java",
        "line": 57,
        "exact": true,
        "location": "test-classes/",
        "version": "?"
      }, {
        "class": "org.apache.logging.log4j.core.layout.JsonLayoutTest",
        "method": "testAllFeatures",
        "file": "JsonLayoutTest.java",
        "line": 105,
        "exact": true,
        "location": "test-classes/",
        "version": "?"
      }, {
        "class": "org.apache.logging.log4j.core.layout.JsonLayoutTest",
        "method": "testLocationOnCompactOnMdcOn",
        "file": "JsonLayoutTest.java",
        "line": 268,
        "exact": true,
        "location": "test-classes/",
        "version": "?"
      }, {
        "class": "sun.reflect.NativeMethodAccessorImpl",
        "method": "invoke",
        "line": -1,
        "exact": false,
        "location": "?",
        "version": "1.7.0_55"
      }],
      "localizedMessage": "I am suppressed exception 1",
      "message": "I am suppressed exception 1",
      "name": "java.lang.IndexOutOfBoundsException"
    }, {
      "commonElementCount": 0,
      "extendedStackTrace": [{
        "class": "org.apache.logging.log4j.core.layout.LogEventFixtures",
        "method": "createLogEvent",
        "file": "LogEventFixtures.java",
        "line": 58,
        "exact": true,
        "location": "test-classes/",
        "version": "?"
      }, {
        "class": "org.apache.logging.log4j.core.layout.JsonLayoutTest",
        "method": "testAllFeatures",
        "file": "JsonLayoutTest.java",
        "line": 105,
        "exact": true,
        "location": "test-classes/",
        "version": "?"
      }, {
        "class": "org.apache.logging.log4j.core.layout.JsonLayoutTest",
        "method": "testLocationOnCompactOnMdcOn",
        "file": "JsonLayoutTest.java",
        "line": 268,
        "exact": true,
        "location": "test-classes/",
        "version": "?"
      }, {
        "class": "sun.reflect.NativeMethodAccessorImpl",
        "method": "invoke",
        "line": -1,
        "exact": false,
        "location": "?",
        "version": "1.7.0_55"
      }],
      "localizedMessage": "I am suppressed exception 2",
      "message": "I am suppressed exception 2",
      "name": "java.lang.IndexOutOfBoundsException"
    }]
  },
  "loggerFQCN": "f.q.c.n",
  "endOfBatch": false,
  "contextMap": [{
    "key": "MDC.B",
    "value": "B_Value"
  }, {
    "key": "MDC.A",
    "value": "A_Value"
  }],
  "contextStack": ["stack_msg1", "stack_msg2"],
  "source": {
    "class": "org.apache.logging.log4j.core.layout.LogEventFixtures",
    "method": "createLogEvent",
    "file": "LogEventFixtures.java",
    "line": 54
  }
}
 * </pre>
 * <p>
 * If {@code complete="false"}, the appender does not write the JSON open array character "[" at the start
 * of the document, "]" and the end, nor comma "," between records.
 * </p>
 * <p>
 * This approach enforces the independence of the JsonLayout and the appender where you embed it.
 * </p>
 * <h3>Encoding</h3>
 * <p>
 * Appenders using this layout should have their {@code charset} set to {@code UTF-8} or {@code UTF-16}, otherwise
 * events containing non ASCII characters could result in corrupted log files.
 * </p>
 * <h3>Pretty vs. compact XML</h3>
 * <p>
 * By default, the JSON layout is not compact (a.k.a. "pretty") with {@code compact="false"}, which means the
 * appender uses end-of-line characters and indents lines to format the text. If {@code compact="true"}, then no
 * end-of-line or indentation is used. Message content may contain, of course, escaped end-of-lines.
 * </p>
 */
@Plugin(name = "ExtendedJsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class ExtendedJsonLayout extends AbstractJacksonLayout {

    private static final String DEFAULT_FOOTER = "]";

    private static final String DEFAULT_HEADER = "[";
    
    private static final String DEFAULT_JSON_EXTENDER_CLASS = "org.apache.logging.log4j.core.layout.ExtendedJsonAdapter";

    static final String CONTENT_TYPE = "application/json";

    public static class Builder<B extends Builder<B>> extends AbstractJacksonLayout.Builder<B>
            implements org.apache.logging.log4j.core.util.Builder<ExtendedJsonLayout> {

        @PluginBuilderAttribute
        private boolean locationInfo;
        
        @PluginBuilderAttribute
        private String jsonAdapterClassName = DEFAULT_JSON_EXTENDER_CLASS;

		@PluginBuilderAttribute
        private boolean properties;
        
        @PluginBuilderAttribute
        private boolean propertiesAsList;
        
        @PluginBuilderAttribute
        private boolean includeStacktrace = true;

        @PluginBuilderAttribute
        private boolean stacktraceAsString = true;

        public Builder() {
            super();
            setCharset(StandardCharsets.UTF_8);
        }

        @Override
        public ExtendedJsonLayout build() {
            final boolean encodeThreadContextAsList = properties && propertiesAsList;
            final String headerPattern = toStringOrNull(getHeader());
            final String footerPattern = toStringOrNull(getFooter());
            return new ExtendedJsonLayout(getConfiguration(), locationInfo, properties, encodeThreadContextAsList, isComplete(),
                    isCompact(), getEventEol(), headerPattern, footerPattern, getCharset(), includeStacktrace, stacktraceAsString, jsonAdapterClassName);
        }

        protected String toStringOrNull(final byte[] header) {
            return header == null ? null : new String(header, Charset.defaultCharset());
        }

        public boolean isLocationInfo() {
            return locationInfo;
        }

        public boolean isProperties() {
            return properties;
        }

        public boolean isPropertiesAsList() {
            return propertiesAsList;
        }
        
        public String getJsonAdapter() {
			return jsonAdapterClassName;
		}


        /**
         * If "true", includes the stacktrace of any Throwable in the generated JSON, defaults to "true".
         * @return If "true", includes the stacktrace of any Throwable in the generated JSON, defaults to "true".
         */
        public boolean isIncludeStacktrace() {
            return includeStacktrace;
        }

        public B setLocationInfo(boolean locationInfo) {
            this.locationInfo = locationInfo;
            return asBuilder();
        }

        public B setProperties(boolean properties) {
            this.properties = properties;
            return asBuilder();
        }

        public B setPropertiesAsList(boolean propertiesAsList) {
            this.propertiesAsList = propertiesAsList;
            return asBuilder();
        }

        /**
         * If "true", includes the stacktrace of any Throwable in the generated JSON, defaults to "true".
         * @param includeStacktrace If "true", includes the stacktrace of any Throwable in the generated JSON, defaults to "true".
         * @return this builder
         */
        public B setIncludeStacktrace(boolean includeStacktrace) {
            this.includeStacktrace = includeStacktrace;
            return asBuilder();
        }
        
        public B setStacktraceAsString(boolean stacktraceAsString) {
            this.stacktraceAsString = stacktraceAsString;
            return asBuilder();
        }
        

		public B setJsonAdapter(String jsonAdapter) {
			this.jsonAdapterClassName = jsonAdapter;
			return asBuilder();
		}
    }

	private ExtendedJson jsonAdapter;


    protected ExtendedJsonLayout(final Configuration config, final boolean locationInfo, final boolean properties,
            final boolean encodeThreadContextAsList,
            final boolean complete, final boolean compact, final boolean eventEol, final String headerPattern,
            final String footerPattern, final Charset charset, final boolean includeStacktrace, final boolean stacktraceAsString, final String jsonExtenderClass) {
        super(config, getObjectWriter(encodeThreadContextAsList, includeStacktrace, stacktraceAsString, locationInfo, properties, compact),
                charset, compact, complete, eventEol,
                PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(headerPattern).setDefaultPattern(DEFAULT_HEADER).build(),
                PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(footerPattern).setDefaultPattern(DEFAULT_FOOTER).build());
        
        Class<?> clazz;
        Object jsonAdapterobject = null;
		try {
			clazz = Class.forName(jsonExtenderClass);
	        Constructor<?> ctor = clazz.getConstructor();
	        jsonAdapterobject = ctor.newInstance(new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        this.jsonAdapter = (ExtendedJson) jsonAdapterobject;
    }
    
    static protected ObjectWriter getObjectWriter(boolean encodeThreadContextAsList, boolean includeStacktrace, boolean stacktraceAsString, boolean locationInfo, boolean properties, boolean compact){
    	ObjectWriter writer = new JacksonFactory.JSON(encodeThreadContextAsList, includeStacktrace, stacktraceAsString).newWriter(
                locationInfo, properties, compact);
    	return writer;
    }

    /**
     * Returns appropriate JSON header.
     *
     * @return a byte array containing the header, opening the JSON array.
     */
    @Override
    public byte[] getHeader() {
        if (!this.complete) {
            return null;
        }
        final StringBuilder buf = new StringBuilder();
        final String str = serializeToString(getHeaderSerializer());
        if (str != null) {
            buf.append(str);
        }
        buf.append(this.eol);
        return getBytes(buf.toString());
    }

    /**
     * Returns appropriate JSON footer.
     *
     * @return a byte array containing the footer, closing the JSON array.
     */
    @Override
    public byte[] getFooter() {
        if (!this.complete) {
            return null;
        }
        final StringBuilder buf = new StringBuilder();
        buf.append(this.eol);
        final String str = serializeToString(getFooterSerializer());
        if (str != null) {
            buf.append(str);
        }
        buf.append(this.eol);
        return getBytes(buf.toString());
    }

    @Override
    public Map<String, String> getContentFormat() {
        final Map<String, String> result = new HashMap<>();
        result.put("version", "2.0");
        return result;
    }

    @Override
    /**
     * @return The content type.
     */
    public String getContentType() {
        return CONTENT_TYPE + "; charset=" + this.getCharset();
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return new Builder<B>().asBuilder();
    }

    /**
     * Creates a JSON Layout using the default settings. Useful for testing.
     *
     * @return A JSON Layout.
     */
    public static ExtendedJsonLayout createDefaultLayout() {
        return new ExtendedJsonLayout(new DefaultConfiguration(), false, false, false, false, false, false,
                DEFAULT_HEADER, DEFAULT_FOOTER, StandardCharsets.UTF_8, true, false, DEFAULT_JSON_EXTENDER_CLASS);
    }
    

    @Override
    public void toSerializable(final LogEvent event, final Writer writer) throws IOException {
        if (complete && eventCount > 0) {
            writer.append(", ");
        }
        this.objectWriter.writeValue(writer, convertLog4jEventToExtendedJsonWrapper(event));
        writer.write(eol);
        markEvent();
    }
    
    private ExtendedJsonWrapper convertLog4jEventToExtendedJsonWrapper(LogEvent event){
    	ExtendedJsonWrapper wrapper = new ExtendedJsonWrapper(convertMutableToLog4jEvent(event), jsonAdapter.getMixedFields());
    	return wrapper;
    }
    
    private static LogEvent convertMutableToLog4jEvent(final LogEvent event) {
        // TODO Jackson-based layouts have certain filters set up for Log4jLogEvent.
        // TODO Need to set up the same filters for MutableLogEvent but don't know how...
        // This is a workaround.
        return event instanceof MutableLogEvent
                ? ((MutableLogEvent) event).createMemento()
                : event;
    }
}