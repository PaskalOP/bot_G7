package org.example.telegram.commands;

import org.example.Buttons;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StartCommand extends BotCommand {
    public StartCommand() {
        super("start", "start command will initiate currency bot");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String text = "Щоб отримати курс валют - натисніть кнопку 'КУРС ВАЛЮТ'. Щоб змінити налаштування - натисніть кнопку НАЛАШТУВАННЯ";

        SendMessage sm = new SendMessage();
        sm.setText(text);
        sm.setChatId(chat.getId());


        List<KeyboardButton> buttonsList = new ArrayList<>();
        buttonsList.add(new KeyboardButton("КУРСИ_ВАЛЮT"));
        buttonsList.add(new KeyboardButton("НАЛАШТУВАННЯ"));


        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(new KeyboardRow(buttonsList));

        ReplyKeyboardMarkup menu = new ReplyKeyboardMarkup();
        menu.setKeyboard(rows);

        sm.setReplyMarkup(menu);


//        KeyboardButton rateButton = InlineKeyboardButton
//                .builder()
//                .text("КУРСИ_ВАЛЮT")
//                .callbackData("КУРСИ_ВАЛЮT")
//                .build();
//        InlineKeyboardButton settingsButton = InlineKeyboardButton
//                .builder()
//                .text("НАЛАШТУВАННЯ")
//                .callbackData("НАЛАШТУВАННЯ")
//                .build();
//
//        InlineKeyboardMarkup ikm = InlineKeyboardMarkup.builder()
//                .keyboard(Collections.singletonList(Arrays.asList(rateButton, settingsButton)))
//                .build();
//
//        sm.setReplyMarkup(ikm);

        try {
            absSender.execute(sm);
        } catch (TelegramApiException e) {
            System.out.println("Error");
        }
    }
}
