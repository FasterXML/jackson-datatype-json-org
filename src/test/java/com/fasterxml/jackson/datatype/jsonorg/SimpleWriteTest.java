package com.fasterxml.jackson.datatype.jsonorg;

import org.codehaus.jackson.map.ObjectMapper;

import org.json.*;

import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

public class SimpleWriteTest extends TestBase
{
    public void testWriteObject() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonOrgModule());

        // Ok: let's create JSONObject from JSON text
        String JSON = "{\"a\":{\"b\":3}}";
        JSONTokener tok = new JSONTokener(JSON);
        JSONObject ob = (JSONObject) tok.nextValue();
        assertEquals(JSON, mapper.writeValueAsString(ob));
    }

    public void testWriteArray() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonOrgModule());

        // Ok: let's create JSONObject from JSON text
        String JSON = "[1,true,\"text\",[null,3],{\"a\":[1.25]}]";
        JSONTokener tok = new JSONTokener(JSON);
        JSONArray ob = (JSONArray) tok.nextValue();
        assertEquals(JSON, mapper.writeValueAsString(ob));
    }
}
