package org.example.currency.model;

import lombok.Data;

@Data
public class NationalBankDto {

    private int r030;
    private String txt;
    private double rate;
    private String cc;
    private String exchangedate;
}
