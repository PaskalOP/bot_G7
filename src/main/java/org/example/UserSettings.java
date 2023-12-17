package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public  class UserSettings {
    String usedId;
    String alertTime = "09:00";
    Buttons bank = Buttons.ПРИВАТБАНК;
    /*
     * Requirements are that:
     * We need to set one default Currency. Users can change settings and choose another Currency or several.
     * That's why when you set new settings, you need to check List, set new value and delete old value
     * */
    List<Buttons> currencies = Arrays.asList(Buttons.USD); //You can add Buttons.EUR - for two values
    int numbersAfterPoint = 2;
}
