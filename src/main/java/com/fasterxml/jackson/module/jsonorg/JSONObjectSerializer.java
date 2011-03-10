package com.fasterxml.jackson.module.jsonorg;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.ser.SerializerBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectSerializer extends SerializerBase<JSONObject>
{
    public final static JSONObjectSerializer instance = new JSONObjectSerializer();

    public JSONObjectSerializer()
    {
        super(JSONObject.class);
    }
    
    @Override
    public void serialize(JSONObject value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException
    {
        jgen.writeStartObject();
        serializeContents(value, jgen, provider);
        jgen.writeEndObject();
    }

    @Override
    public void serializeWithType(JSONObject value, JsonGenerator jgen, SerializerProvider provider,
            TypeSerializer typeSer)
        throws IOException, JsonGenerationException
    {
        typeSer.writeTypePrefixForObject(value, jgen);
        serializeContents(value, jgen, provider);
        typeSer.writeTypeSuffixForObject(value, jgen);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException
    {
        return createSchemaNode("object", true);
    }
    
    protected void serializeContents(JSONObject value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException
    {
        Iterator<?> it = value.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object ob;
            try {
                ob = value.get(key);
            } catch (JSONException e) {
                throw new JsonGenerationException(e);
            }
            if (ob == null || ob == JSONObject.NULL) {
                if (provider.isEnabled(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES)) {
                    jgen.writeNullField(key);
                }
            } else {
                Class<?> cls = ob.getClass();
                jgen.writeFieldName(key);
                if (cls == JSONObject.class) {
                    serialize((JSONObject) ob, jgen, provider);
                } else if (cls == JSONArray.class) {
                    JSONArraySerializer.instance.serialize((JSONArray) ob, jgen, provider);
                } else if (JSONObject.class.isAssignableFrom(cls)) { // sub-class
                    serialize((JSONObject) ob, jgen, provider);
                } else if (JSONArray.class.isAssignableFrom(cls)) { // sub-class
                    JSONArraySerializer.instance.serialize((JSONArray) ob, jgen, provider);
                } else {
                    provider.defaultSerializeValue(ob, jgen);
                }
            }
        }
    }
}
