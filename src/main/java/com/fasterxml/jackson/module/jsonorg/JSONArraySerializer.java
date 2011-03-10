package com.fasterxml.jackson.module.jsonorg;

import java.io.IOException;
import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONObject;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.TypeSerializer;
import org.codehaus.jackson.map.ser.SerializerBase;

public class JSONArraySerializer extends SerializerBase<JSONArray>
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
        jgen.writeStartObject();
        serializeContents(value, jgen, provider);
        jgen.writeEndObject();
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
            } else {
                Class<?> cls = ob.getClass();
                if (cls == JSONObject.class) {
                    JSONObjectSerializer.instance.serialize((JSONObject) ob, jgen, provider);
                } else if (cls == JSONArray.class) {
                    serialize((JSONArray) ob, jgen, provider);
                } else if (JSONObject.class.isAssignableFrom(cls)) { // sub-class
                    JSONObjectSerializer.instance.serialize((JSONObject) ob, jgen, provider);
                } else if (JSONArray.class.isAssignableFrom(cls)) { // sub-class
                    serialize((JSONArray) ob, jgen, provider);
                } else {
                    provider.defaultSerializeValue(ob, jgen);
                }
            }
        }        
    }
}
