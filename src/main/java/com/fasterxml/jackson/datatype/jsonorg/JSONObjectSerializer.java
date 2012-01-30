package com.fasterxml.jackson.datatype.jsonorg;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;

import com.fasterxml.jackson.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectSerializer extends JSONBaseSerializer<JSONObject>
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
                continue;
            }
            jgen.writeFieldName(key);
            Class<?> cls = ob.getClass();
            if (cls == JSONObject.class) {
                serialize((JSONObject) ob, jgen, provider);
            } else if (cls == JSONArray.class) {
                JSONArraySerializer.instance.serialize((JSONArray) ob, jgen, provider);
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
