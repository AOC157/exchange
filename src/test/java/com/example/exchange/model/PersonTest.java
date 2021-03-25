package com.example.exchange.model;

import com.example.exchange.repository.PersonRepository;
import com.example.exchange.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void personValidationTest1() {
        Product product1 = new Product("cake","brown",5000.0);
        productRepository.save(product1);

        Product product2 = new Product("pen","red",7000.0);
        productRepository.save(product2);

        Person person1 = new Person(Arrays.asList(product1,product2));

        personRepository.save(person1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void personOneToManyValidationTest2() {
        Product product1 = new Product("cake","brown",5000.0);
        productRepository.save(product1);

        Product product2 = new Product("pen","red",7000.0);
        productRepository.save(product2);

        Person person1 = new Person(Arrays.asList(product1,product2));
        Person person2 = new Person(Collections.singletonList(product2));

        personRepository.save(person1);
        personRepository.save(person2);
    }
}
