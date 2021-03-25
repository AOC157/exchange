package com.example.exchange.model;

import com.example.exchange.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Test(expected = ConstraintViolationException.class)
    public void productValidationTest1() {
        Product product = new Product();
        product.setName("cake");
        product.setColor("brown");
        product.setPrice(-20);

        productRepository.save(product);
    }

    @Test(expected = ConstraintViolationException.class)
    public void productValidationTest2() {
        Product product = new Product();
        product.setName("");
        product.setColor("brown");
        product.setPrice(5000);

        productRepository.save(product);
    }

    @Test(expected = ConstraintViolationException.class)
    public void productValidationTest3() {
        Product product = new Product();
        product.setName("cake");
        product.setColor("");
        product.setPrice(5000);

        productRepository.save(product);
    }

    @Test
    public void productValidationTest4() {
        Product product = new Product();
        product.setName("cake");
        product.setColor("brown");
        product.setPrice(5000);

        productRepository.save(product);
    }
}
