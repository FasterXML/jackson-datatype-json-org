package com.fasterxml.jackson.module.jsonorg;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.module.SimpleModule;

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
        addSerializer(null);
    }
}