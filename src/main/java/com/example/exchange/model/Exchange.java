package com.example.exchange.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Exchange {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @NotNull
    private transient Person buyer;
    @NotNull
    private transient Person seller;
    @OneToMany
    @NotNull
    private List<Product> exchangedProducts;

    public Exchange(){

    }

    public Exchange(Person buyer, Person seller, List<Product> exchangedProducts) {
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

    public List<Product> getExchangedProducts() {
        return exchangedProducts;
    }

    public void setExchangedProducts(List<Product> exchangedProducts) {
        this.exchangedProducts = exchangedProducts;
    }
}
