package com.example.exchange.controller;

import com.example.exchange.model.Exchange;
import com.example.exchange.model.Person;
import com.example.exchange.model.Product;
import com.example.exchange.service.ExchangeService;
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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ExchangeController.class)
public class ExchangeControllerTest {

    @MockBean
    private ExchangeService exchangeService;

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

        Person person1 = new Person(Collections.singletonList(product1));
        Person person2 = new Person(Collections.singletonList(product2));

        Exchange exchange = new Exchange(person2.getId(),person1.getId(),person1.getProducts());

        ObjectMapper mapper = new ObjectMapper();
        String jsonExchange = mapper.writeValueAsString(exchange);

        given(exchangeService.save(any(Exchange.class))).willReturn(exchange);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/exchange/insert")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonExchange))
                .andExpect(status().isOk())
                .andReturn();

        Exchange resultExchange = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Exchange.class);

        assertEquals(exchange.getExchangedProducts().get(0).getName(),resultExchange.getExchangedProducts().get(0).getName());
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

        Person person1 = new Person(Collections.singletonList(product1));
        Person person2 = new Person(Collections.singletonList(product2));

        Exchange exchange = new Exchange(person2.getId(),person1.getId(),person1.getProducts());

        given(exchangeService.get(exchange.getId())).willReturn(exchange);

        mockMvc.perform(MockMvcRequestBuilders.get("/exchange/get/" + exchange.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exchangedProducts[0].name").value(exchange.getExchangedProducts().get(0).getName()));
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

        Exchange exchange = new Exchange(person2.getId(),person1.getId(),person1.getProducts());

        List<Exchange> allExchanges = Collections.singletonList(exchange);

        given(exchangeService.getAll()).willReturn(allExchanges);

        mockMvc.perform(MockMvcRequestBuilders.get("/exchange/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].exchangedProducts[0].name").value(exchange.getExchangedProducts().get(0).getName()));
    }
}
