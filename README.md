Project to build Jackson (http://jackson.codehaus.org) extension module (jar) to support datatypes of "json org" JSON library (see [http://org.json/java])

== Usage ==

Register module with ObjectMapper:

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JsonOrgModule());

And then you can read and write JSON to/from org.json.JSONObject:

    JSONObject ob = mapper.readValue(json, JSONObject.class); // read from a source
    String json = mapper.writeValue(ob); // output as String
