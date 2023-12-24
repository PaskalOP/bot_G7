package org.example.telegram.buttons;

import org.example.UserSettings;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ChooseCurrency {

    private EditMessageText editMessageText;
    private UserSettings settings;

    public ChooseCurrency(UserSettings settings) {
        this.settings=settings;
    }


    public SendMessage sendCurrencyButtons(Long chatId) {


        SendMessage sm = new SendMessage();
        sm.setText("Оберіть валюти");
        sm.setChatId(chatId);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton buttonUSD = new InlineKeyboardButton();
        if (settings.getCurrencies().contains(Buttons.USD)) {
            buttonUSD.setText("✅ USD");
        } else {
            buttonUSD.setText("USD");
        }
        buttonUSD.setCallbackData("USD");

        InlineKeyboardButton buttonEUR = new InlineKeyboardButton();
        if (settings.getCurrencies().contains(Buttons.EUR)) {
            buttonEUR.setText("✅ EUR");
        } else {
            buttonEUR.setText("EUR");
        }
        buttonEUR.setCallbackData("EUR");

        rowInline.add(buttonUSD);
        rowInline.add(buttonEUR);

        rowsInline.add(rowInline);

        keyboardMarkup.setKeyboard(rowsInline);
        sm.setReplyMarkup(keyboardMarkup);

        return sm;
    }

    public EditMessageText editCurrencyMessage(UserSettings settings, Long chatId, Integer messageId) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton buttonUSD = new InlineKeyboardButton();
        if (settings.getCurrencies().contains(Buttons.USD)) {
            buttonUSD.setText("✅ USD");
        } else {
            buttonUSD.setText("USD");
        }
        buttonUSD.setCallbackData("USD");

        InlineKeyboardButton buttonEUR = new InlineKeyboardButton();
        if (settings.getCurrencies().contains(Buttons.EUR)) {
            buttonEUR.setText("✅ EUR");
        } else {
            buttonEUR.setText("EUR");
        }
        buttonEUR.setCallbackData("EUR");

        rowInline.add(buttonUSD);
        rowInline.add(buttonEUR);

        rowsInline.add(rowInline);

        keyboardMarkup.setKeyboard(rowsInline);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText("Оберіть валюти");
        editMessageText.setReplyMarkup(keyboardMarkup);

        return editMessageText;
    }

}
