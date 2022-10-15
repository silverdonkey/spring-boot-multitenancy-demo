# Demo for Multitenancy with Spring Boot and JPA

### Description
Demonstrate a technical implementation of database routing with Spring boot JPA utilizing
`AbstractRoutingDataSource` for database routing at runtime. 
* Based on [Providing Multitenancy with Spring Boot](https://www.bytefish.de/blog/spring_boot_multitenancy.html)
* Extended with dockerized MySql 8 Server, managing containers with docker-compose and different spring profiles

#### Note about MySQL's 'schema vs database' notion
From MySQL 8.0 Reference Manual: [schema](https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_schema) is synonymous with [database](https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_database).

### Prep for Demo
From your command line run docker-compose:
* Start containers rebuilding it (`--build` flag) in the background (`-d` option): `docker-compose up --build -d`
* Check the containers: `docker ps -a`
* You can connect to the MySql8 Server from your favourite DB-tool:
  * url: localhost:3306
  * credentials: -u=dbuser -p=password
* Or use the Adminer: http://localhost:8181/?server=mysql-docker&username=dbuser
  * [Adminer](https://www.adminer.org/) (formerly phpMinAdmin) is a simple and lightweight php web application to work with your databases like mysql
* Open your browser and go to http://localhost:8080/entity/all
  * mydb1 is the default schema
* Better use [Postman Collection](postman/Spring%20Boot%20Multitenancy.postman_collection.json) 
  * Switch between databases (schemas) by setting the X-TenantID http-header accordingly (mydb1 or mydb2)

#### Demo Issue with startup order and dependencies
* Change the profile in the Dockerfile to `local`
* Rebuild everything:
  * `docker-compose down -v`
  * `docker-compose up --build`
* Look at the logs for "ERROR com.zaxxer.hikari.pool.HikariPool : HikariPool-1 - Exception during pool initialization. ... com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure"


### Build and Run the Spring-Boot service locally with Maven
#### Build
* `mvn clean verify`

#### Run
Precondition: MySql8 Server is started (`docker-compose up -d`) 

Use the `dev` profile which starts the Spring-Boot-App at port 8282 (to avoid conflict with the container started by docker-compose):
* `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
* Go to http://localhost:8282/entity/all
* Use the [Postman Collection](postman/Spring%20Boot%20Multitenancy.postman_collection.json)


### Commands

#### Start all Docker Container (to rebuild use '--build' flag):
* `docker-compose up` to create and start containers
* `docker-compose up -d` to start in the background/as deamon
* `docker-compose up --build -d` to rebuild and start as deamon

#### Check running Container:
* docker ps -a

#### Stop Docker Containter
NOTE: Init the DB using the [Script](database-init.sql). The docker-entrypoint-initdb.d folder will only be run once
while the container is created (instantiated) so you actually have to do a docker-compose down -v to re-activate this for the next run.
* `docker-compose stop` to stop all containers
* `docker-compose down` to stop and remove all containers
* `docker-compose down -v` to remove also volumes