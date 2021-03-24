package com.example.exchange.service;

import com.example.exchange.model.ExchangedMoney;
import com.example.exchange.repository.ExchangedMoneyRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@EnableScheduling
@Service
public class ExchangedMoneyService {

    @Autowired
    private ExchangedMoneyRepository exchangedMoneyRepository;

    @Scheduled(cron = "0 0 0 * * ?" , zone = "GMT+3:30")
    public void save(){
        exchangedMoneyRepository.save(new ExchangedMoney());
        ExchangedMoney.setStaticMoney(0);
        ExchangedMoney.setStaticDate(new Date());
    }
    public ExchangedMoney get(Date date) {
        if (exchangedMoneyRepository.existsById(date)){
            return exchangedMoneyRepository.getOne(date);
        }
        throw new ObjectNotFoundException(date,"the date not found");
    }

    public List<ExchangedMoney> getAll(){
        return exchangedMoneyRepository.findAll();
    }
}

