package org.example.telegram;


import org.example.Buttons;
import org.example.UserSettings;
import org.example.telegram.commands.ChooseBank;
import org.example.telegram.commands.ChooseSettings;
import org.example.telegram.commands.RateDataPrint;
import org.example.telegram.commands.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class BotTelegram extends TelegramLongPollingCommandBot {
    //Налаштування створюються при запуску команди старт. І передаються в поле
    // для використання з різних точок класу
    private UserSettings settings;

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
                    ChooseBank banks = new ChooseBank();
                    Long chatId = update.getCallbackQuery().getMessage().getChatId();
                    SendMessage sm = banks.sendMassageButton (chatId);


                    try {
                        execute(sm);
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }
                }

                if (data.equals("MONO")){
                    settings.setBank(Buttons.МОНОБАНК);
                }

            }
            if (update.hasMessage()){
                if(update.getMessage().getText().equals("/start")){
                   StartCommand start = new StartCommand();
                    settings = new UserSettings(update.getMessage().getChat());
                    SendMessage sm = start.executeStart(update.getMessage().getChat());
                    try {
                        execute(sm);
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }
                    System.out.println(settings.getBank());
                    System.out.println(settings.getCurrencies());

                }
                if (update.getMessage().getText().equals("НАЛАШТУВАННЯ")){
                    ChooseSettings settings = new ChooseSettings();
                    SendMessage sm = settings.executeSettings(update.getMessage().getChat());
                    try {
                        execute(sm);
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }
                }
                if (update.getMessage().getText().equals("КУРСИ ВАЛЮT")){
                    RateDataPrint message = new RateDataPrint();
                    SendMessage sm = message.printRate(settings,update.getMessage().getChat());

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
