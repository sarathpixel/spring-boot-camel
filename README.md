## Spring Boot Camel

This is a example Spring Boot with Apache Camel. 

The below section details how to run the application
1) Swagger definition : student.yml in resources folder. 
    GET http://<host>>:<port>/camel/api-doc ( on local: http://localhost:8080/camel/api-doc  )
    
2) as a standalone springboot application

3) Docker - Build a docker image and run on container (You should have Docker installed) 
   1) To start, run:
            `mvn spring-boot:run`
            Then, make a POST http request to: POST localhost:8080/camel/students/list
            
            Include the HEADER: Content-Type: application/json, 
            and a BODY Payload like:
            
            {
                "students":[{
                "name": "Ram",
                "email": "ram@gmail.com",
                "age": 4
             }, {
                "name": "Shyam",
                "email": "shyam23@gmail.com",
                "age": 5
             }, {
                "name": "John",
                "email": "",
                "age": ""
             }]	
            }
            
            We will get a return code of 200 and the response: `Hello, World` - if the transform() method from Application class is uncommented and the process() method is commented
            
            [Student{name='Ram', email='ram@gmail.com', age=4, status='allocated-transform'}, Student{name='Shyam', email='shyam23@gmail.com', age=5, status='null'}, Student{name='test3', email='test3@gmail.com', age=41, status='null'}]
            

2) Build Docker Image and Run app:
To Create Docker Image:  Move to the root directory of the project and execute 'docker build -t spring-boot-camel-test-app .'
You should see the execution as below
    saraths-MacBook-Pro:spring-boot-camel sarath$ docker build -t spring-boot-camel-test-app .
    Sending build context to Docker daemon  35.11MB
    Step 1/5 : FROM java:8-jdk-alpine
    8-jdk-alpine: Pulling from library/java
    709515475419: Pull complete 
    38a1c0aaa6fd: Pull complete 
    5b58c996e33e: Pull complete 
    Digest: sha256:d49bf8c44670834d3dade17f8b84d709e7db47f1887f671a0e098bafa9bae49f
    Status: Downloaded newer image for java:8-jdk-alpine
     ---> 3fd9dd82815c
    Step 2/5 : COPY ./target/spring-boot-camel-0.0.1-SNAPSHOT.jar /usr/app/
     ---> f489bf0236c2
    Step 3/5 : WORKDIR /usr/app
     ---> Running in bc44ae841cb9
    Removing intermediate container bc44ae841cb9
     ---> 93bfb85df2a3
    Step 4/5 : RUN sh -c 'touch spring-boot-camel-0.0.1-SNAPSHOT.jar'
     ---> Running in b6ecb8370f54
    Removing intermediate container b6ecb8370f54
     ---> 13a02dabb146
    Step 5/5 : ENTRYPOINT ["java","-jar","spring-boot-camel-0.0.1-SNAPSHOT.jar"]
     ---> Running in ecd9ff443eb7
    Removing intermediate container ecd9ff443eb7
     ---> 996f81ac39df
    Successfully built 996f81ac39df
    Successfully tagged spring-boot-camel-test-app:latest

saraths-MacBook-Pro:spring-boot-camel sarath$ docker images
REPOSITORY                   TAG                  IMAGE ID            CREATED              SIZE
spring-boot-camel-test-app   latest               fcc54a402922        About a minute ago   214MB

saraths-MacBook-Pro:spring-boot-camel sarath$ docker run -p 8090:8080 spring-boot-camel-test-app .


