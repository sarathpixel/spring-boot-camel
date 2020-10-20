package com.mk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mk.camel.SpringBootCamelApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootCamelApplication.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
