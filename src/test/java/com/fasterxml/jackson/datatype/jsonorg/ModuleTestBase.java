package com.fasterxml.jackson.datatype.jsonorg;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public abstract class ModuleTestBase extends junit.framework.TestCase
{
    public ObjectMapper newMapper() {
        return new ObjectMapper()
                .registerModule(new JsonOrgModule());
    }

    public JsonMapper.Builder newMapperBuilder() {
        return JsonMapper.builder()
                .addModule(new JsonOrgModule());
    }
    
    protected void verifyException(Throwable e, String... matches)
    {
        String msg = e.getMessage();
        String lmsg = (msg == null) ? "" : msg.toLowerCase();
        for (String match : matches) {
            String lmatch = match.toLowerCase();
            if (lmsg.indexOf(lmatch) >= 0) {
                return;
            }
        }
        throw new Error("Expected an exception with one of substrings ("+Arrays.asList(matches)+"): got one with message \""+msg+"\"");
    }
}
