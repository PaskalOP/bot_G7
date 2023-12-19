package org.example.currency;

import org.example.UserSettings;
import org.example.currency.model.UserRequest;

public class CurrencyServiceDemo {
    public static void main(String[] args) {

        // Створюємо новий екземпляр класу CurrencyService
        CurrencyService currencyService = new CurrencyService();

        // Створюємо об'єкт UserSettings з того, що ввів User
        // По замовчуванню використовується дефолтний набір значень
       // UserSettings userSettings = new UserSettings();

        // Створюємо UserRequest на отриманння інформації по курсу валют
        // Використовуєм інформацію з UserSettings
        //UserRequest userFromSettings = new UserRequest(userSettings);

        // Дані отримуємо у вигляді строки
        //String rate = currencyService.getRate(userFromSettings);
        //System.out.println(rate);

    }
}
