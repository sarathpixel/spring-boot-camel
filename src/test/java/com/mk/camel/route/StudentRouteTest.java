package com.mk.camel.route;

import com.mk.camel.model.Student;
import com.mk.camel.model.StudentAllocation;
import com.mk.camel.model.StudentListRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentRouteTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenPostisCalledWithStudentList_ResponseWithAllocationISRecieved() {
        StudentListRequest stList = new StudentListRequest();
        stList.setStudents(new ArrayList<Student>(Arrays.asList(
                new Student("Ram", "ram@gmail.com", 4),
                new Student("Mohan", "Mohan@gmail.com", 5))));

        // Call the REST API
        ResponseEntity<StudentAllocation> result = restTemplate.postForEntity("/camel/students/list", stList, StudentAllocation.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        StudentAllocation s = (StudentAllocation) result.getBody();
        assertThat(s.getNursery() == 1);
    }

    @Test
    public void givenPostisCalledWithInvalidJSON_Response_Bad_Request() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String invalidJson = "{\"students\":[{ \"name\": \"Ram\"\"email\": \"ram@gmail.com\",\"age\": 4}]}";
        HttpEntity<String> request = new HttpEntity<String>(invalidJson, headers);

        ResponseEntity<String> result = restTemplate.postForEntity("/camel/students/list", request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertEquals("Invalid JSON", result.getBody());
    }
}

