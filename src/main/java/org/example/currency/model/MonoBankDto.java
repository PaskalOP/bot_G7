package org.example.currency.model;

import lombok.Data;

@Data
public class MonoBankDto {

    private int currencyCodeA;
    private int currencyCodeB;
    private int date;
    private double rateBuy;
    private double rateSell;
    private double rateCross;
}