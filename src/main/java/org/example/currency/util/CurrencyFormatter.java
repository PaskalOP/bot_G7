package org.example.currency.util;

import org.example.currency.model.Currency;

import java.util.List;

public class CurrencyFormatter {
    public static final String FORMAT = "%s\n Купівля - %s\n Продаж - %s";
    public static final String FORMAT_NBU = "Офіційний курс %s - %s";

    public static String getFormatted(Currency currency, List<Double> rates, int numbersAfterPoint) {

        String formatNumbers = "%." + numbersAfterPoint + "f";
        if (rates.size() == 2) {
            return String.format(FORMAT, currency,
                    String.format(formatNumbers, rates.get(0)),
                    String.format(formatNumbers, rates.get(1)));
        } else {
            return String.format(FORMAT_NBU, currency,
                    String.format(formatNumbers, rates.get(0)));
        }
    }
}
