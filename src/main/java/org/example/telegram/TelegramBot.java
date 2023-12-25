package org.example.telegram;


import lombok.SneakyThrows;
import org.example.telegram.calculateForRate.CalculateForRate;
import org.example.telegram.calculateForRate.SettingsForCalculate;
import org.example.UserSettings;
import org.example.telegram.buttons.*;

import org.example.telegram.calculateForRate.WantBuyCurrency;
import org.example.telegram.calculateForRate.WantSellCurrency;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MessageId;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TelegramBot extends TelegramLongPollingBot {
    //Налаштування створюються при запуску команди старт. І передаються в поле
    // для використання з різних точок класу    private UserSettings settings;
    private UserSettings settings;
    private SettingsForCalculate calculateData;
    private WantSellCurrency forSell;
    private WantBuyCurrency forBuy;
     private SendMessage sendMess;
     private Long chatId;
     private Integer messageId;
     private CalculateForRate calculator;

     private EditMessageText editMessageText;

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
     @SneakyThrows
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
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            switch(data){
                case "BANKS":
                    ChooseBank banks = new ChooseBank(settings);
                    try {
                        execute(banks.sendButtonMessage(chatId));
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }break;

                case "CURRENCIES":
                    //ChooseCurrency currencyButtons = new ChooseCurrency(settings);
                    ChooseCurrencyTest chooseCurrencyTest = new ChooseCurrencyTest(settings);
                    try {
                        //execute(currencyButtons.sendCurrencyButtons(chatId));
                        execute(chooseCurrencyTest.sendButtonMessage(chatId));

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
                    ChooseDigitsAfterComma chooseDigitsAfterComma = new ChooseDigitsAfterComma(settings);
                    try {
                        execute(chooseDigitsAfterComma.sendButtonMessage(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }break;

                case "МОНОБАНК":
                    settings.setBank(Buttons.МОНОБАНК);
                    //SendMessage sm = new SendMessage();
                    sendMess.setChatId(chatId);
                    sendMess.setText("Му-ха-ха... Тепер ти на темій стороні котиків!");
                    ChooseBank chooseBank2 = new ChooseBank(settings);
                    try {
                        execute(chooseBank2.editButtonMessage(settings,chatId,messageId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "ПРИВАТБАНК":
                    settings.setBank(Buttons.ПРИВАТБАНК);
                    ChooseBank chooseBank1 = new ChooseBank(settings);
                    execute(chooseBank1.editButtonMessage(settings,chatId,messageId));
                    break;
                case "НБУ":
                    settings.setBank(Buttons.НБУ);
                    ChooseBank chooseBank3 = new ChooseBank(settings);
                    execute(chooseBank3.editButtonMessage(settings,chatId,messageId));
                    break;
                case "EUR":
                    if(settings.getCurrencies().contains(Buttons.EUR)){
                        settings.getCurrencies().remove(Buttons.EUR);
                        //ChooseCurrency chooseCurrency = new ChooseCurrency(settings);
                        //execute(chooseCurrency.editCurrencyMessage(settings, chatId, messageId));
                        ChooseCurrencyTest chooseCurrencyTest1 = new ChooseCurrencyTest(settings);
                        execute(chooseCurrencyTest1.editButtonMessage(settings, chatId, messageId));
                    }
                    else {
                        settings.getCurrencies().add(Buttons.EUR);
                        //ChooseCurrency chooseCurrency = new ChooseCurrency(settings);
                        //execute(chooseCurrency.editCurrencyMessage(settings, chatId, messageId));
                        ChooseCurrencyTest chooseCurrencyTest1 = new ChooseCurrencyTest(settings);
                        execute(chooseCurrencyTest1.editButtonMessage(settings, chatId, messageId));
                    }

                    break;
                case "USD":
                    if(settings.getCurrencies().contains(Buttons.USD)){
                        settings.getCurrencies().remove(Buttons.USD);
                        //ChooseCurrency chooseCurrency = new ChooseCurrency(settings);
                        //execute(chooseCurrency.editCurrencyMessage(settings, chatId, messageId));
                        ChooseCurrencyTest chooseCurrencyTest1 = new ChooseCurrencyTest(settings);
                        execute(chooseCurrencyTest1.editButtonMessage(settings, chatId, messageId));
                    }
                    else {
                        settings.getCurrencies().add(Buttons.USD);
                        //ChooseCurrency chooseCurrency = new ChooseCurrency(settings);
                        //execute(chooseCurrency.editCurrencyMessage(settings, chatId, messageId));
                        ChooseCurrencyTest chooseCurrencyTest1 = new ChooseCurrencyTest(settings);
                        execute(chooseCurrencyTest1.editButtonMessage(settings, chatId, messageId));

                    }
                    break;
                case "2":
                    settings.setNumbersAfterPoint(2);
                    ChooseDigitsAfterComma chooseDigitsAfterComma1 = new ChooseDigitsAfterComma(settings);
                    execute(chooseDigitsAfterComma1.editButtonMessage(settings, chatId, messageId));
                    break;
                case "3":
                    settings.setNumbersAfterPoint(3);
                    ChooseDigitsAfterComma chooseDigitsAfterComma2 = new ChooseDigitsAfterComma(settings);
                    execute(chooseDigitsAfterComma2.editButtonMessage(settings, chatId, messageId));
                    break;
                case "4":
                    settings.setNumbersAfterPoint(4);
                    ChooseDigitsAfterComma chooseDigitsAfterComma3 = new ChooseDigitsAfterComma(settings);
                    execute(chooseDigitsAfterComma3.editButtonMessage(settings, chatId, messageId));
                    break;
                case "9":
                    settings.setAlertTime("9");
                    scheduledNotification(settings);
                    break;
                case "10":
                    settings.setAlertTime("10");
                    scheduledNotification(settings);
                    break;
                case "11":
                    settings.setAlertTime("11");
                    scheduledNotification(settings);
                    break;
                case "12":
                    settings.setAlertTime("12");
                    scheduledNotification(settings);
                    break;
                case "13":
                    settings.setAlertTime("13");
                    scheduledNotification(settings);
                    break;
                case "14":
                    settings.setAlertTime("14");
                    scheduledNotification(settings);
                    break;
                case "15":
                    settings.setAlertTime("15");
                    scheduledNotification(settings);
                    break;
                case "16":
                    settings.setAlertTime("16");
                    scheduledNotification(settings);
                    break;
                case "17":
                    settings.setAlertTime("17");
                    scheduledNotification(settings);
                    break;
                case "18":
                    settings.setAlertTime("18");
                    scheduledNotification(settings);
                    break;
                case "19":
                    settings.setAlertTime("19");
                    scheduledNotification(settings);
                    break;
                case "20":
                    settings.setAlertTime("20");
                    scheduledNotification(settings);
                    break;
                case "DISABLE_ALERT":
                    settings.setAlertTime("DISABLE_ALERT");
                    stopScheduledNotification();
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
    private static ScheduledExecutorService scheduler;
    public void scheduledNotification(UserSettings settings) {

        scheduler = Executors.newScheduledThreadPool(1);
        // Визначте час, коли потрібно відправити повідомлення
        Calendar scheduledTime = Calendar.getInstance();
        scheduledTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(settings.getAlertTime())); // Година (24-годинний формат)
        scheduledTime.set(Calendar.MINUTE, 0);       // Хвилина
        scheduledTime.set(Calendar.SECOND, 0);       // Секунда

        // Отримайте різницю в часі між поточним часом і визначеним часом
        long initialDelay = scheduledTime.getTimeInMillis() - System.currentTimeMillis();

        // Встановіть завдання для відправлення повідомлення
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("123");

            RateDataPrint message = new RateDataPrint();
            try {
                execute(message.printRate(settings, chatId));
            } catch (TelegramApiException e) {
                System.out.println("Error");
            }

        }, initialDelay, TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS);
    }
    public static void stopScheduledNotification() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }


    }
}
