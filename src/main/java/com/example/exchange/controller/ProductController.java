package com.example.exchange.controller;

import com.example.exchange.model.Product;
import com.example.exchange.service.ProductService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {

    @Autowired
    public ProductService productService;


    @PostMapping(value = "/insert")
    public void insertProduct(@Valid @RequestBody Product product) {
        productService.save(product);
    }

    @GetMapping(value = "/getAll")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping(value = "/get/{id}")
    public String getOneProduct(@PathVariable("id") int id) {
        return productService.get(id).toString();
    }
}
