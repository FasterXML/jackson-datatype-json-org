Project to build Jackson (http://jackson.codehaus.org) extension module (jar) to support datatypes of "json org" JSON library (see [http://org.json/java])

== Status

Module is fully usable.

== Usage

### Maven dependency

To use module on Maven-based projects, use following dependency:

    <dependency>
      <groupId>com.fasterxml.jackson</groupId>
      <artifactId>jackson-datatype-json-org</artifactId>
      <version>0.9.2</version>
    </dependency>

(or whatever version is most up-to-date at the moment)

### Registering module

To use the the Module in Jackson, simply register it with the ObjectMapper instance:
Modules are registered through ObjectMapper, like so:

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JsonOrgModule());

### Data conversions

And then you can read and write JSON to/from org.json.JSONObject:

    JSONObject ob = mapper.readValue(json, JSONObject.class); // read from a source
    String json = mapper.writeValue(ob); // output as String

As well as do conversion to/from POJOs:

    MyValue value = mapper.convertValue(jsonObject, MyValue.class);
    JSONObject jsonObject = mapper.convertValue(value, JSONObject.class);

or to/from Tree Model:

    JsonNode root = mapper.valueToTree(jsonObject);
    jsonObject = mapper.treeToValue(root, JSONObject.class);

(and all examples similarly with JSONArray, if root value is an array)
