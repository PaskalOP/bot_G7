package org.example.telegram.buttons;

import org.example.UserSettings;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ChooseCurrencyTest implements ButtonsInterf{

    private UserSettings settings;

    public ChooseCurrencyTest(UserSettings settings) {
        this.settings=settings;
    }

    @Override
    public SendMessage sendButtonMessage(Long chatId) {
        SendMessage sm = new SendMessage();
        sm.setText("Оберіть валюти");
        sm.setChatId(chatId);

        InlineKeyboardMarkup keyboardMarkup = createKeyboardMarkup();
        sm.setReplyMarkup(keyboardMarkup);
        return sm;
    }

    @Override
    public InlineKeyboardMarkup createKeyboardMarkup() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        rowInline.add(createButton(Buttons.USD));
        rowInline.add(createButton(Buttons.EUR));

        rowsInline.add(rowInline);
        keyboardMarkup.setKeyboard(rowsInline);

        return keyboardMarkup;
    }

    @Override
    public InlineKeyboardButton createButton(Buttons button) {
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        if (settings.getCurrencies().contains(button)){
            keyboardButton.setText("✅ " + button);
        }else {
            keyboardButton.setText(String.valueOf(button));
        }
       keyboardButton.setCallbackData(String.valueOf(button));
        return keyboardButton;
    }

    @Override
    public EditMessageText editButtonMessage(UserSettings settings, Long chatId, Integer messageId) {

        InlineKeyboardMarkup keyboardMarkup = createKeyboardMarkup();

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText("Оберіть валюти");
        editMessageText.setReplyMarkup(keyboardMarkup);

        return editMessageText;

    }
}
