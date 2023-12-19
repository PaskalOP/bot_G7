package org.example.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ChooseCurrency {

    public SendMessage sendCurrencyButtons(Long chatId){

        SendMessage sm = new SendMessage();
        sm.setText("Оберіть валюти");
        sm.setChatId(chatId);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton buttonUSD  = new InlineKeyboardButton();
        buttonUSD.setText("USD");
        buttonUSD.setCallbackData("USD");

        InlineKeyboardButton buttonEUR = new InlineKeyboardButton();
        buttonEUR.setText("EUR");
        buttonEUR.setCallbackData("EUR");

        rowInline.add(buttonUSD);
        rowInline.add(buttonEUR);

        rowsInline.add(rowInline);

        keyboardMarkup.setKeyboard(rowsInline);
        sm.setReplyMarkup(keyboardMarkup);

        return sm;
    }
}
