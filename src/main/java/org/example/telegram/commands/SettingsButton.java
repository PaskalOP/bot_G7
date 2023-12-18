package org.example.telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SettingsButton {

    public SendMessage executeSettings (Chat chat) {
        String text = "налаштуйте необхідні дані";

        SendMessage sm = new SendMessage();
        sm.setText(text);
        sm.setChatId(chat.getId());

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        //  ці кнопки підтримують емоджі.
        InlineKeyboardButton banksButton = new InlineKeyboardButton();
        banksButton.setText("БАНКИ");
        banksButton.setCallbackData("БАНКИ");



        InlineKeyboardButton currencyButton = new InlineKeyboardButton();
        currencyButton.setText("Валюти");
        currencyButton.setCallbackData("ВАЛЮТИ");

        InlineKeyboardButton notificButton = new InlineKeyboardButton();
        notificButton.setText("Час сповіщень");
        notificButton.setCallbackData("ЧАС_СПОВІЩЕНЬ");

        rowInline.add(banksButton );
        rowInline.add(currencyButton );
        rowInline.add(notificButton );

        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);

        return sm;

    }
}
