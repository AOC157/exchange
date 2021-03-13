package com.example.exchange.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

public class Exchange {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private Person buyer;
    private Person seller;
    private ArrayList<Product> exchangedProducts;

    public Exchange(Person buyer, Person seller, ArrayList<Product> exchangedProducts) {
        this.buyer = buyer;
        this.seller = seller;
        this.exchangedProducts = exchangedProducts;
    }

    public int getId() {
        return id;
    }

    public Person getBuyer() {
        return buyer;
    }

    public void setBuyer(Person buyer) {
        this.buyer = buyer;
    }

    public Person getSeller() {
        return seller;
    }

    public void setSeller(Person seller) {
        this.seller = seller;
    }

    public ArrayList<Product> getExchangedProducts() {
        return exchangedProducts;
    }

    public void setExchangedProducts(ArrayList<Product> exchangedProducts) {
        this.exchangedProducts = exchangedProducts;
    }
}
