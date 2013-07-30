var compiledFn = null;

function getGlobal() {
	return (function(){ 
		return this
	})();
}

// Avoiding java/javascript string problems;
var javaStrToJs = function(s) {
	return s += '';
};

var addToGlobal = function(valueName, value) {
	getGlobal()[valueName] = value;
};

function addGlobalObject(value, jsonElement) {
	value = javaStrToJs(value);

	if (jsonElement.isJsonPrimitive()) {
		var jsonPrimitive = jsonElement.getAsJsonPrimitive();
		if (jsonPrimitive.isString()) {
			addToGlobal(value, javaStrToJs(jsonPrimitive.getAsString()));
		} else if (jsonPrimitive.isNumber()) {
			var number = new Number(jsonPrimitive.getAsLong()) + 0;
			addToGlobal(value, number);
		} else {
			var b = jsonElement.getAsBoolean();
			if (typeof(b) === "boolean") {
				b = b ? true : false;
			} else {
				b = b.equals(java.lang.Boolean.TRUE) ? true : false;
			}
			addToGlobal(value, b);
		}
	} else {
		var valueStr = javaStrToJs(jsonElement.toString());
		var obj = JSON.parse(valueStr);
		addToGlobal(value, obj);
	}
}

function compileFn(template) {
	template = javaStrToJs(template);
	compiledFn = doT.template(template);
}

function result(context) {
	context = context + '';
	var c = JSON.parse(context);
	return compiledFn(c);
}