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

        InlineKeyboardButton bankButton = InlineKeyboardButton
                .builder()
                .text("БАНКИ")
                .callbackData("BANKS")
                .build();
        InlineKeyboardButton currencyButton = InlineKeyboardButton
                .builder()
                .text("Валюти")
                .callbackData("/currency")
                .build();
        InlineKeyboardButton notificationButton = InlineKeyboardButton
                .builder()
                .text("Час сповіщень")
                .callbackData("/timeNotification")
                .build();

        InlineKeyboardMarkup ikm = InlineKeyboardMarkup.builder()
                .keyboard(Collections.singletonList(Arrays.asList(currencyButton , notificationButton,bankButton )))
                .build();

        sm.setReplyMarkup(ikm);
        return sm;

    }
}
