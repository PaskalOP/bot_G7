package org.example;

import lombok.Data;
import org.example.telegram.buttons.Buttons;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public  class UserSettings {
    Long chatId;
    String alertTime = "09:00";
    Buttons bank = Buttons.ПРИВАТБАНК;
    /*
     * Requirements are that:
     * We need to set one default Currency. Users can change settings and choose another Currency or several.
     * That's why when you set new settings, you need to check List, set new value and delete old value
     * */
    List<Buttons> currencies = Arrays.asList(Buttons.USD); //You can add Buttons.EUR - for two values
    int numbersAfterPoint = 2;
    public UserSettings(Chat chat){
        chatId = chat.getId();
    }
}
