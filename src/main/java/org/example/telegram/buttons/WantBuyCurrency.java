package org.example.telegram.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class WantBuyCurrency {
    public SendMessage sendMessAboveBuy(Long catId){

        SendMessage sm = new SendMessage();
        sm.setText("Яку валюту ви хочете придбати?");
        sm.setChatId(catId);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton USDforBuy = new InlineKeyboardButton();
        USDforBuy.setText("USD");
        USDforBuy.setCallbackData("usdForBuy");

        InlineKeyboardButton EURforBuy = new InlineKeyboardButton();
        EURforBuy.setText("EUR");
        EURforBuy.setCallbackData("eurForBuy");

        InlineKeyboardButton UANforBuy = new InlineKeyboardButton();
        UANforBuy .setText("UAN");
        UANforBuy .setCallbackData("uanForBuy");

        rowInline.add(USDforBuy);
        rowInline.add(EURforBuy);
        rowInline.add(UANforBuy );

        rowsInline.add(rowInline);

        keyboardMarkup.setKeyboard(rowsInline);
        sm.setReplyMarkup(keyboardMarkup);

        return sm;
    }
}
