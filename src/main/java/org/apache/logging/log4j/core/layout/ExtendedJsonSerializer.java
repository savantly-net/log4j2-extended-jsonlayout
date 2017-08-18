package org.apache.logging.log4j.core.layout;

// https://stackoverflow.com/a/37063144/1308685

import java.io.IOException;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.type.SimpleType;

public final class ExtendedJsonSerializer extends BeanSerializer {
	
    private Predicate filter = new Predicate<Entry<String, String>>() {
		@Override
		public boolean test(Entry<String, String> entry) {
			return entry.getValue() != null;
		}
	};
	
	private Consumer getConsumer(final JsonGenerator gen){
		return new Consumer<Entry<String, String>>() {
			@Override
			public void accept(Entry<String, String> entry) {
			    try {
			        gen.writeFieldName(entry.getKey());
			        gen.writeRawValue(entry.getValue());
			    } catch (IOException e) {
			        throw new RuntimeException(e);
			    }
			}
		};
	}
	

    public ExtendedJsonSerializer() {
        super(SimpleType.construct(ExtendedJsonWrapper.class), null, new BeanPropertyWriter[0], new BeanPropertyWriter[0]);
    }

    public ExtendedJsonSerializer(BeanSerializerBase base) {
        super(base);
    }

    @Override
    protected void serializeFields(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (bean instanceof ExtendedJsonWrapper) {
            ExtendedJsonWrapper mixin  = (ExtendedJsonWrapper) bean;
            Object      origin = mixin.getOrigin();

            BeanSerializer serializer = (BeanSerializer) provider.findValueSerializer(SimpleType.construct(origin.getClass()));

            new ExtendedJsonSerializer(serializer).serializeFields(origin, gen, provider);

            mixin.getMixed().entrySet()
                    .stream()
                    .filter(filter)
                    .forEach(getConsumer(gen));
        } else {
            super.serializeFields(bean, gen, provider);
        }

    }
    


}