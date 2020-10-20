package com.mk.camel.route;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mk.camel.exceptions.StudentException;
import com.mk.camel.exceptions.StudentExceptionHandler;
import com.mk.camel.model.Student;
import com.mk.camel.model.StudentAllocation;
import com.mk.camel.model.StudentListRequest;
import com.mk.camel.process.StudentProcessor;
import com.mk.camel.transform.MyAggregationStrategy;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;


@Component
class StudentRoute extends RouteBuilder {
    @Value("${server.port}")
    String serverPort;

    @Value("${test.api.path}")
    String contextPath;

    @Override
    public void configure() throws JAXBException {

        CamelContext context = new DefaultCamelContext();

        // http://localhost:8080/camel/api-doc
        restConfiguration().contextPath(contextPath) //
                .port(serverPort)
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Student API")
                .apiProperty("api.version", "v1")
                .apiProperty("cors", "true") // cross-site
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.auto)
                .dataFormatProperty("prettyPrint", "true");


        // we do special error handling for when Invalid Student details  is
        // thrown
        onException(StudentException.class)
                .handled(true)
                .bean(StudentExceptionHandler.class, "studentDetailsInvalid")
                .to("mock:error");

        errorHandler(deadLetterChannel("mock:error").maximumRedeliveries(1));

        onException(JsonMappingException.class)
                .handled(true)
                .transform()
                .simple("Invalid JSON")
                .to("mock:error")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400));

        onException(HttpServerErrorException.InternalServerError.class)
                .handled(true)
                .transform(exceptionMessage())
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500));


        rest("/students/").description("Students Service")
                .id("api-route")
                .post("/list")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)

                .to("json-validator:strudentschema.json")
                .bindingMode(RestBindingMode.auto)

                .type(StudentListRequest.class)
                .enableCORS(true)
                .outType(StudentAllocation.class)
                .to("direct:allocate");

        from("direct:allocate")
                .routeId("sorting-id")
                .transform().body(StudentListRequest.class, StudentListRequest::getStudents)
                .split(body(), new MyAggregationStrategy())
                .convertBodyTo(Student.class)
                .choice()
                    .when().simple("${body.age} == 4").to("direct:nursery")
                    .when().simple("${body.age} == 5").to("direct:reception")
                    .otherwise().to("direct:others")
                .marshal().json(true)
                .log("Response = ${body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));


        from("direct:nursery")
                .routeId("route-nursery")
                .log("Nursery allocation : ${body}")
                .to("seda:report")
                .setBody().simple("Nursery");
        //.to("direct:transform");

        from("direct:reception").
                routeId("route-reception")
                .log("Reception allocation : ${body}")
                .transform().simple("reception")
                .process(new StudentProcessor())
                .to("mock:reception");


        from("direct:others")
                .routeId("route-others")
                .log("Handling Something Other : ${body}")
                .transform().simple("others");


        from("seda:report")
                .routeId("route-report")
                .log("Sending to File : ${body}")
                .transform().simple("Nursery:${body}")
                .to("file:target/reports");

        from("direct:transform")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Student student = (Student) exchange.getIn()
                                .getBody();
                        student.setStatus("allocated-transform");
                        exchange.getIn()
                                .setBody(student);
                    }
                });

    }
}

