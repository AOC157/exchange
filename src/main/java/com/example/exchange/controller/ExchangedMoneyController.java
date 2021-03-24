package com.example.exchange.controller;

import com.example.exchange.model.ExchangedMoney;
import com.example.exchange.service.ExchangedMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/money")
@Validated
public class ExchangedMoneyController {

    @Autowired
    private ExchangedMoneyService exchangedMoneyService;

    @GetMapping(value = "/getAll")
    public List<ExchangedMoney> getAllExchanges() {
        return exchangedMoneyService.getAll();
    }

    @GetMapping(value = "/get/{id}")
    public String getOneExchange(@PathVariable("id")Date date) {
        return exchangedMoneyService.get(date).toString();
    }
}

