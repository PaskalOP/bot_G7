package org.example.currency.service;

import org.example.currency.model.Currency;

import java.util.List;

public interface ICurrencyService {
    List<Double> getRate(Currency currency);
}
