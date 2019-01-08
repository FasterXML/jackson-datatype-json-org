package com.fasterxml.jackson.datatype.jsonorg;

import java.io.*;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;

public class TestVersions extends ModuleTestBase
{
    public void testMapperVersions() throws IOException
    {
        JsonOrgModule module = new JsonOrgModule();
        assertVersion(module);
    }

    /*
    /**********************************************************
    /* Helper methods
    /**********************************************************
     */
    
    private void assertVersion(Versioned vers)
    {
        final Version v = vers.version();
        assertEquals(PackageVersion.VERSION, v);
    }
}

