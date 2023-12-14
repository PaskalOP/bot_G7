package org.example.currency.model;

import lombok.Data;

@Data
public class PrivatBankDto {

    private Currency ccy;
    private Currency base_ccy;
    private String buy;
    private String sale;
}
