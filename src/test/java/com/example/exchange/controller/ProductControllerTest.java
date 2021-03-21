package com.example.exchange.controller;

import com.example.exchange.model.Product;
import com.example.exchange.service.ProductService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void insertTest() throws Exception {
        Product product = new Product();
        product.setName("cake");
        product.setColor("brown");
        product.setPrice(5000.0);

        ObjectMapper mapper = new ObjectMapper();
        String jsonProduct = mapper.writeValueAsString(product);

        given(productService.save(any(Product.class))).willReturn(product);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/product/insert")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonProduct))
                .andExpect(status().isOk())
                .andReturn();

        Product resultProduct = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Product.class);

        assertEquals(product.getName(), resultProduct.getName());
    }

    @Test
    public void getTest() throws Exception {
        Product product = new Product();
        product.setName("cake");
        product.setColor("brown");
        product.setPrice(5000.0);

        given(productService.get(product.getId())).willReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/get/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name"). value(product.getName()));
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

        List<Product> allProducts = Arrays.asList(product1, product2);

        given(productService.getAll()).willReturn(allProducts);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(product1.getName()))
                .andExpect(jsonPath("$[1].name").value(product2.getName()));
    }
}

