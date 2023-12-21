package org.example.currency;

import org.example.currency.model.UserRequest;
import org.example.currency.service.ICurrencyService;
import org.example.currency.service.MonoBankICurrencyService;
import org.example.currency.service.NationalBankICurrencyService;
import org.example.currency.service.PrivatBankICurrencyService;
import org.example.currency.util.CurrencyFormatter;

import java.util.List;

import static org.example.currency.model.Currency.USD;

public class CurrencyService {
    ICurrencyService currencyService;

    public String getRate(UserRequest userRequest) {
        switch (userRequest.getBankName()) {
            case MONO -> currencyService = new MonoBankICurrencyService();
            case NATIONAL -> currencyService = new NationalBankICurrencyService();
            case PRIVAT -> currencyService = new PrivatBankICurrencyService();
        }

        String bank;
        switch (String.valueOf(userRequest.getBankName())) {
            case "MONO" -> bank = "МоноБанк";
            case "NATIONAL" -> bank = "НБУ";
            default ->  bank = "ПриватБанк";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Курс валют в ").append(bank);

        userRequest.getCurrencies().stream()
                .map(c -> {
                    List<Double> rates = currencyService.getRate(c);
                    return CurrencyFormatter.getFormatted(c, rates, userRequest.getNumbersAfterPoint());
                }).forEach(s -> builder.append("\n").append(s));

        return builder.toString();
    }
}
