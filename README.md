# store-mgmt
Java 17+, Maven, Spring Boot 3 example REST API for store managing.

REST Client samples found in file: store-mgmt.http

Hardcoded Users for Security implementation (found in SecurityConfig.java):
* john with password: doe -> ROLE: USER
* jane with password: doe -> ROLE: VIEWER, ADMIN
* admin with password: admin -> ROLE: ADMIN

## PRODUCT
First feature implemented is managing Products.
We can:
    - List all products
    - List one product by id
    - Add one new product

### DOCUMENTATION
OPEN API standard, with Swagger.
API can be access at [swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Dev links
Springboot:
1. [Actuator](http://localhost:8080/actuator)