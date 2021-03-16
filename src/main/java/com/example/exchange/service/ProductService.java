package com.example.exchange.service;

import com.example.exchange.model.Product;
import com.example.exchange.repository.ProductRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void save(Product product){
        productRepository.save(product);
    }

    public Product get(int id) throws NotFoundException {
        if (productRepository.existsById(id)){
            return productRepository.getOne(id);
        }
        throw new NotFoundException("");
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

}
