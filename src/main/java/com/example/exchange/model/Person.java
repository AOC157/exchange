package com.example.exchange.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Person {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @OneToMany
    private List<Product> products;

    public Person(){

    }

    public Person(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"products\":" + products +
                '}';
    }
}
