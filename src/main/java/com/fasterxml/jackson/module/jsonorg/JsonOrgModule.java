package com.fasterxml.jackson.module.jsonorg;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonOrgModule extends SimpleModule
{
    private final static String NAME = "JsonOrgModule";
    
    // Should externalize this somehow
    private final static Version VERSION = new Version(1, 0, 0, null); // 1.0.0
    
    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */
    
    public JsonOrgModule()
    {
        super(NAME, VERSION);
        addDeserializer(JSONArray.class, JSONArrayDeserializer.instance);
        addDeserializer(JSONObject.class, JSONObjectDeserializer.instance);
        addSerializer(JSONArraySerializer.instance);
        addSerializer(JSONObjectSerializer.instance);
    }
}