package br.com.ez;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class DJTest {

	@Test
	public void testSimpleDJ() {
		JsonObject object = new JsonObject();
		object.addProperty("foo", "with doT");

		String result = new DJ().template("<h1>Here is a sample template {{=it.foo}}</h1>").context(object).result();
		assertEquals("<h1>Here is a sample template with doT</h1>", result);
	}

	@Test
	public void testStringEscapeDJ() {
		JsonObject object = new JsonObject();
		object.addProperty("foo", "'\"with doT");

		String result = new DJ().template("<h1>Here is a sample template {{=it.foo}}</h1>").context(object).result();
		assertEquals("<h1>Here is a sample template '\"with doT</h1>", result);
	}

	@Test
	public void testArrayEscapeDJ() {
		JsonObject object = new JsonObject();
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(1));
		array.add(new JsonPrimitive(2));
		object.add("foo", array);

		String result = new DJ().template("{{~it.foo :value:index}}{{=value}}{{~}}").context(object).result();
		assertEquals("12", result);
	}

	@Test
	public void testGlobalObjectPrimitiveStringDJ() {
		JsonObject object = new JsonObject();
		object.add("foo", new JsonPrimitive("Global Object test"));

		JsonPrimitive globalValue = new JsonPrimitive("This is a global value");

		String result = new DJ().template("{{=it.foo}} - {{=globalValue}}").addGlobalObject("globalValue", globalValue)
				.context(object).result();
		assertEquals("Global Object test - This is a global value", result);
	}

	@Test
	public void testGlobalObjectPrimitiveNumberDJ() {
		JsonObject object = new JsonObject();
		object.add("foo", new JsonPrimitive("Global Object test"));

		JsonPrimitive globalValue = new JsonPrimitive(1);

		String result = new DJ().template("{{=it.foo}} - {{=globalValue}}").addGlobalObject("globalValue", globalValue)
				.context(object).result();
		assertEquals("Global Object test - 1", result);
	}

	@Test
	public void testGlobalObjectPrimitiveBooleanDJ() {
		JsonObject object = new JsonObject();
		object.add("foo", new JsonPrimitive("Global Object test"));

		JsonPrimitive globalValue = new JsonPrimitive(false);

		String result = new DJ().template("{{=it.foo}} - {{=globalValue}}").addGlobalObject("globalValue", globalValue)
				.context(object).result();
		assertEquals("Global Object test - false", result);

		object = new JsonObject();
		object.add("foo", new JsonPrimitive("Global Object test"));

		globalValue = new JsonPrimitive(true);

		result = new DJ().template("{{=it.foo}} - {{=globalValue}}").addGlobalObject("globalValue", globalValue)
				.context(object).result();
		assertEquals("Global Object test - true", result);

		object = new JsonObject();
		object.add("foo", new JsonPrimitive("Global Object test"));

		globalValue = new JsonPrimitive(Boolean.FALSE);

		result = new DJ().template("{{=it.foo}} - {{=globalValue}}").addGlobalObject("globalValue", globalValue)
				.context(object).result();
		assertEquals("Global Object test - false", result);

		object = new JsonObject();
		object.add("foo", new JsonPrimitive("Global Object test"));

		globalValue = new JsonPrimitive(Boolean.TRUE);

		result = new DJ().template("{{=it.foo}} - {{=globalValue}}").addGlobalObject("globalValue", globalValue)
				.context(object).result();
		assertEquals("Global Object test - true", result);

	}
	
	@Test
	public void testGlobalObjectDJ() {
		JsonObject object = new JsonObject();
		object.add("foo", new JsonPrimitive("Global Object test"));

		JsonObject globalObject = new JsonObject();
		globalObject.add("message", new JsonPrimitive("This is a global value"));

		String result = new DJ().template("{{=it.foo}} - {{=globalValue.message}}")
				.addGlobalObject("globalValue", globalObject).context(object).result();
		assertEquals("Global Object test - This is a global value", result);
	}

	@Test
	public void testGlobalArrayDJ() {
		JsonObject object = new JsonObject();
		object.add("foo", new JsonPrimitive("Global Object test"));

		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(1));
		array.add(new JsonPrimitive(2));

		String result = new DJ().template("{{=it.foo}} - {{~globalValue :value:index}}{{=value}}{{~}}")
				.addGlobalObject("globalValue", array).context(object).result();
		assertEquals("Global Object test - 12", result);
	}

	@Test
	public void testHtmlTagDJ() {
		JsonObject object = new JsonObject();
		object.addProperty("foo", "<strong>I'm strong!</strong>");

		String result = new DJ().template("<h1>Here is a sample template with html tags on it's body, take a look: {{=it.foo}}</h1>").context(object).result();
		assertEquals("<h1>Here is a sample template with html tags on it's body, take a look: <strong>I'm strong!</strong></h1>", result);
	}
	
}
