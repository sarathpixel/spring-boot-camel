package com.mk.camel.exceptions;

import org.apache.camel.Body;
import org.apache.camel.Headers;

import java.util.Map;

public class StudentExceptionHandler {

    /**
     * This method creates the response to the caller if the student details could not
     * be processed
     *
     * @param headers the in headers
     * @param payload the in payload
     * @return the out payload
     */
    public static Object studentDetailsInvalid(@Headers Map headers, @Body String payload) {

        return "Student ERROR";
    }
}
