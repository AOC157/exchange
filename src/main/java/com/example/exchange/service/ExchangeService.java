package com.example.exchange.service;

import com.example.exchange.model.Exchange;
import com.example.exchange.repository.ExchangeRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    public void save(Exchange exchange){
        exchangeRepository.save(exchange);
    }

    public Exchange get(int id) throws NotFoundException {
        if (exchangeRepository.existsById(id)){
            return exchangeRepository.getOne(id);
        }
        throw new NotFoundException("");
    }

    public List<Exchange> getAll(){
        return exchangeRepository.findAll();
    }
}
