[![Build Status](https://travis-ci.org/savantly-net/log4j2-extended-jsonlayout.svg?branch=master)](https://travis-ci.org/savantly-net/log4j2-extended-jsonlayout)  


# log4j2-extended-jsonlayout
Customizable JSONLayout for Log4j2


### Include the artifact  
```
<dependency>
	<groupId>net.savantly.log4j2</groupId>
	<artifactId>extended-jsonlayout</artifactId>
	<version>2.8.0</version>
</dependency>
```  
Use v2.8.0 for log4j2 v2.8.x  
use v2.9.0-SNAPSHOT for log4j2 v2.9.x  

If you're using a SNAPSHOT, add the sonatype snapshot repository to your pom.  
```
<repositories>
  <repository>
    <id>oss.sonatype.org-snapshot</id>
    <url>http://oss.sonatype.org/content/repositories/snapshots</url>
    <releases>
      <enabled>false</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
 ```


### Example Usage  

Inside your log4j2 configuration, you can configure the new pattern "ExtendedJsonLayout"

``` 
	<Appenders>
		<Console name="STDOUT" target="SYSTEM_OUT">
			<ExtendedJsonLayout complete="false" properties="true"/>
		</Console>
	</Appenders>
	
```  

The default ExtendedJson implementation Produces output like below -  
```
{
  "timeMillis" : 1503426388690,
  "thread" : "main",
  "level" : "INFO",
  "loggerName" : "org.hibernate.validator.internal.xml.ValidationXmlParser",
  "message" : "HV000007: META-INF/validation.xml found. Parsing XML based configuration.",
  "endOfBatch" : false,
  "loggerFqcn" : "org.hibernate.validator.internal.util.logging.Log_$logger",
  "contextMap" : { },
  "threadId" : 1,
  "threadPriority" : 5,
  "hostname" : "MY-LAPTOP"
}
``` 



### Add custom Json Fields  

Implement the [ExtendedJson interface](./src/main/java/org/apache/logging/log4j/core/layout/ExtendedJson.java)  
And configure the class name in your log configuration -  

``` 
	<ExtendedJsonLayout complete="false" properties="true" 
				jsonAdapterClassName="com.example.MyCustomJsonLogger">
	</ExtendedJsonLayout>
```


### Example Custom Implementation  

```
package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.core.layout.ExtendedJson;

public class MyCustomJsonLogger implements ExtendedJson{
	
	HashMap<String, Object> mixedIn = new HashMap<>();
	Random r = new Random();
	
	public MyCustomJsonLogger() {
		mixedIn.put("myCustomKey", "myCustomValue");
		mixedIn.put("anotherKey", r.nextInt(200));
	}

	@Override
	public Map<String, Object> getMixedFields() {
		return mixedIn;
	}

}

```   

Each item in the Map<String,Object> are added to the root logging message so that it appears like this -  

```
{
  "timeMillis" : 1503390773796,
  "thread" : "main",
  "level" : "INFO",
  "loggerName" : "org.springframework.data.repository.config.RepositoryConfigurationDelegate",
  "message" : "Multiple Spring Data modules found, entering strict repository configuration mode!",
  "endOfBatch" : false,
  "loggerFqcn" : "org.apache.logging.slf4j.Log4jLogger",
  "contextMap" : { },
  "threadId" : 1,
  "threadPriority" : 5,
  "anotherKey" : 36,
  "myCustomKey" : "myCustomValue"
}
```
