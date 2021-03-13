package com.example.exchange.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Exchange {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @NotNull
    private int buyerId;
    @NotNull
    private int sellerId;
    @OneToMany
    @NotNull
    private List<Product> exchangedProducts;

    public Exchange(){

    }

    public Exchange(int buyerId, int sellerId, List<Product> exchangedProducts) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.exchangedProducts = exchangedProducts;
    }

    public int getId() {
        return id;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public List<Product> getExchangedProducts() {
        return exchangedProducts;
    }

    public void setExchangedProducts(List<Product> exchangedProducts) {
        this.exchangedProducts = exchangedProducts;
    }
}
