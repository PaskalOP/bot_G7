package org.example.currency.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.currency.model.Currency;
import org.example.currency.model.NationalBankDto;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NationalBankICurrencyService implements ICurrencyService {

    @Override
    public List<Double> getRate(Currency currency) {
        String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

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
                .getParameterized(List.class, NationalBankDto.class)
                .getType();
        List<NationalBankDto> list = new Gson().fromJson(jsonString, type);

        List<Double> result = new ArrayList<>();

        list.stream()
                .filter(c -> c.getR030() == currency.getCode())
                .forEach(dto -> {
                    result.add(dto.getRate());
                });

        return result;
    }
}
