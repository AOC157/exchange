package com.example.exchange.repository;

import com.example.exchange.model.ExchangedMoney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ExchangedMoneyRepository  extends JpaRepository<ExchangedMoney, Date> {
}
