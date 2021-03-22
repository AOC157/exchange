package com.example.exchange.controller;

import com.example.exchange.model.Person;
import com.example.exchange.model.Product;
import com.example.exchange.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void insertTest() throws Exception {
        Product product1 = new Product();
        product1.setName("cake");
        product1.setColor("brown");
        product1.setPrice(5000.0);

        Product product2 = new Product();
        product2.setName("pen");
        product2.setColor("red");
        product2.setPrice(7000.0);

        Person person = new Person(Arrays.asList(product1, product2));

        ObjectMapper mapper = new ObjectMapper();
        String jsonPerson = mapper.writeValueAsString(person);

        given(personService.save(any(Person.class))).willReturn(person);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/person/insert")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk())
                .andReturn();

        Person resultPerson = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Person.class);

        assertEquals(person.getProducts().get(0).getName(),resultPerson.getProducts().get(0).getName());
    }

    @Test
    public void getTest() throws Exception {
        Product product1 = new Product();
        product1.setName("cake");
        product1.setColor("brown");
        product1.setPrice(5000.0);

        Product product2 = new Product();
        product2.setName("pen");
        product2.setColor("red");
        product2.setPrice(7000.0);

        Person person = new Person(Arrays.asList(product1, product2));

        given(personService.get(any(Integer.class))).willReturn(person);

        mockMvc.perform(MockMvcRequestBuilders.get("/person/get/" + person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].name").value(product1.getName()));
    }

    @Test
    public void getAllTest() throws Exception {
        Product product1 = new Product();
        product1.setName("cake");
        product1.setColor("brown");
        product1.setPrice(5000.0);

        Product product2 = new Product();
        product2.setName("pen");
        product2.setColor("red");
        product2.setPrice(7000.0);

        Person person1 = new Person(Collections.singletonList(product1));
        Person person2 = new Person(Collections.singletonList(product2));


        List<Person> allPeople = Arrays.asList(person1,person2);

        given(personService.getAll()).willReturn(allPeople);

        mockMvc.perform(MockMvcRequestBuilders.get("/person/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].products[0].name").value(product1.getName()))
                .andExpect(jsonPath("$[1].products[0].color").value(product2.getColor()));
    }
}


