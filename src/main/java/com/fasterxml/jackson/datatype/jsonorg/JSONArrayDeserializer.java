package com.fasterxml.jackson.datatype.jsonorg;

import java.io.IOException;

import com.fasterxml.jackson.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONArrayDeserializer extends StdDeserializer<JSONArray>
{
    public final static JSONArrayDeserializer instance = new JSONArrayDeserializer();

    public JSONArrayDeserializer()
    {
        super(JSONArray.class);
    }
    
    @Override
    public JSONArray deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException
    {
        // 07-Jan-2019, tatu: As per [datatype-json-org#15], need to verify it's an Array
        if (!p.isExpectedStartArrayToken()) {
            final JsonToken t = p.currentToken();
            return (JSONArray) ctxt.handleUnexpectedToken(getValueType(ctxt), t, p,
                    "Unexpected token (%s), expected START_ARRAY for %s value",
                    t, ClassUtil.nameOf(handledType()));
        }
        
        JSONArray array = new JSONArray();
        JsonToken t;
        while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
            switch (t) {
            case START_ARRAY:
                array.put(deserialize(p, ctxt));
                continue;
            case START_OBJECT:
                array.put(JSONObjectDeserializer.instance.deserialize(p, ctxt));
                continue;
            case VALUE_STRING:
                array.put(p.getText());
                continue;
            case VALUE_NULL:
                array.put(JSONObject.NULL);
                continue;
            case VALUE_TRUE:
                array.put(Boolean.TRUE);
                continue;
            case VALUE_FALSE:
                array.put(Boolean.FALSE);
                continue;
            case VALUE_NUMBER_INT:
                array.put(p.getNumberValue());
                continue;
            case VALUE_NUMBER_FLOAT:
                array.put(p.getNumberValue());
                continue;
            case VALUE_EMBEDDED_OBJECT:
                array.put(p.getEmbeddedObject());
                continue;
            default:
                return (JSONArray) ctxt.handleUnexpectedToken(getValueType(ctxt), p);
            }
        }
        return array;
    }
}
