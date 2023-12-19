package org.example.telegram.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.ArrayList;
import java.util.List;

public class ChooseSettings {

    public SendMessage sendButton(Long chatid){
        // Створюємо клавіатуру з кнопками
        InlineKeyboardMarkup markup = createInlineKeyboard();

        // Створюємо повідомлення з клавіатурою
        SendMessage message = new SendMessage();
        message.setChatId(chatid);
        message.setText("Choose an option:");
        message.setReplyMarkup(markup);


        // Відправляємо повідомлення з клавіатурою
        return message;
    }

    private InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        // Створюємо кнопку
        InlineKeyboardButton button = new InlineKeyboardButton("Click me!");
        button.setCallbackData("button_click");

        // Створюємо рядок з кнопкою
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

        // Додаємо рядок до клавіатури
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        // Встановлюємо клавіатуру у розмітку
        markup.setKeyboard(keyboard);

        return markup;
    }
}
