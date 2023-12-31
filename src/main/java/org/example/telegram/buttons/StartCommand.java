package org.example.telegram.buttons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

public class StartCommand  {

    public SendMessage executeStart (Long chatId) {

        String text = "Щоб отримати курс валют - натисніть кнопку КУРС ВАЛЮТ. \nЩоб змінити налаштування - натисніть кнопку НАЛАШТУВАННЯ. \nЩоб порахувати сумму по курсу валют - натисніть ПОРАХУВАТИ ПО КУРСУ";

        SendMessage sm = new SendMessage();
        sm.setText(text);
        sm.setChatId(chatId);


        List<KeyboardButton> buttonsList = new ArrayList<>();

        buttonsList.add(new KeyboardButton("КУРСИ ВАЛЮT"));
        buttonsList.add(new KeyboardButton("НАЛАШТУВАННЯ"));
        buttonsList.add(new KeyboardButton("ПОРАХУВАТИ ПО КУРСУ"));


        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(new KeyboardRow(buttonsList));

        ReplyKeyboardMarkup menu = new ReplyKeyboardMarkup();
        menu.setKeyboard(rows);

        sm.setReplyMarkup(menu);
        return sm;

    }
}
