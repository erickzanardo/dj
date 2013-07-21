package br.com.ez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DJ {

	private ScriptableObject global;
	private JsonElement jsonContext = null;

	private boolean templateCompiled = false;

	public DJ() {
		Context cx = Context.enter();

		global = new ImporterTopLevel(cx);

		String doTJs = readFile("doT.min.js");

		Script doTScript = cx.compileString(doTJs, "doT.min.js", 1, null);
		doTScript.exec(cx, global);

		String djJs = readFile("dj.js");

		Script djScript = cx.compileString(djJs, "dj.js", 1, null);
		djScript.exec(cx, global);

		Context.exit();
	}

	public DJ template(String template) {
		Context cx = Context.enter();

		ScriptableObject scriptableObject = (ScriptableObject) cx
				.newObject(global);
		scriptableObject.setParentScope(global);

		Function f = (Function) global.get("compileFn");

		f.call(cx, global, scriptableObject, new Object[]{template});

		Context.exit();

		templateCompiled = true;
		return this;
	}

	public DJ context(JsonElement element) {
		this.jsonContext = element;
		return this;
	}

	public String result() {
		if (!templateCompiled) {
			throw new RuntimeException("No template was provided");
		}

		Context cx = Context.enter();

		ScriptableObject scriptableObject = (ScriptableObject) cx
				.newObject(global);
		scriptableObject.setParentScope(global);

		Function f = (Function) global.get("result");

		if (jsonContext == null) {
			jsonContext = new JsonObject();
		}

		Object result = f.call(cx, global, scriptableObject, new Object[]{jsonContext});

		Context.exit();

		return (String) result;
	}

	private String readFile(String file) {

		InputStream resourceAsStream = DJ.class.getClassLoader()
				.getResourceAsStream(file);

		BufferedReader br = null;
		StringBuilder ret = new StringBuilder();

		try {
			String line;
			br = new BufferedReader(new InputStreamReader(resourceAsStream));

			while ((line = br.readLine()) != null) {
				ret.append(line).append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return ret.toString();
	}
}
