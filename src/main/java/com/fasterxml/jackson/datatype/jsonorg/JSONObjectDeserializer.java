package com.fasterxml.jackson.datatype.jsonorg;

import java.io.IOException;

import com.fasterxml.jackson.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectDeserializer extends StdDeserializer<JSONObject>
{
    private static final long serialVersionUID = 1L;

    public final static JSONObjectDeserializer instance = new JSONObjectDeserializer();

    public JSONObjectDeserializer()
    {
        super(JSONObject.class);
    }

    @Override
    public JSONObject deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException
    {
        JSONObject ob = new JSONObject();
        JsonToken t = p.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = p.nextToken();
        }
        for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
            String fieldName = p.getCurrentName();
            t = p.nextToken();
            try {
                switch (t) {
                case START_ARRAY:
                    ob.put(fieldName, JSONArrayDeserializer.instance.deserialize(p, ctxt));
                    continue;
                case START_OBJECT:
                    ob.put(fieldName, deserialize(p, ctxt));
                    continue;
                case VALUE_STRING:
                    ob.put(fieldName, p.getText());
                    continue;
                case VALUE_NULL:
                    ob.put(fieldName, JSONObject.NULL);
                    continue;
                case VALUE_TRUE:
                    ob.put(fieldName, Boolean.TRUE);
                    continue;
                case VALUE_FALSE:
                    ob.put(fieldName, Boolean.FALSE);
                    continue;
                case VALUE_NUMBER_INT:
                    ob.put(fieldName, p.getNumberValue());
                    continue;
                case VALUE_NUMBER_FLOAT:
                    ob.put(fieldName, p.getNumberValue());
                    continue;
                case VALUE_EMBEDDED_OBJECT:
                    ob.put(fieldName, p.getEmbeddedObject());
                    continue;
                default:
                }
            } catch (JSONException e) {
                throw ctxt.instantiationException(handledType(), e);
            }
            return (JSONObject) ctxt.handleUnexpectedToken(JSONObject.class, p);
        }
        return ob;
    }
}
