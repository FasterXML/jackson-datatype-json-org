package com.fasterxml.jackson.datatype.jsonorg;

import java.io.IOException;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.StdDeserializer;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectDeserializer extends StdDeserializer<JSONObject>
{
    public final static JSONObjectDeserializer instance = new JSONObjectDeserializer();

    public JSONObjectDeserializer()
    {
        super(JSONObject.class);
    }

    @Override
    public JSONObject deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException
    {
        JSONObject ob = new JSONObject();
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        for (; t == JsonToken.FIELD_NAME; t = jp.nextToken()) {
            String fieldName = jp.getCurrentName();
            t = jp.nextToken();
            try {
                switch (t) {
                case START_ARRAY:
                    ob.put(fieldName, JSONArrayDeserializer.instance.deserialize(jp, ctxt));
                    continue;
                case START_OBJECT:
                    ob.put(fieldName, deserialize(jp, ctxt));
                    continue;
                case VALUE_STRING:
                    ob.put(fieldName, jp.getText());
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
                    ob.put(fieldName, jp.getNumberValue());
                    continue;
                case VALUE_NUMBER_FLOAT:
                    ob.put(fieldName, jp.getNumberValue());
                    continue;
                case VALUE_EMBEDDED_OBJECT:
                    ob.put(fieldName, jp.getEmbeddedObject());
                    continue;
                }
            } catch (JSONException e) {
                throw ctxt.mappingException("Failed to construct JSONObject: "+e.getMessage());
            }
            throw ctxt.mappingException("Urecognized or unsupported JsonToken type: "+t);
        }
        return ob;
    }    
}
