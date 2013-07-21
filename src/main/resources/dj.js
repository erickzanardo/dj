var compiledFn = null;

function compileFn(template) {
	// Avoiding java/javascript string problems;
	template = template + '';
	compiledFn = doT.template(template);
}

function result(context) {
	context = context + '';
	var c = JSON.parse(context);
	return compiledFn(c);
}