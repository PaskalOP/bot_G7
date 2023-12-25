package org.example.telegram.calculateForRate;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class WantSellCurrency {
    public SendMessage messForSellCurrency(Long chatId){
        SendMessage sm = new SendMessage();

        sm.setText("Яку валюта у вас є?");
        sm.setChatId(chatId);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton USDforSell  = new InlineKeyboardButton();
        USDforSell.setText("USD");
        USDforSell.setCallbackData("USDForSell");

        InlineKeyboardButton EURforSell = new InlineKeyboardButton();
        EURforSell.setText("EUR");
        EURforSell.setCallbackData("EURForSell");

        InlineKeyboardButton UANforSell = new InlineKeyboardButton();
        UANforSell.setText("UAN");
        UANforSell.setCallbackData("UANForSell");

        rowInline.add(USDforSell);
        rowInline.add(EURforSell);
        rowInline.add(UANforSell);

        rowsInline.add(rowInline);

        keyboardMarkup.setKeyboard(rowsInline);
        sm.setReplyMarkup(keyboardMarkup);

        return sm;
    }

}
