package com.example.exchange.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@RunWith(SpringRunner.class)
public class ProductTest {

    private Validator validator;

    @Before
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test(expected = AssertionError.class)
    public void productValidationTest1() {
        Product product = new Product();
        product.setName("cake");
        product.setColor("brown");
        product.setPrice(-20);

        assert(validator.validate(product).isEmpty());
    }

    @Test(expected = AssertionError.class)
    public void productValidationTest2() {
        Product product = new Product();
        product.setName("");
        product.setColor("brown");
        product.setPrice(5000);

        assert(validator.validate(product).isEmpty());
    }

    @Test(expected = AssertionError.class)
    public void productValidationTest3() {
        Product product = new Product();
        product.setName("cake");
        product.setColor("");
        product.setPrice(5000);

        assert(validator.validate(product).isEmpty());
    }

    @Test
    public void productValidationTest4() {
        Product product = new Product();
        product.setName("cake");
        product.setColor("brown");
        product.setPrice(5000);

        assert(validator.validate(product).isEmpty());
    }
}
