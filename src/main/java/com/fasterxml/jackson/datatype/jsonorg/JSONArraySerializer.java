package com.fasterxml.jackson.datatype.jsonorg;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public class JSONArraySerializer extends JSONBaseSerializer<JSONArray>
{
    public final static JSONArraySerializer instance = new JSONArraySerializer();

    public JSONArraySerializer()
    {
        super(JSONArray.class);
    }

    @Override // since 2.6
    public boolean isEmpty(SerializerProvider provider, JSONArray value) {
        return (value == null) || value.length() == 0;
    }
    
    @Override
    public void serialize(JSONArray value, JsonGenerator g, SerializerProvider provider) throws IOException
    {
        g.writeStartArray();
        serializeContents(value, g, provider);
        g.writeEndArray();
    }

    @Override
    public void serializeWithType(JSONArray value, JsonGenerator g, SerializerProvider ctxt,
            TypeSerializer typeSer) throws IOException
    {
        g.setCurrentValue(value);
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, ctxt,
                typeSer.typeId(value, JsonToken.START_ARRAY));
        serializeContents(value, g, ctxt);
        typeSer.writeTypeSuffix(g, ctxt, typeIdDef);
    }

    protected void serializeContents(JSONArray value, JsonGenerator g, SerializerProvider provider)
        throws IOException
    {
        for (int i = 0, len = value.length(); i < len; ++i) {
            Object ob = value.opt(i);
            if (ob == null || ob == JSONObject.NULL) {
                g.writeNull();
                continue;
            }
            Class<?> cls = ob.getClass();
            if (cls == JSONObject.class) {
                JSONObjectSerializer.instance.serialize((JSONObject) ob, g, provider);
            } else if (cls == JSONArray.class) {
                serialize((JSONArray) ob, g, provider);
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
                JSONObjectSerializer.instance.serialize((JSONObject) ob, g, provider);
            } else if (JSONArray.class.isAssignableFrom(cls)) { // sub-class
                serialize((JSONArray) ob, g, provider);
            } else {
                provider.writeValue(g, ob);
            }
        }        
    }
}
