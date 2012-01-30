package com.fasterxml.jackson.datatype.jsonorg;

import com.fasterxml.jackson.databind.module.SimpleModule;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonOrgModule extends SimpleModule
{
    private final static String NAME = "JsonOrgModule";
    
    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */
    
    public JsonOrgModule()
    {
        super(NAME, ModuleVersion.instance.version());
        addDeserializer(JSONArray.class, JSONArrayDeserializer.instance);
        addDeserializer(JSONObject.class, JSONObjectDeserializer.instance);
        addSerializer(JSONArraySerializer.instance);
        addSerializer(JSONObjectSerializer.instance);
    }
}