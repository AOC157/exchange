package com.example.exchange.controller;

import com.example.exchange.model.Person;
import com.example.exchange.model.Product;
import com.example.exchange.repository.PersonRepository;
import com.example.exchange.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class PersonControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PersonRepository personRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void insertPersonTest() throws Exception {
        Product savedProduct1 = productRepository.save(new Product("cake" ,"brown", 5000));
        Product savedProduct2 = productRepository.save(new Product("pen" ,"red", 7000));

        Person person = new Person(Arrays.asList(savedProduct1,savedProduct2));

        ObjectMapper mapper = new ObjectMapper();
        String jsonPerson = mapper.writeValueAsString(person);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/person/insert")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonPerson))
                .andExpect(status().isOk())
                .andReturn();

        Person resultPerson = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Person.class);

        assertThat(resultPerson.getProducts().get(0).getId()).isEqualTo(savedProduct1.getId());
        assertThat(resultPerson.getProducts().get(0).getName()).isEqualTo(savedProduct1.getName());
        assertThat(resultPerson.getProducts().get(0).getColor()).isEqualTo(savedProduct1.getColor());
        assertThat(resultPerson.getProducts().get(0).getPrice()).isEqualTo(savedProduct1.getPrice());

        assertThat(resultPerson.getProducts().get(1).getId()).isEqualTo(savedProduct2.getId());
        assertThat(resultPerson.getProducts().get(1).getName()).isEqualTo(savedProduct2.getName());
        assertThat(resultPerson.getProducts().get(1).getColor()).isEqualTo(savedProduct2.getColor());
        assertThat(resultPerson.getProducts().get(1).getPrice()).isEqualTo(savedProduct2.getPrice());
    }

    @Test
    public void getOnePersonTest() throws Exception {
        Product savedProduct1 = productRepository.save(new Product("cookie" ,"brown", 5000));
        Product savedProduct2 = productRepository.save(new Product("pen" ,"red", 8000));

        Person savedPerson = personRepository.save(new Person(Arrays.asList(savedProduct1,savedProduct2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/person/get/" + savedPerson.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedPerson.getId()))
                .andExpect(jsonPath("$.products[0].name").value(savedPerson.getProducts().get(0).getName()))
                .andExpect(jsonPath("$.products[0].color").value(savedPerson.getProducts().get(0).getColor()))
                .andExpect(jsonPath("$.products[1].id").value(savedPerson.getProducts().get(1).getId()))
                .andExpect(jsonPath("$.products[1].price").value(savedPerson.getProducts().get(1).getPrice()));
    }

    @Test
    public void getAllPeopleTest() throws Exception {
        Product savedProduct = productRepository.save(new Product("pencil" ,"black", 3000));

        Person savedPerson = personRepository.save(new Person(Collections.singletonList(savedProduct)));

        int size = personRepository.findAll().size();

        mockMvc.perform(MockMvcRequestBuilders.get("/person/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(size)))
                .andExpect(jsonPath("$[" + (size - 1) +"].products[0].id").value(savedPerson.getProducts().get(0).getId()))
                .andExpect(jsonPath("$[" + (size - 1) +"].products[0].name").value(savedPerson.getProducts().get(0).getName()))
                .andExpect(jsonPath("$[" + (size - 1) +"].products[0].color").value(savedPerson.getProducts().get(0).getColor()));
    }
}