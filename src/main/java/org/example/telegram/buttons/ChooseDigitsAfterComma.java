package org.example.telegram.buttons;

import org.example.telegram.settings.UserSettings;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ChooseDigitsAfterComma implements ButtonsInterf{
    private UserSettings settings;

    public ChooseDigitsAfterComma(UserSettings settings) {
        this.settings = settings;
    }


    @Override
    public SendMessage sendButtonMessage(Long chatId) {
        SendMessage sm = new SendMessage();
        sm.setText("Кількість знаків після коми");
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

        rowInline.add(createButton(Buttons.TWO));
        rowInline.add(createButton(Buttons.THREE));
        rowInline.add(createButton(Buttons.FOUR));

        rowsInline.add(rowInline);
        keyboardMarkup.setKeyboard(rowsInline);

        return keyboardMarkup;
    }

    @Override
    public InlineKeyboardButton createButton(Buttons button) {

        int digitsAfterCom = 0;
        switch(button){
            case TWO -> digitsAfterCom=2;
            case THREE -> digitsAfterCom=3;
            case FOUR -> digitsAfterCom=4;
        }

        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        if (settings.getNumbersAfterPoint()==digitsAfterCom){
            keyboardButton.setText("✅ " + digitsAfterCom);
        }else {
            keyboardButton.setText(String.valueOf(digitsAfterCom));
        }
        keyboardButton.setCallbackData(String.valueOf(digitsAfterCom));

        return keyboardButton;

    }

    @Override
    public EditMessageText editButtonMessage(UserSettings settings, Long chatId, Integer messageId) {
        InlineKeyboardMarkup keyboardMarkup = createKeyboardMarkup();

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText("Кількість знаків після коми");
        editMessageText.setReplyMarkup(keyboardMarkup);

        return editMessageText;
    }
}
