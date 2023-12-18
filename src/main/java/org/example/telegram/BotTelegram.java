package org.example.telegram;


import org.example.telegram.commands.BankButtonMethods;
import org.example.telegram.commands.SettingsButton;
import org.example.telegram.commands.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class BotTelegram extends TelegramLongPollingCommandBot {
    public BotTelegram() {
      //  register(new StartCommand());
       // register (new SettingsButton());

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

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update:updates) {
            if (update.hasCallbackQuery()) {
                String data = update.getCallbackQuery().getData();


                if (data.equals("БАНКИ") ){
                    BankButtonMethods banks = new BankButtonMethods();
                    Long chatId = update.getCallbackQuery().getMessage().getChatId();
                    SendMessage sm = banks.sendMassageButton (chatId);

                    try {
                        execute(sm);
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }
                    System.out.println("banks");
                }
                System.out.println("some update");
            }
            if (update.hasMessage()){
                if(update.getMessage().getText().equals("/start")){
                   StartCommand start = new StartCommand();
                    SendMessage sm = start.executeStart(update.getMessage().getChat());
                    try {
                        execute(sm);
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }
                }
                if (update.getMessage().getText().equals("НАЛАШТУВАННЯ")){
                    SettingsButton settings = new SettingsButton();
                    SendMessage sm = settings.executeSettings(update.getMessage().getChat());
                    try {
                        execute(sm);
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }
                }


            }

        }

    }
}
