package com.mk.camel.route;

import com.mk.camel.model.Student;
import com.mk.camel.model.StudentAllocation;
import com.mk.camel.model.StudentListRequest;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentRouteTest2 {
    @EndpointInject("mock:seda:report")
    protected MockEndpoint resultEndpoint;
    @EndpointInject("mock:reception")
    protected MockEndpoint receptionEndpoint;
    @Produce("direct:nursery")
    protected ProducerTemplate template;
    @Autowired
    private TestRestTemplate restTemplate;

    @DirtiesContext
    @Test
    public void testSedaReportISInvoked() throws Exception {
        String expectedBody = "{name=Ram, email=ram@gmail.com, age=4, status=null}";
        resultEndpoint.expectedBodiesReceived();
        template.sendBody(new Student("Ram", "ram@gmail.com", 4));//sendBodyAndHeader(expectedBody, "foo", "bar");
        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testDirectReceptionIsInvoked() throws InterruptedException {
        StudentListRequest stList = new StudentListRequest();
        stList.setStudents(new ArrayList<Student>(Arrays.asList(
                new Student("Ram", "ram@gmail.com", 4),
                new Student("Mohan", "Mohan@gmail.com", 5))));

        // Call the REST API
        ResponseEntity<StudentAllocation> result = restTemplate.postForEntity("/camel/students/list", stList, StudentAllocation.class);
        String expectedBody = "{name=Ram, email=ram@gmail.com, age=4, status=null}";
        receptionEndpoint.allMessages();
        receptionEndpoint.assertIsSatisfied();
    }

}

