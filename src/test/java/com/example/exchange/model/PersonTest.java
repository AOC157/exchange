package com.example.exchange.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
public class PersonTest {

    private Validator validator;

    @Before
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void personValidationTest1() {
        Product product1 = new Product("cake","brown",5000.0);
        Product product2 = new Product("pen","red",7000.0);

        Person person1 = new Person(Arrays.asList(product1,product2));

        assert(validator.validate(person1).isEmpty());
    }
}
