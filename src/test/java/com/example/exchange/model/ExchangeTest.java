package com.example.exchange.model;

import com.example.exchange.repository.ExchangeRepository;
import com.example.exchange.repository.PersonRepository;
import com.example.exchange.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.validation.ConstraintViolationException;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeTest {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test(expected = ConstraintViolationException.class)
    public void exchangeValidationTest1() {
        Product product1 = new Product("cake","brown",5000.0);
        productRepository.save(product1);

        Product product2 = new Product("pen","red",7000.0);
        productRepository.save(product2);

        Person person1 = new Person(Collections.singletonList(product1));
        personRepository.save(person1);

        Person person2 = new Person(Collections.singletonList(product2));
        personRepository.save(person2);

        Exchange exchange = new Exchange(person1.getId(),person2.getId(), Collections.emptyList());
        exchangeRepository.save(exchange);
    }

    @Test
    public void exchangeValidationTest2() {
        Product product1 = new Product("cake","brown",5000.0);
        productRepository.save(product1);

        Product product2 = new Product("pen","red",7000.0);
        productRepository.save(product2);

        Person person1 = new Person(Collections.singletonList(product1));
        personRepository.save(person1);

        Person person2 = new Person(Collections.singletonList(product2));
        personRepository.save(person2);

        Exchange exchange = new Exchange(person2.getId(),person1.getId(), Collections.singletonList(product1));
        exchangeRepository.save(exchange);
    }
}
