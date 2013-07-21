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

		String result = new DJ()
				.template("<h1>Here is a sample template {{=it.foo}}</h1>")
				.context(object).result();
		assertEquals("<h1>Here is a sample template with doT</h1>", result);
	}

	@Test
	public void testStringEscapeDJ() {
		JsonObject object = new JsonObject();
		object.addProperty("foo", "'\"with doT");

		String result = new DJ()
				.template("<h1>Here is a sample template {{=it.foo}}</h1>")
				.context(object).result();
		assertEquals("<h1>Here is a sample template '\"with doT</h1>", result);
	}

	@Test
	public void testArrayEscapeDJ() {
		JsonObject object = new JsonObject();
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(1));
		array.add(new JsonPrimitive(2));
		object.add("foo", array);

		String result = new DJ()
				.template("{{~it.foo :value:index}}{{=value}}{{~}}")
				.context(object).result();
		assertEquals("12", result);
	}

}
