

# Overview - myRetail RESTful service
```
myRetail is a rapidly growing company with HQ in Richmond, VA and 
over 200 stores across the east coast. myRetail wants to make its 
internal data available to any number of client devices, from 
myRetail.com to native mobile apps. 

The goal for this project was to create an end-to-end Proof-of-Concept 
for a products API, which will aggregate product data from multiple 
sources and return it as JSON to the caller. 

the project was implemented with java 8 and Spring Boot  
the database used was MongoDB, run locally  
unit testing was done with the spock framework  

tools used included: maven, Postman, git, mongodb  
these should be installed locally  
```

# Environment Setup

accessing the project:  
download or clone the github project:  
https://github.com/tmart593/myRetail  

install MongoDB and start running on localhost:27017  

run the database setup script (dbsetup.js) located in <project root dir>/scripts/  

$ mongo localhost:27017/myretail ./scripts/dbsetup.js  

  
  
# Running the application

from the project install directory, build, test and run the application  

$ mvn clean package  
$ java -jar target/myretail-0.0.1-SNAPSHOT.jar  

# Testing

unit tests can be run (from the project root dir):  

$ mvn test  

for blackbox/functional testing,  
open the Postman app and import the request collection  
found at <project root>/myretail.postman_collection.json  

open and run each of the tests in postman  

 there are GET and PUT example requests:
 
 for example, the request 'GET product data'  
 
 http://localhost:8080/products/13860425  
 
 should get the response  (status: 200):  
 ```
 {
    "id": 13860425,
    "name": "Godzilla (Blu-ray)",
    "current_price": {
        "value": 1.78,
        "currency_code": "USD"
    }
}
```
  

as an alternative to postman, curl commands can be used to run tests -  

note that the -v option produces verbose output so the 
return http status codes are evident   
(the -s (silent) option removes the progress meter)  

  $ curl -vs http://localhost:8080/products/13860425  
	$ curl -vs http://localhost:8080/products/13860429  
	$ curl -vs -H "Content-Type: application/json" -X PUT -d '{"id": 13860425,"name": "Godzilla (Blu-ray)","current_price": {"value": 11.78,"validCurrencyCode": true,"currency_code": "USD"}}' \  
	         http://localhost:8080/products/13860425  

output can also be directed to a file by appending to the command: > test_GET_notfound.txt 2>&1  



# Preparing for production 



add a 'develop' branch for devlopers to make PR requests to

add more documentation  

add Swagger support

add profiling/monitoring such as prometheus/micrometer/grafana

improve error messages for field level errors - path variables, etc

add security to the app - OAuth2/stargate etc

__________________________________________________________

testing and CI/CD -

add more unit tests to gain 100% code coverage

create integration tests

set up jenkins pipeline (jenkinsfile) to include above tests, ideally also including
          code quality software such as sonarqube
          
jenkins should handle creating tags for releases (based on the master branch)
                   
performance tests done with jmeter or similar load testing tool
     do a significant amount of this and confirm SLA is being met

create a product in a deployment environment (openshift, azure, AWS etc)
___________________________________________________________

refactor Update Product api - 

```
the product info service should be replaced with an actual internal service
(including all the tests for each env)

if the update product api is intended to update
both the price data and the other data (name etc)
then several approaches could be taken -
     
     1. if the one service should update all the data for a product
     (ie more than just the price data), and that
     data resides in 2 data sources that cannot be
     connected as part of a transaction or 2 phase commit,
     then some type of eventual consistency approach is needed -
     for example, through kafka messaging updates to
     each data source could taken care of -
     this would depend on the time allowed for eventual consistency
     and the recovery process.
     
     2. more ideally each data source would have its own microservice
        and be updated independently, ie the client
        would call each one separately

     3. the 2 data sources could be part of a (RDBMS) transaction
         i.e. use traditional relational databases that have rollback ability
         or even have the data stored in one relational db,
         thus making a transaction very easy

          
```
  





