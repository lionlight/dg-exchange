# DGExchange
&nbsp;&nbsp;&nbsp;&nbsp; **Project using:**<br>
&nbsp;&nbsp;&nbsp;&nbsp;
[*Spring Boot 2*](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) <br>
&nbsp;&nbsp;&nbsp;&nbsp;
[*Spring Cloud OpenFeign*](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/) <br>
&nbsp;&nbsp;&nbsp;&nbsp;
[*Lombok*](https://projectlombok.org/features/all) <br>
&nbsp;&nbsp;&nbsp;&nbsp;
[*Docker*](https://docs.docker.com/) <br>
&nbsp;&nbsp;&nbsp;&nbsp;
[*Gson*](https://github.com/google/gson#readme) <br>
&nbsp;&nbsp;&nbsp;&nbsp;
[*Swagger*](https://swagger.io/tools/open-source/open-source-integrations/) <br>
&nbsp;&nbsp;&nbsp;&nbsp;
**Testing:** <br>
&nbsp;&nbsp;&nbsp;&nbsp;
[*WireMock*](https://wiremock.org/docs) <br>
&nbsp;&nbsp;&nbsp;&nbsp;
[*Mockito*](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) <br>
&nbsp;&nbsp;&nbsp;&nbsp;
[*Junit 5*](https://junit.org/junit5/docs/current/user-guide/) <br>
&nbsp;&nbsp;&nbsp;&nbsp;

# STEPS
[*Configuration*](https://github.com/lionlight/DGExchange/edit/master/README.md#configuration)<br>
[*Docker*](https://github.com/lionlight/DGExchange/edit/master/README.md#docker)<br>
[*Endpoints*](https://github.com/lionlight/DGExchange/edit/master/README.md#endpoints)<br>

# Configuration
- **See application.yml profiles**
```
spring:
  profiles.active: production
```
- **If profiles.active is set to "production" than you need to modify build configuration, past your evn and change environment variables**
```
app.gif.api-key=<must-be-overriden>
app.gif.api-url=<must-be-overriden>
app.gif.positive-tag=<must-be-overriden>
app.rate.api-url=<must-be-overriden>
app.rate.app-id=<must-be-overriden>
feign.name=<must-be-overriden>
```

- **Like this**
![2022-06-04_15-35-15](https://user-images.githubusercontent.com/88512563/171997272-8dfe33b0-4b18-4184-8337-23ef779cf804.png)


**Or change profile from production to test with my default configuration**
```
spring:
  profiles.active: test
```

# Docker
<h3>Build project</h3>

- **Run in the command line:**
```
gradle build
```
- **Open a terminal and go to the app directory with the Dockerfile. Now build the container image using the docker build command.**
```
docker build -t dg-exchange-image .
```
- **Run docker container**
```
docker run --name dg-exchange-container -p 8080:8080 -it dg-exchange-image
```

# Endpoints
- **Open in browser**
```
http://localhost:8080/api/doc
```
**Now you see swagger api documentation like this:**
![screenshot-localhost_8080-2022 06 04-14_07_49](https://user-images.githubusercontent.com/88512563/171995837-650883da-1bbf-4afc-9180-799d28fd97c5.png)

- **Open in browser (or via swagger /api/doc) /rates endpoint with parameter **

**Parameters:**
- **base=( USD/RUB and etc currency tickers )**
```
http://localhost:8080/api/v1/rates?base=RUB
```
**Now you can see random gif with specialized tag:**
![2022-06-04_15-04-23](https://user-images.githubusercontent.com/88512563/171996351-1b002dd3-4fac-42c7-945d-85fc9d60b25c.png)

<br><br>


