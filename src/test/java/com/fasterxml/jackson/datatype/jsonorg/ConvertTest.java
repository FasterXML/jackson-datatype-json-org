package com.fasterxml.jackson.datatype.jsonorg;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class ConvertTest extends ModuleTestBase
{
    static class TestDomain {
        public Integer id;
        public String name;
        public Double da;
        public Map<String,Object> ldt;
        public Map<String,Object> ld;
        public Map<String,Object> lt;
        public JSONObject jsn;
        public JSONArray jsa;
    }

    private final ObjectMapper MAPPER = newMapper();
    
    public void testIssue15() throws Exception
    {
        Map<String,Object> map = new HashMap<>();
        map.put("name", "zpj");
        map.put("id", 111);
        map.put("jsa", "[1, 34, 32, \"zpj\", {\"age\": 18, \"name\": \"zpj\", \"child\": {\"name\": \"zzy\", \"gender\": \"nan\"}}, {\"url\": \"test\", \"name\": \"suhu\"}]");
        final String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        try {
            MAPPER.readValue(json, TestDomain.class);
            fail("Should not pass");
        } catch (MismatchedInputException e) {
            verifyException(e, "Unexpected token");
            verifyException(e, "org.json.JSONArray");
            verifyException(e, "START_ARRAY");
        }
    }
}
