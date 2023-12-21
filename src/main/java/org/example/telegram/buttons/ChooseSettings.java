package org.example.telegram.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ChooseSettings {

    public SendMessage executeSettings(Chat chat) {
        String text = "налаштуйте необхідні дані";

        SendMessage sm = new SendMessage();
        sm.setText(text);
        sm.setChatId(chat.getId());

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        //  ці кнопки підтримують емоджі.
        InlineKeyboardButton banksButton = new InlineKeyboardButton();
        banksButton.setText("Банки");
        banksButton.setCallbackData("BANKS");


        InlineKeyboardButton currencyButton = new InlineKeyboardButton();
        currencyButton.setText("Валюти");
        currencyButton.setCallbackData("CURRENCIES");

        rowInline1.add(banksButton);
        rowInline1.add(currencyButton);

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();

        InlineKeyboardButton notificButton = new InlineKeyboardButton();
        notificButton.setText("Час сповіщень");
        notificButton.setCallbackData("ALARM_TIME");

        InlineKeyboardButton countDigitsAfterComma = new InlineKeyboardButton();
        countDigitsAfterComma.setText("Кількість знаків після коми");
        countDigitsAfterComma.setCallbackData("DIGITS_COMMA");

        rowInline2.add(notificButton);
        rowInline2.add(countDigitsAfterComma);

        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);

        return sm;

    }
}
