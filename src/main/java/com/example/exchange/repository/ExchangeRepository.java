package com.example.exchange.repository;

import com.example.exchange.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange,Integer> {
}
