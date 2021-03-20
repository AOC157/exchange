package com.example.exchange.service;

import com.example.exchange.model.Product;
import com.example.exchange.repository.ProductRepository;
import org.hibernate.ObjectNotFoundException;
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

    public Product get(int id) {
        if (productRepository.existsById(id)){
            return productRepository.getOne(id);
        }
        throw new ObjectNotFoundException(id,"the product not found");
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

}
