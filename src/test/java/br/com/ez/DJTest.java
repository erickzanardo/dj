package br.com.ez;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.JsonObject;

public class DJTest {

	@Test
	public void testDJ() {
		
		JsonObject object = new JsonObject();
		object.addProperty("foo", "with doT");

		String result = new DJ().template("<h1>Here is a sample template {{=it.foo}}</h1>").context(object).result();
		assertEquals("<h1>Here is a sample template with doT</h1>", result);
	}
}
