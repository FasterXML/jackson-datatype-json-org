package com.fasterxml.jackson.datatype.jsonorg;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.*;

public class JSONObjectSerializer extends JSONBaseSerializer<JSONObject>
{
    private static final long serialVersionUID = 1L;

    public final static JSONObjectSerializer instance = new JSONObjectSerializer();

    public JSONObjectSerializer()
    {
        super(JSONObject.class);
    }

    @Override // since 2.6
    public boolean isEmpty(SerializerProvider provider, JSONObject value) {
        return (value == null) || value.length() == 0;
    }

    @Override
    public void serialize(JSONObject value, JsonGenerator g, SerializerProvider provider)
        throws IOException
    {
        g.writeStartObject();
        serializeContents(value, g, provider);
        g.writeEndObject();
    }

    @Override
    public void serializeWithType(JSONObject value, JsonGenerator g, SerializerProvider provider,
            TypeSerializer typeSer) throws IOException
    {
        typeSer.writeTypePrefixForObject(value, g);
        serializeContents(value, g, provider);
        typeSer.writeTypeSuffixForObject(value, g);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException
    {
        return createSchemaNode("object", true);
    }
    
    protected void serializeContents(JSONObject value, JsonGenerator g, SerializerProvider provider)
        throws IOException
    {
        Iterator<?> it = value.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object ob = value.opt(key);
            if (ob == null || ob == JSONObject.NULL) {
                if (provider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES)) {
                    g.writeNullField(key);
                }
                continue;
            }
            g.writeFieldName(key);
            Class<?> cls = ob.getClass();
            if (cls == JSONObject.class) {
                serialize((JSONObject) ob, g, provider);
            } else if (cls == JSONArray.class) {
                JSONArraySerializer.instance.serialize((JSONArray) ob, g, provider);
            } else  if (cls == String.class) {
                g.writeString((String) ob);
            } else  if (cls == Integer.class) {
                g.writeNumber(((Integer) ob).intValue());
            } else  if (cls == Long.class) {
                g.writeNumber(((Long) ob).longValue());
            } else  if (cls == Boolean.class) {
                g.writeBoolean(((Boolean) ob).booleanValue());
            } else  if (cls == Double.class) {
                g.writeNumber(((Double) ob).doubleValue());
            } else if (JSONObject.class.isAssignableFrom(cls)) { // sub-class
                serialize((JSONObject) ob, g, provider);
            } else if (JSONArray.class.isAssignableFrom(cls)) { // sub-class
                JSONArraySerializer.instance.serialize((JSONArray) ob, g, provider);
            } else {
                provider.defaultSerializeValue(ob, g);
            }
        }
    }
}
