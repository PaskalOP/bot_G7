package org.example.telegram;

import org.example.telegram.commands.StartCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

public class BotTelegram extends TelegramLongPollingCommandBot {
    public BotTelegram() {
        register(new StartCommand());
        register (new StartButton());
    }

    @Override
    public String getBotUsername() {
        return BotLogin.NAME;
    }

    @Override
    public String getBotToken() {
        return BotLogin.TOKEN;
    }
    @Override
    public void processNonCommandUpdate(Update update){


    }

}
