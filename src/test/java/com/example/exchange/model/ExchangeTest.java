package com.example.exchange.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;

public class ExchangeTest {

    private Validator validator;

    @Before
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test(expected = AssertionError.class)
    public void exchangeValidationTest1() {
        Product product1 = new Product("cake","brown",5000.0);
        Product product2 = new Product("pen","red",7000.0);

        Person person1 = new Person(Collections.singletonList(product1));

        Person person2 = new Person(Collections.singletonList(product2));

        Exchange exchange = new Exchange(person1.getId(),person2.getId(), Collections.emptyList());

        assert(validator.validate(exchange).isEmpty());
    }

    @Test
    public void exchangeValidationTest2() {
        Product product1 = new Product("cake","brown",5000.0);
        Product product2 = new Product("pen","red",7000.0);

        Person person1 = new Person(Collections.singletonList(product1));

        Person person2 = new Person(Collections.singletonList(product2));

        Exchange exchange = new Exchange(person2.getId(),person1.getId(), Collections.singletonList(product1));

        assert(validator.validate(exchange).isEmpty());
    }
}
