package org.example.telegram.buttons;

import org.example.telegram.settings.UserSettings;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface ButtonsInterf {

    public SendMessage sendButtonMessage(Long chatId);

    public InlineKeyboardMarkup createKeyboardMarkup();

    public InlineKeyboardButton createButton(Buttons button);

    public EditMessageText editButtonMessage(UserSettings settings, Long chatId, Integer messageId);
}
