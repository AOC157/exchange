package com.example.exchange.service;

import com.example.exchange.model.Exchange;
import com.example.exchange.model.ExchangedMoney;
import com.example.exchange.model.Person;
import com.example.exchange.model.Product;
import com.example.exchange.repository.ExchangeRepository;
import com.example.exchange.repository.PersonRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private PersonRepository personRepository;

    public Exchange save(Exchange exchange) {
        Person buyer = personRepository.getOne(exchange.getBuyerId());
        Person seller = personRepository.getOne(exchange.getSellerId());

        double tempExchangedMoney = ExchangedMoney.getStaticMoney();

        for(Product product : exchange.getExchangedProducts()){
            boolean existFlag = false;
            for(Product sellerProduct : seller.getProducts()){
                if(sellerProduct.getId() == product.getId()){
                    seller.getProducts().remove(sellerProduct);
                    buyer.getProducts().add(product);
                    ExchangedMoney.setStaticMoney(ExchangedMoney.getStaticMoney() + product.getPrice());
                    existFlag = true;
                    break;
                }
            }
            if(!existFlag){
                ExchangedMoney.setStaticMoney(tempExchangedMoney);
                throw new ObjectNotFoundException(product.getId(),"the product not found");
            }
        }

        personRepository.save(seller);
        personRepository.save(buyer);

        return exchangeRepository.save(exchange);
    }

    public Exchange get(int id) {
        if (exchangeRepository.existsById(id)){
            return exchangeRepository.getOne(id);
        }
        throw new ObjectNotFoundException(id,"the exchange not found");
    }

    public List<Exchange> getAll(){
        return exchangeRepository.findAll();
    }
}
