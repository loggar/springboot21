# SpringBoot 2.1 Webapp

## version compatibility

- java 1.8
- spring 5.1.5.RELEASE
- springboot 2.1.1.RELEASE
- tomcat embeded 9.0.34
- junit 5.0


## Run application

```
$ mvn clean package spring-boot:repackage
$ java -jar target/spring-boot-ops.war
```

```
$ mvn spring-boot:run
```

## Shutdown application

```
curl -X POST localhost:<port>/<context-path>/shutdownContext

curl -X POST localhost:26080/springboot21/shutdownContext
```

## SSE connection example

```
http://localhost:<port>/<context-path>/sample/eventsource-notification-client
```

Sample Trig SSE

```
$ curl http://localhost:<port>/<context-path>/notification-message/trig?count=100
```