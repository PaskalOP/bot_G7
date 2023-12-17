package org.example.currency.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.currency.model.Currency;
import org.example.currency.model.PrivatBankDto;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrivatBankICurrencyService implements ICurrencyService {

    @Override
    public List<Double> getRate(Currency currency) {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

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
                .getParameterized(List.class, PrivatBankDto.class)
                .getType();
        List<PrivatBankDto> list = new Gson().fromJson(jsonString, type);

        List<Double> result = new ArrayList<>();

        list.stream()
                .filter(c -> c.getCcy() == currency)
                .filter(c -> c.getBase_ccy() == Currency.UAH)
                .forEach(dto -> {
                    result.add(Double.valueOf(dto.getBuy()));
                    result.add(Double.valueOf(dto.getSale()));
                });

        return result;
    }
}
