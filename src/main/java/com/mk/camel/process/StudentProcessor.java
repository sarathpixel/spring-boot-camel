package com.mk.camel.process;

import com.mk.camel.model.Student;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StudentProcessor implements Processor {
    private static final Logger log = LogManager.getLogger(StudentProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Student student = (Student) exchange.getIn().getBody();
        log.debug("Inside Processor - Called with Exchange ,{}", exchange);
        student.setStatus("Allocated");
        exchange.getIn().setBody(student);
    }
}
