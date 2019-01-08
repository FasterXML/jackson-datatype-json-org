package com.fasterxml.jackson.datatype.jsonorg;

import com.fasterxml.jackson.databind.*;

import org.json.*;

public class SimpleWriteTest extends ModuleTestBase
{
    public void testWriteObject() throws Exception
    {
        final ObjectMapper mapper = newMapper();

        // Ok: let's create JSONObject from JSON text
        String JSON = "{\"a\":{\"b\":3}}";
        JSONTokener tok = new JSONTokener(JSON);
        JSONObject ob = (JSONObject) tok.nextValue();
        assertEquals(JSON, mapper.writeValueAsString(ob));

        // And for [Issue#2], with null(s):
        JSON = "{\"a\":null}";
        tok = new JSONTokener(JSON);
        ob = (JSONObject) tok.nextValue();
        assertEquals(JSON, mapper.writeValueAsString(ob));
    }

    public void testWriteArray() throws Exception
    {
        final ObjectMapper mapper = newMapper();

        // Ok: let's create JSONObject from JSON text
        String JSON = "[1,true,\"text\",[null,3],{\"a\":[1.25]}]";
        JSONTokener tok = new JSONTokener(JSON);
        JSONArray ob = (JSONArray) tok.nextValue();
        assertEquals(JSON, mapper.writeValueAsString(ob));
    }
}
