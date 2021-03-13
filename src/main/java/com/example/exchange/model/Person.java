package com.example.exchange.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

public class Person {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private ArrayList<Product> products;

    public Person(int id, ArrayList<Product> products) {
        this.id = id;
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }
}
