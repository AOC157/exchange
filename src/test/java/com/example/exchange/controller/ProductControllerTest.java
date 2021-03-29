package com.example.exchange.controller;

import com.example.exchange.model.Product;
import com.example.exchange.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class ProductControllerTest {


    @Autowired
    private ProductRepository productRepository;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void insertProductTest() throws Exception {
        Product product = new Product();
        product.setName("cake");
        product.setColor("brown");
        product.setPrice(5000.0);

        Product savedProduct = productRepository.save(product);

        ObjectMapper mapper = new ObjectMapper();
        String jsonProduct = mapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/product/insert")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonProduct))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedProduct.getId()))
                .andExpect(jsonPath("$.color").value(savedProduct.getColor()))
                .andExpect(jsonPath("$.price").value(savedProduct.getPrice()))
                .andExpect(jsonPath("$.name").value(savedProduct.getName()));
    }

    @Test
    public void getOneProductTest() throws Exception {
        Product savedProduct = productRepository.save(new Product("cake" , "brown" , 5000));

        mockMvc.perform(MockMvcRequestBuilders.get("/product/get/" + savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id"). value(savedProduct.getId()))
                .andExpect(jsonPath("$.name"). value(savedProduct.getName()))
                .andExpect(jsonPath("$.color"). value(savedProduct.getColor()))
                .andExpect(jsonPath("$.price"). value(savedProduct.getPrice()));
    }

    @Test
    public void getAllProductsTest() throws Exception {
        int sizeBeforeSave = productRepository.findAll().size();

        Product savedProduct1 = productRepository.save(new Product("cake" ,"brown", 5000));
        Product savedProduct2 = productRepository.save(new Product("pen" ,"red", 7000));

        mockMvc.perform(MockMvcRequestBuilders.get("/product/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(sizeBeforeSave + 2)))
                .andExpect(jsonPath("$[" + (sizeBeforeSave) +"].id").value(savedProduct1.getId()))
                .andExpect(jsonPath("$[" + (sizeBeforeSave) +"].color").value(savedProduct1.getColor()))
                .andExpect(jsonPath("$[" + (sizeBeforeSave) +"].price").value(savedProduct1.getPrice()))
                .andExpect(jsonPath("$[" + (sizeBeforeSave) +"].name").value(savedProduct1.getName()))
                .andExpect(jsonPath("$[" + (sizeBeforeSave + 1) +"].id").value(savedProduct2.getId()))
                .andExpect(jsonPath("$[" + (sizeBeforeSave + 1) +"].color").value(savedProduct2.getColor()))
                .andExpect(jsonPath("$[" + (sizeBeforeSave + 1) +"].price").value(savedProduct2.getPrice()))
                .andExpect(jsonPath("$[" + (sizeBeforeSave + 1) +"].name").value(savedProduct2.getName()));
    }
}