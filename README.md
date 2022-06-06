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

# Steps
[*Configuration*](https://github.com/lionlight/DGExchange/edit/master/README.md#configuration)<br>
[*Docker*](https://github.com/lionlight/DGExchange/edit/master/README.md#docker)<br>
[*Endpoints*](https://github.com/lionlight/DGExchange/edit/master/README.md#endpoints)<br>

# Configuration
- **You need to edit env.list like this**

```
app.rate.api-url=https://openexchangerates.org
app.gif.api-url=http://api.giphy.com
app.gif.positive-tag=rich
app.gif.negative-tag=broke
app.rate.app-id=<must-be-overriden>
app.gif.api-key=<must-be-overriden>
feign.name=<must-be-overriden>
```

- **Change environments which are marked must-be-overriden**

# Docker
<h3>Build project</h3>

- **Open a terminal and go to the app directory with the Dockerfile. Now build the container image using the docker build command.**

```
docker build -t dg-exchange-image .
```

- **Run docker container**
```
docker run -d -p 8080:8080 --name dg-exchange-container -it --env-file ./env.list dg-exchange-image
```

# Endpoints
- **Open in browser**
```
http://localhost:8080/api/v1/doc
```
**Now you see swagger api documentation like this:**
![screenshot-localhost_8080-2022 06 04-14_07_49](https://user-images.githubusercontent.com/88512563/171995837-650883da-1bbf-4afc-9180-799d28fd97c5.png)
&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;
- **Open in browser (or via swagger /api/v1/doc) /rates endpoint with parameter**
&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;

**Parameters:**
- **code=( USD/RUB and etc currency tickers )**
```
http://localhost:8080/dgexchange/gifs?code=RUB
```
**Now you can see random gif with specialized tag :money_with_wings:**
![2022-06-06_17-17-14](https://user-images.githubusercontent.com/88512563/172168239-16946a98-261d-455a-a3e9-f30720464cdc.png)


<br><br>

**API**
- **tag=( rich/broke and etc tags for search gifs)**
```
http://localhost:8080/api/v1/gifs?tag=rich
```
**Now you can see api response json with different links of the same gif*
![2022-06-06_17-11-13](https://user-images.githubusercontent.com/88512563/172167409-57d70e2d-3360-4c18-92f7-c261ad1a462f.png)

- **base=( USD/RUB and etc currency tickers )**
```
http://localhost:8080/api/v1/rates?base=RUB
```
**Now you can see api response json with different currency value response (current/yesterday)*
![2022-06-06_17-12-54](https://user-images.githubusercontent.com/88512563/172168061-17b54f17-f9b1-42b8-9948-afde1fd473fe.png)





