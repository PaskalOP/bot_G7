package org.example.telegram;

import org.example.telegram.commands.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

public class BotTelegram extends TelegramLongPollingCommandBot {

    public BotTelegram(){
        register(new StartCommand());
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
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            String receivedText = update.getMessage().getText();

            SendMessage sm = new SendMessage();
            sm.setText("You just wrote " + receivedText);
            sm.setChatId(update.getMessage().getChatId());

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                System.out.println("something went wrong");
            }
        }
    }
    @Override
    public void onUpdatesReceived(List<Update> updates) {

        System.out.println("Some updates recived");
    }
}
