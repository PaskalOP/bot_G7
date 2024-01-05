package org.example.telegram.settings;

import lombok.Data;
import org.example.telegram.buttons.Buttons;

import java.util.ArrayList;
import java.util.List;

@Data
public  class UserSettings {
    private Long chatId;
    private String alertTime = "9";
    private Buttons bank;
    private List<Buttons> currencies = new ArrayList<>();
    private int numbersAfterPoint = 0;
    public UserSettings(Long chatId){
        this.chatId = chatId;
        this.bank = Buttons.ПРИВАТБАНК;
        currencies.add(Buttons.USD);
        this.numbersAfterPoint = 2;
    }
    public UserSettings(Long chatId, String alertTime, Buttons bank, List<Buttons> currencies, int numbersAfterPoint) {
        this.chatId = chatId;
        this.alertTime = alertTime;
        this.bank = bank;
        this.currencies = currencies;
        this.numbersAfterPoint = numbersAfterPoint;
    }
}
