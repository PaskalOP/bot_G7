package org.example.telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class BotTelegram extends TelegramLongPollingCommandBot {
    @Override
    public String getBotUsername() {
        return BotLogin.NAME;
    }

    @Override
    public String getBotToken() {
        return BotLogin.TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }
    @Override
    public void onUpdatesReceived(List<Update> updates) {

        System.out.println("Some updates recived");
    }
}
