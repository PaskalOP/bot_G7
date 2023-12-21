package org.example.telegram;


import org.example.Buttons;
import org.example.UserSettings;
import org.example.telegram.commands.*;
import org.example.telegram.sendNotification.SendNotification;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.invoke.SwitchPoint;
import java.util.Arrays;
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
    public void processNonCommandUpdate(Update update) {

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update : updates) {
            if (update.hasMessage()) {
                if (update.getMessage().getText().equals("/start")) {
                    StartCommand start = new StartCommand();
                    settings = new UserSettings(update.getMessage().getChat());
                    try {
                        execute(start.executeStart(update.getMessage().getChat()));
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }
                    System.out.println(settings.getBank());
                    System.out.println(settings.getCurrencies());

                }
                if (update.getMessage().getText().equals("НАЛАШТУВАННЯ")) {
                    ChooseSettings settings = new ChooseSettings();
                    try {
                        execute(settings.executeSettings(update.getMessage().getChat()));
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }
                }
                if (update.getMessage().getText().equals("КУРСИ ВАЛЮT")) {
                    RateDataPrint message = new RateDataPrint();
                    try {
                        execute(message.printRate(settings, update.getMessage().getChat()));
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }
                }
            }
            if (update.hasCallbackQuery()){
            String data = update.getCallbackQuery().getData();
            Long chatId =update.getCallbackQuery().getMessage().getChatId();
            switch(data){
                case "BANKS":
                    ChooseBank banks = new ChooseBank();
                    try {
                        execute(banks.sendMassageButton(chatId));
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }break;

                case "CURRENCIES":
                    ChooseCurrency currencyButtons = new ChooseCurrency();
                    try {
                        execute(currencyButtons.sendCurrencyButtons(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }break;

                case "ALARM_TIME":
                    ChooseSetAlarmTime setAlarmTimeButtons = new ChooseSetAlarmTime();
                    try {
                        execute(setAlarmTimeButtons.sendAlarmTimeOptions(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }break;

                case "DIGITS_COMMA":
                    ChooseDigitsAfterComma chooseDigitsAfterComma = new ChooseDigitsAfterComma();
                    try {
                        execute(chooseDigitsAfterComma.sendDigitsAfterCommaButton(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }break;

                case "MONO":
                    settings.setBank(Buttons.МОНОБАНК);
                    SendMessage sm = new SendMessage();
                    sm.setChatId(chatId);
                    sm.setText("Му-ха-ха... Тепер ти на темій стороні котиків!");
                    try {
                        execute(sm);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "PRIVAT":
                    settings.setBank(Buttons.ПРИВАТБАНК);
                    break;
                case "NATIONAL":
                    settings.setBank(Buttons.НБУ);
                    break;
                case "EUR":
                    settings.setCurrencies( Arrays.asList(Buttons.EUR));
                    break;
                case "USD":
                    settings.setCurrencies( Arrays.asList(Buttons.USD));
                    break;
                case "2":
                    settings.setNumbersAfterPoint(2);
                    break;
                case "3":
                    settings.setNumbersAfterPoint(3);
                    break;
                case "4":
                    settings.setNumbersAfterPoint(4);
                    break;
                case "9":
                    settings.setAlertTime(9);

                    SendNotification.scheduledNotification(20);
                    break;
            }
            }

        }
    }






}
