package com.example.exchange.controller;

import com.example.exchange.model.Exchange;
import com.example.exchange.model.Person;
import com.example.exchange.model.Product;
import com.example.exchange.repository.ExchangeRepository;
import com.example.exchange.repository.PersonRepository;
import com.example.exchange.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "/application.properties")
@EnableConfigurationProperties
@ActiveProfiles("test")
@SpringBootTest
public class ExchangeControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void insertExchangeTest() throws Exception {
        Product savedProduct1 = productRepository.save(new Product("cake" ,"brown", 5000));
        Product savedProduct2 = productRepository.save(new Product("pen" ,"red", 7000));

        Person person1 = personRepository.save(new Person(new ArrayList<>(Arrays.asList(savedProduct1,savedProduct2))));
        Person person2 = personRepository.save(new Person(new ArrayList<>()));

        Exchange exchange = new Exchange(person2.getId(),person1.getId(),person1.getProducts());

        ObjectMapper mapper = new ObjectMapper();
        String jsonExchange = mapper.writeValueAsString(exchange);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/exchange/insert")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonExchange))
                .andExpect(status().isOk())
                .andReturn();

        Exchange savedExchange = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Exchange.class);

        assertThat(savedExchange.getExchangedProducts().get(0).getId()).isEqualTo(savedProduct1.getId());
        assertThat(savedExchange.getExchangedProducts().get(0).getName()).isEqualTo(savedProduct1.getName());
        assertThat(savedExchange.getExchangedProducts().get(0).getColor()).isEqualTo(savedProduct1.getColor());
        assertThat(savedExchange.getExchangedProducts().get(0).getPrice()).isEqualTo(savedProduct1.getPrice());

        assertThat(savedExchange.getExchangedProducts().get(1).getId()).isEqualTo(savedProduct2.getId());
        assertThat(savedExchange.getExchangedProducts().get(1).getName()).isEqualTo(savedProduct2.getName());
        assertThat(savedExchange.getExchangedProducts().get(1).getColor()).isEqualTo(savedProduct2.getColor());
        assertThat(savedExchange.getExchangedProducts().get(1).getPrice()).isEqualTo(savedProduct2.getPrice());
    }

    @Test
    public void getOneExchangeTest() throws Exception {
        Product savedProduct1 = productRepository.save(new Product("cookie" ,"brown", 5000));
        Product savedProduct2 = productRepository.save(new Product("pencil" ,"black", 7000));

        Exchange savedExchange = exchangeRepository.save(new Exchange(1,2,new ArrayList<>(Arrays.asList(savedProduct1,savedProduct2))));

        mockMvc.perform(MockMvcRequestBuilders.get("/exchange/get/" + savedExchange.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exchangedProducts[0].id").value(savedExchange.getExchangedProducts().get(0).getId()))
                .andExpect(jsonPath("$.exchangedProducts[0].name").value(savedExchange.getExchangedProducts().get(0).getName()))
                .andExpect(jsonPath("$.exchangedProducts[0].color").value(savedExchange.getExchangedProducts().get(0).getColor()))
                .andExpect(jsonPath("$.exchangedProducts[0].price").value(savedExchange.getExchangedProducts().get(0).getPrice()))
                .andExpect(jsonPath("$.sellerId").value(savedExchange.getSellerId()))
                .andExpect(jsonPath("$.buyerId").value(savedExchange.getBuyerId()));
    }

    @Test
    public void getAllExchangesTest() throws Exception {

        Product savedProduct1 = productRepository.save(new Product("ball" ,"yellow", 5000));
        Product savedProduct2 = productRepository.save(new Product("oil" ,"yellow", 37000));

        Exchange savedExchange = exchangeRepository.save(new Exchange(1,2,new ArrayList<>(Arrays.asList(savedProduct1,savedProduct2))));

        int size = exchangeRepository.findAll().size();

        mockMvc.perform(MockMvcRequestBuilders.get("/exchange/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(size)))
                .andExpect(jsonPath("$["+ (size - 1) +"].exchangedProducts[0].name").value(savedExchange.getExchangedProducts().get(0).getName()))
                .andExpect(jsonPath("$["+ (size - 1) +"].exchangedProducts[0].color").value(savedExchange.getExchangedProducts().get(0).getColor()))
                .andExpect(jsonPath("$["+ (size - 1) +"].exchangedProducts[0].price").value(savedExchange.getExchangedProducts().get(0).getPrice()))
                .andExpect(jsonPath("$["+ (size - 1) +"].sellerId").value(savedExchange.getSellerId()))
                .andExpect(jsonPath("$["+ (size - 1) +"].buyerId").value(savedExchange.getBuyerId()));
    }
}
