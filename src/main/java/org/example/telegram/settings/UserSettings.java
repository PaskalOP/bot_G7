package org.example.telegram.settings;

import lombok.Data;
import org.example.telegram.buttons.Buttons;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public  class UserSettings {
    Long chatId;
    String alertTime = "9";
    Buttons bank = Buttons.ПРИВАТБАНК;
    List<Buttons> currencies = new ArrayList<>();
    int numbersAfterPoint = 2;
    public UserSettings(Long chatId){

        this.chatId = chatId;
        currencies.add(Buttons.USD);
    }
}
