package org.example.currency.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.currency.model.Currency;
import org.example.currency.model.MonoBankDto;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MonoBankICurrencyService implements ICurrencyService {

    @Override
    public List<Double> getRate(Currency currency) {
        String url = "https://api.monobank.ua/bank/currency";

        String jsonString = "";
        try {

            jsonString = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();

        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        Type type = TypeToken
                .getParameterized(List.class, MonoBankDto.class)
                .getType();
        List<MonoBankDto> list = new Gson().fromJson(jsonString, type);

        List<Double> result = new ArrayList<>();

        list.stream()
                .filter(c -> c.getCurrencyCodeA() == currency.getCode())
                .filter(c -> c.getCurrencyCodeB() == Currency.UAH.getCode())
                .forEach(dto -> {
                    result.add(dto.getRateBuy());
                    result.add(dto.getRateSell());
                });

        return result;

    }
}