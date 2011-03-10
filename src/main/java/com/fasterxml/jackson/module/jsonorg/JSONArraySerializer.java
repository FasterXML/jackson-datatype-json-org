package com.fasterxml.jackson.module.jsonorg;

import java.io.IOException;
import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONObject;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.TypeSerializer;

public class JSONArraySerializer extends JSONBaseSerializer<JSONArray>
{
    public final static JSONArraySerializer instance = new JSONArraySerializer();

    public JSONArraySerializer()
    {
        super(JSONArray.class);
    }
    
    @Override
    public void serialize(JSONArray value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException
    {
        jgen.writeStartArray();
        serializeContents(value, jgen, provider);
        jgen.writeEndArray();
    }

    @Override
    public void serializeWithType(JSONArray value, JsonGenerator jgen, SerializerProvider provider,
            TypeSerializer typeSer)
        throws IOException, JsonGenerationException
    {
        typeSer.writeTypePrefixForArray(value, jgen);
        serializeContents(value, jgen, provider);
        typeSer.writeTypeSuffixForArray(value, jgen);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException
    {
        return createSchemaNode("array", true);
    }
    
    protected void serializeContents(JSONArray value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException
    {
        for (int i = 0, len = value.length(); i < len; ++i) {
            Object ob = value.opt(i);
            if (ob == null || ob == JSONObject.NULL) {
                jgen.writeNull();
                continue;
            }
            Class<?> cls = ob.getClass();
            if (cls == JSONObject.class) {
                JSONObjectSerializer.instance.serialize((JSONObject) ob, jgen, provider);
            } else if (cls == JSONArray.class) {
                serialize((JSONArray) ob, jgen, provider);
            } else  if (cls == String.class) {
                jgen.writeString((String) ob);
            } else  if (cls == Integer.class) {
                jgen.writeNumber(((Integer) ob).intValue());
            } else  if (cls == Long.class) {
                jgen.writeNumber(((Long) ob).longValue());
            } else  if (cls == Boolean.class) {
                jgen.writeBoolean(((Boolean) ob).booleanValue());
            } else  if (cls == Double.class) {
                jgen.writeNumber(((Double) ob).doubleValue());
            } else if (JSONObject.class.isAssignableFrom(cls)) { // sub-class
                JSONObjectSerializer.instance.serialize((JSONObject) ob, jgen, provider);
            } else if (JSONArray.class.isAssignableFrom(cls)) { // sub-class
                serialize((JSONArray) ob, jgen, provider);
            } else if (JSONArray.class.isAssignableFrom(cls)) { // sub-class
                JSONArraySerializer.instance.serialize((JSONArray) ob, jgen, provider);
            } else {
                provider.defaultSerializeValue(ob, jgen);
            }
        }        
    }
}
