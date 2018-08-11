package com.springboot.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.junit.Assert;
import org.junit.Test;

import com.springboot.demo.domain.User;



public class JsonTests extends SpringBootDemoApplicationTests{
	
	@Test
	public void testOrgJson() throws Exception{
		JSONObject carrierServiceValue=new JSONObject();
		 carrierServiceValue.put("name","Shipping Rate Provider");
		 carrierServiceValue.put("callback_url", "http://shipingrateprovider.com");
		 carrierServiceValue.put("format", "json");
		 carrierServiceValue.put("service_discovery", true);
		 JSONObject carrierService=new JSONObject();
		 carrierService.put("carrier_service", carrierServiceValue);
		 
		 Assert.assertEquals("Shipping Rate Provider", carrierService.getJSONObject("carrier_service").get("name").toString());
		 Assert.assertEquals("http://shipingrateprovider.com", carrierService.getJSONObject("carrier_service").get("callback_url").toString());
		 Assert.assertEquals("json", carrierService.getJSONObject("carrier_service").get("format").toString());
		 Assert.assertEquals(true, carrierService.getJSONObject("carrier_service").get("service_discovery"));
		 
		 Writer myWriter=new FileWriter(new File("./src/test/resources/json/test1.json"));
		 JSONWriter jsonWriter=new JSONWriter(myWriter)
		 								.object()
		 								.key("carrier_service")
		 								.value(carrierServiceValue)
		 								.endObject();
		 jsonWriter.toString();
		 								
		 myWriter.flush();
		 myWriter.close();
		 
	}
	
	@Test
	public void testJavaxJsonFromGlassFish() throws Exception{
		 
		 JsonObject value = Json.createObjectBuilder()
			     .add("firstName", "John")
			     .add("lastName", "Smith")
			     .add("age", 25)
			     .add("address", Json.createObjectBuilder()
			         .add("streetAddress", "21 2nd Street")
			         .add("city", "New York")
			         .add("state", "NY")
			         .add("postalCode", "10021"))
			     .add("phoneNumber", Json.createArrayBuilder()
			         .add(Json.createObjectBuilder()
			             .add("type", "home")
			             .add("number", "212 555-1234"))
			         .add(Json.createObjectBuilder()
			             .add("type", "fax")
			             .add("number", "646 555-4567")))
			     .build();
		 
		 Assert.assertEquals("John", value.getJsonString("firstName").getString());
		 Assert.assertEquals("Smith", value.getJsonString("lastName").getString());
		 Assert.assertEquals(25, value.getJsonNumber("age").intValue());
		 Assert.assertEquals("21 2nd Street", value.getJsonObject("address").getJsonString("streetAddress").getString());
		 
		 OutputStream os=new FileOutputStream(new File("./src/test/resources/json/test2.json"));
		 JsonWriter writer=Json.createWriter(os);
		 writer.writeObject(value);
		 os.flush();
		 os.close();
		 
		 InputStream is=new FileInputStream(new File("./src/test/resources/json/test2.json"));
		 JsonReader reader=Json.createReader(is);
		 
		 JsonObject rValue=reader.readObject();
		 Assert.assertEquals("John", rValue.getJsonString("firstName").getString());
		 Assert.assertEquals("Smith", rValue.getJsonString("lastName").getString());
		 Assert.assertEquals(25, rValue.getJsonNumber("age").intValue());
		 Assert.assertEquals("21 2nd Street", rValue.getJsonObject("address").getJsonString("streetAddress").getString());
		 
		 is.close();
	}
	
	@Test
	public void testCodehausJacksonJson() throws Exception{
		 ObjectMapper objectMapper=new ObjectMapper();
		 User user=new User("jhon", "pass");
		 List<String> authority = new ArrayList<>();
		 authority.add("user");
		 user.setAuthority(authority);
		 objectMapper.writeValue(new File("./src/test/resources/json/user.json"), user);
		 
		 User rUser=objectMapper.readValue(new File("./src/test/resources/json/user.json"), User.class);
		 
		 Assert.assertEquals("jhon", rUser.getUsername());
		 Assert.assertEquals("pass", rUser.getPassword());
	}

}
