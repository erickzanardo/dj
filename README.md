DJ (doT for Java)
==

DJ is a simple API for using the doT javascript template (http://olado.github.io/doT/index.html) lib on Java.

It's is very simple to use, as the following code shows

Configure your pom to use the DJ dependency

```xml
  <repository>
    <id>erickzanardo-releases</id>
    <url>http://erickzanardo.github.com/maven/releases/</url>
  </repository>

  <dependency>
    <groupId>com.rambi</groupId>
    <artifactId>rambi-engine</artifactId>
    <version>1.1.3</version>
  </dependency>
```

```java
JsonObject object = new JsonObject();
  	object.addProperty("foo", "with doT");

		String result = new DJ()
				.template("<h1>Here is a sample template {{=it.foo}}</h1>")
				.context(object).result();
        
    // <h1>Here is a sample template with doT</h1>
```

More info about doT.js syntax on http://olado.github.io/doT/index.html.
