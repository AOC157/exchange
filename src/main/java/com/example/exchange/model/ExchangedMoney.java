package com.example.exchange.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ExchangedMoney {

    private transient static Date staticDate = new Date();

    private transient static double staticMoney = 0;

    private double money;

    @Id
    private Date date;

    public ExchangedMoney() {
        money = staticMoney;
        date = staticDate;
    }

    public static Date getStaticDate() {
        return staticDate;
    }

    public static void setStaticDate(Date staticDate) {
        ExchangedMoney.staticDate = staticDate;
    }

    public static double getStaticMoney() {
        return staticMoney;
    }

    public static void setStaticMoney(double staticMoney) {
        ExchangedMoney.staticMoney = staticMoney;
    }

    @Override
    public String toString() {
        return '{' +
                "\"date\":" + date +
                ", \"money\":" + money +
                '}';
    }
}
