package org.apache.logging.log4j.core.layout;

// https://stackoverflow.com/a/37063144/1308685

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.type.SimpleType;

public final class ExtendedJsonSerializer extends BeanSerializer {
	
	private static final long serialVersionUID = 1L;

	public ExtendedJsonSerializer() {
        super(SimpleType.constructUnsafe(ExtendedJsonWrapper.class), null, new BeanPropertyWriter[0], new BeanPropertyWriter[0]);
    }

    public ExtendedJsonSerializer(BeanSerializerBase base) {
        super(base);
    }

    @Override
    protected void serializeFields(Object bean, final JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (bean instanceof ExtendedJsonWrapper) {
            ExtendedJsonWrapper mixin = (ExtendedJsonWrapper) bean;
            Object origin = mixin.getOrigin();

            BeanSerializer serializer = (BeanSerializer) provider.findValueSerializer(origin.getClass());
            new ExtendedJsonSerializer(serializer).serializeFieldsFiltered(origin, gen, provider);
            
            Set<Entry<String, Object>> entries = mixin.getMixed().entrySet();
            
            //gen.writeObjectField("mixedIn", mixin.getMixed());
            
            for (Entry<String, Object> entry : entries) {
				if(entry.getValue() != null){
					 try {
     			        gen.writeObjectField(entry.getKey(), entry.getValue());
     			    } catch (IOException e) {
     			        throw new RuntimeException(e);
     			    }
				}
			}

        } else {
            super.serializeFields(bean, gen, provider);
        }

    }
    


}