package org.example.telegram.commands;

import org.example.Buttons;
import org.example.Settings;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

public class StartCommand  {

    public SendMessage executeStart (Chat chat) {
        String text = "Щоб отримати курс валют - натисніть кнопку 'КУРС ВАЛЮТ'. Щоб змінити налаштування - натисніть кнопку НАЛАШТУВАННЯ";

        SendMessage sm = new SendMessage();
        sm.setText(text);
        sm.setChatId(chat.getId());


        List<KeyboardButton> buttonsList = new ArrayList<>();
        KeyboardButton rateCurrency = new KeyboardButton();
        rateCurrency.setText("КУРСИ_ВАЛЮT");

        buttonsList.add(new KeyboardButton("КУРСИ_ВАЛЮT"));
        buttonsList.add(new KeyboardButton("BANKS"));


        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(new KeyboardRow(buttonsList));

        ReplyKeyboardMarkup menu = new ReplyKeyboardMarkup();
        menu.setKeyboard(rows);

        sm.setReplyMarkup(menu);
        return sm;

    }
}
