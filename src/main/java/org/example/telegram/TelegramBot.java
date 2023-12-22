package org.example.telegram;


import org.example.telegram.calculateForRate.CalculateForRate;
import org.example.telegram.calculateForRate.SettingsForCalculate;
import org.example.UserSettings;
import org.example.telegram.buttons.*;

import org.example.telegram.calculateForRate.WantBuyCurrency;
import org.example.telegram.calculateForRate.WantSellCurrency;
import org.example.telegram.sendNotification.SendNotification;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    //Налаштування створюються при запуску команди старт. І передаються в поле
    // для використання з різних точок класу    private UserSettings settings;
    private UserSettings settings;
    private SettingsForCalculate calculateData;
    private WantSellCurrency forSell;
    private WantBuyCurrency forBuy;
     private SendMessage sendMess;
     private Long chatId;
     private CalculateForRate calculator;

    public TelegramBot(){
        calculateData = new SettingsForCalculate();
        forSell = new WantSellCurrency();
        forBuy = new WantBuyCurrency();
        sendMess = new SendMessage();


    }
    @Override
    public String getBotUsername() {        return BotLogin.NAME;
    }
    @Override
    public String getBotToken() {        return BotLogin.TOKEN;
    }
//    @Override    public void processNonCommandUpdate(Update update) {
//    }
     @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            if (update.getMessage().getText().equals("/start")) {
                StartCommand start = new StartCommand();
                settings = new UserSettings(update.getMessage().getChat());
                chatId = update.getMessage().getChatId();
                try {
                    execute(start.executeStart(chatId));
                } catch (TelegramApiException e) {
                    System.out.println("Error");
                }
            }
            if (update.getMessage().getText().equals("НАЛАШТУВАННЯ")) {
                ChooseSettings settings = new ChooseSettings();
                try {
                    execute(settings.executeSettings(chatId));
                } catch (TelegramApiException e) {
                    System.out.println("Error");
                }
            }
            if (update.getMessage().getText().equals("КУРСИ ВАЛЮT")) {
                RateDataPrint message = new RateDataPrint();
                try {
                    execute(message.printRate(settings, chatId));
                } catch (TelegramApiException e) {
                    System.out.println("Error");
                }
                if  (settings.getCurrencies().isEmpty()){
                    SendMessage sendMes = new SendMessage();
                    sendMes .setChatId(update.getMessage().getChat().getId());
                    sendMes .setText("Ой, а що то таке трапилось?\uD83D\uDE31 \n Ти просто не обрав жодної валюти\uD83D\uDC46");
                    try {
                        execute(sendMes);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (update.getMessage().getText().equals("ПОРАХУВАТИ ПО КУРСУ")) {

                try {
                    execute(forBuy.sendMessAboveBuy(chatId));
                } catch (TelegramApiException e) {
                    System.out.println("Error");
                }

            }
            if(calculateData.isDigit(update.getMessage().getText())){
                calculateData.setHaveSum(update.getMessage().getText());

                calculator = new CalculateForRate(calculateData, settings);

                sendMess.setChatId(chatId);
                sendMess.setText(calculator.calculate());
                try {
                    execute(sendMess);
                } catch (TelegramApiException e) {
                    System.out.println("Error");
                }

            }
        }
        if (update.hasCallbackQuery()){
            String data = update.getCallbackQuery().getData();
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
                    //SendMessage sm = new SendMessage();
                    sendMess.setChatId(chatId);
                    sendMess.setText("Му-ха-ха... Тепер ти на темій стороні котиків!");
                    try {
                        execute(sendMess);
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
                    if(settings.getCurrencies().contains(Buttons.EUR)){
                        settings.getCurrencies().remove(Buttons.EUR);
                    }
                    else {
                        settings.getCurrencies().add(Buttons.EUR);
                    }

                    break;
                case "USD":
                    if(settings.getCurrencies().contains(Buttons.USD)){
                        settings.getCurrencies().remove(Buttons.USD);
                    }
                    else {
                        settings.getCurrencies().add(Buttons.USD);
                    }
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
                    settings.setAlertTime("9");
                    SendNotification.scheduledNotification(20);
                    break;
                case "usdForBuy":
                    calculateData.setWantBuyCurrency(Buttons.USD);
                    try {
                        execute(forSell.messForSellCurrency(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "eurForBuy":
                    calculateData.setWantBuyCurrency(Buttons.EUR);
                    try {
                        execute(forSell.messForSellCurrency(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "uanForBuy":
                    calculateData.setWantBuyCurrency(Buttons.UAN);
                    try {
                        execute(forSell.messForSellCurrency(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "USDForSell":
                    calculateData.setWantSellCurrensy(Buttons.USD);
                    sendMess.setChatId(chatId);
                    sendMess.setText("Яку суму ви хочете розміняти? >\n Напишіть ціле число");

                    try {
                        execute(sendMess);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case "EURForSell":
                    calculateData.setWantSellCurrensy(Buttons.EUR);
                    sendMess.setChatId(chatId);
                    sendMess.setText("Якy суму ви хочете розміняти?");
                    try {
                        execute(sendMess);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "UANForSell":
                    calculateData.setWantSellCurrensy(Buttons.UAN);
                    sendMess.setChatId(chatId);
                    sendMess.setText("Якy суму ви хочете розміняти?");
                    try {
                        execute(sendMess);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }


    }
}