package org.example;

import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.Arrays;
import java.util.List;

public  class Settings {
   public static Chat chat;
    public static  String alertTime = "09:00";
    public static Buttons bank = Buttons.ПРИВАТБАНК;

    public static List<Buttons> currencies = Arrays.asList(Buttons.USD); //You can add Buttons.EUR - for two values
    public static  int numbersAfterPoint = 2;
}
