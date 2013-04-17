package com.fasterxml.jackson.datatype.jsonorg;

import com.fasterxml.jackson.databind.module.SimpleModule;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonOrgModule extends SimpleModule
{
    private static final long serialVersionUID = 1;

    private final static String NAME = "JsonOrgModule";
    
    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */
    
    public JsonOrgModule()
    {
        super(NAME, PackageVersion.VERSION);
        addDeserializer(JSONArray.class, JSONArrayDeserializer.instance);
        addDeserializer(JSONObject.class, JSONObjectDeserializer.instance);
        addSerializer(JSONArraySerializer.instance);
        addSerializer(JSONObjectSerializer.instance);
    }
}