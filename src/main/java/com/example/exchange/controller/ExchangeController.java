package com.example.exchange.controller;

import com.example.exchange.model.Exchange;
import com.example.exchange.model.Person;
import com.example.exchange.service.ExchangeService;
import com.example.exchange.service.PersonService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/exchange")
@Validated
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @PostMapping(value = "/insert")
    public Exchange insertExchange(@Valid @RequestBody Exchange exchange) throws IOException {
        return exchangeService.save(exchange);
    }

    @GetMapping(value = "/getAll")
    public List<Exchange> getAllExchanges() {
        return exchangeService.getAll();
    }

    @GetMapping(value = "/get/{id}")
    public String getOneExchange(@PathVariable("id") int id) throws NotFoundException {
        return exchangeService.get(id).toString();
    }
}
