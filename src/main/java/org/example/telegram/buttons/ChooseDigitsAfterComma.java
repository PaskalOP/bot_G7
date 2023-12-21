package org.example.telegram.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ChooseDigitsAfterComma {

    public SendMessage sendDigitsAfterCommaButton(Long chatId){
        SendMessage sm = new SendMessage();
        sm.setText("Кількість знаків після коми");
        sm.setChatId(chatId);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton twoButton  = new InlineKeyboardButton();
        twoButton.setText("2");
        twoButton.setCallbackData("2");

        InlineKeyboardButton threeButton = new InlineKeyboardButton();
        threeButton.setText("3");
        threeButton.setCallbackData("3");

        InlineKeyboardButton fourButton = new InlineKeyboardButton();
        fourButton.setText("4");
        fourButton.setCallbackData("4");

        rowInline.add(twoButton);
        rowInline.add(threeButton);
        rowInline.add(fourButton);

        rowsInline.add(rowInline);

        keyboardMarkup.setKeyboard(rowsInline);
        sm.setReplyMarkup(keyboardMarkup);

        return sm;
    }
}
