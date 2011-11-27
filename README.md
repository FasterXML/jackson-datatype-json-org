Project to build Jackson (http://jackson.codehaus.org) extension module (jar) to support datatypes of "json org" JSON library (see [http://org.json/java])

## Status

Module is fully usable and officially released.

Project aims to ensure compatibility with Jackson versions starting with 1.8: the initial `1.8.0` release should work with Jackson 1.8 and 1.9 versions, and may work with 1.7 as well.

## Usage

### Maven dependency

To use module on Maven-based projects, use following dependency:

    <dependency>
      <groupId>com.fasterxml.jackson</groupId>
      <artifactId>jackson-datatype-json-org</artifactId>
      <version>1.8.0</version>
    </dependency>

(or whatever version is most up-to-date at the moment)

### Registering module

To use the the Module in Jackson, simply register it with the ObjectMapper instance:

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JsonOrgModule());

This will ensure that basic datatype of `org.json` package can be read and written using Jackson data-binding functionality.

### Data conversions

After registering the module, you can read and write JSON to/from org.json.JSONObject similar to handling custom POJOs or standard JDK types:

    JSONObject ob = mapper.readValue(json, JSONObject.class); // read from a source
    String json = mapper.writeValue(ob); // output as String

As well as do conversion to/from POJOs:

    MyValue value = mapper.convertValue(jsonObject, MyValue.class);
    JSONObject jsonObject = mapper.convertValue(value, JSONObject.class);

or to/from Tree Model:

    JsonNode root = mapper.valueToTree(jsonObject);
    jsonObject = mapper.treeToValue(root, JSONObject.class);

Similarly, you can read/write/convert-to/convert-from `JSONArray` instead of `JSONObject`.

