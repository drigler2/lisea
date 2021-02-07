# about lisea

lisea is a Spring ${insert adjective here} example app. It's not
intended to serve any particular purpose, or even to be deployed but
more as an experiment in software engineering design. It's intended
however to be fully functional. Lack of business logic is a serious
design limitation of this kind of project, but I will try to keep
things as plugable as possible.

### naming

Specific services are named after Roman gods due to their hierarchical
relationships and the fact that I find the ancient Romans
fascinating. lisea (name) is inherited from an older project, I like
it so I kept it.

### packaging

projects are packaged as a single mvn project, I believe this is more
convenient ATM. There won't be any shared dependencies except maybe
some very basic ones, ie language or collections stuff... Also, since
there aren't many/any shared dependencies, it's easy to brake this
apart if needed.

### persistence

I'm using POSTGRES but you can probably use any vendor as all
jdbc-queries are distributed at runtime from mercury via zookeeper. I
should probably provide some out-of-the-box queries....

### lombok

I intend to use lombok, but only for boilerplate code such as
constructors, getters and setters, since I find that efficient. Lombok
can do much more, but I'm careful about using more ATM.

### projects in lisea ecosystem
###### juno
user credentials store, literally nothing else, for now at least.

###### mercury
holds configuration for specific services, this service
needs to be configured/running in order to run other services (juno
for example) as it provides juno with info on how to connect to it's
database, as well as e.g. jdbc queries.

You have to have zookeeper up and running in order to mercury to do
it's job, as it distributes all this info via zookeeper.

###### vulcan
cpp asio based application server which produces (somewhat) serial
ID-s based on snowflake(d) implementations. There are minor issues
with negative id-s but server can handle up to 10000 req/s on my PC
(oldish i5) which is good enaough for now.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/maven-plugin/)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-security)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Project Lombok](https://projectlombok.org/features/all)
* [POSTGRES](https://www.postgresql.org/docs/)
* [Bootstrap](https://getbootstrap.com/docs/4.1/getting-started/introduction/)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

LocalWords:  lisea
