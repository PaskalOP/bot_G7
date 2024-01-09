package org.example.telegram;


import lombok.SneakyThrows;
import org.example.telegram.calculateForRate.CalculateForRate;
import org.example.telegram.calculateForRate.SettingsForCalculate;
import org.example.telegram.settings.SavedSettings;
import org.example.telegram.settings.UserSettings;
import org.example.telegram.buttons.*;

import org.example.telegram.calculateForRate.WantBuyCurrency;
import org.example.telegram.calculateForRate.WantSellCurrency;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.example.telegram.buttons.Buttons.*;

public class TelegramBot extends TelegramLongPollingBot {
    //Налаштування створюються при запуску команди старт. І передаються в поле
    // для використання з різних точок класу    private UserSettings settings;
    private UserSettings settings;
    private SavedSettings savedSettings = null;
    private boolean isSettingsChanged = false;

    public boolean isSettingsChanged() {
        return isSettingsChanged;
    }
    public void setSettingsChanged(boolean settingsChanged) {
        isSettingsChanged = settingsChanged;
    }

    private SettingsForCalculate calculateData;
    private WantSellCurrency forSell;
    private WantBuyCurrency forBuy;
     private SendMessage sendMess;
     private Long chatId;
     private CalculateForRate calculator;
     private ChooseSetAlarmTime setAlarmTimeButtons;


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
    public static int parseToInt(String stringToParse, int defaultValue) {
        try {
            return Integer.parseInt(stringToParse);
        } catch(NumberFormatException ex) {
            return defaultValue;
        }
    }
     @SneakyThrows
     @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().getText().equals("/start")) {
                StartCommand start = new StartCommand();
                chatId = update.getMessage().getChatId();
//                UserSettings userSettings = new UserSettings(475043906L, "9", ПРИВАТБАНК, List.of(new Buttons[]{USD,EUR}), 2);
//                settings = new UserSettings(chatId);
                savedSettings = new SavedSettings(chatId);
                settings = savedSettings.getCurrentSettings();
                setSettingsChanged(false);

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
            if (calculateData.isDigit(update.getMessage().getText())){
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
            int dataInt = parseToInt(data, 0);
            if ( dataInt > 1 && dataInt < 5 ) {
                settings.setNumbersAfterPoint(dataInt);
                setSettingsChanged(true);
                ChooseDigitsAfterComma chooseDigitsAfterComma1 = new ChooseDigitsAfterComma(settings);
                execute(chooseDigitsAfterComma1.editButtonMessage(settings, chatId, messageId));
            }
            else if ( dataInt > 8 && dataInt < 19 ) {
                settings.setAlertTime(data);
                setSettingsChanged(true);
                execute(setAlarmTimeButtons.editButtonMessage(chatId,messageId));
                scheduledNotification(settings);
            }
            else
            switch(data){
                case "BANKS":
                    ChooseBank banks = new ChooseBank(settings);
                    try {
                        execute(banks.sendButtonMessage(chatId));
                    } catch (TelegramApiException e) {
                        System.out.println("Error");
                    }break;

                case "CURRENCIES":
                    ChooseCurrency chooseCurrency = new ChooseCurrency(settings);
                    try {
                        execute(chooseCurrency.sendButtonMessage(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }break;

                case "ALARM_TIME":
                    setAlarmTimeButtons = new ChooseSetAlarmTime(settings);
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
                    sendMess.setChatId(chatId);
                    sendMess.setText("Му-ха-ха... Тепер ти на темій стороні котиків!");
                    ChooseBank chooseBank2 = new ChooseBank(settings);
                    try {
                        execute(chooseBank2.editButtonMessage(settings,chatId,messageId));
                        setSettingsChanged(true);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "ПРИВАТБАНК":
                    settings.setBank(Buttons.ПРИВАТБАНК);
                    ChooseBank chooseBank1 = new ChooseBank(settings);
                    execute(chooseBank1.editButtonMessage(settings,chatId,messageId));
                    setSettingsChanged(true);
                    break;
                case "НБУ":
                    settings.setBank(Buttons.НБУ);
                    ChooseBank chooseBank3 = new ChooseBank(settings);
                    execute(chooseBank3.editButtonMessage(settings,chatId,messageId));
                    setSettingsChanged(true);
                    break;
                case "EUR":
                    if(settings.getCurrencies().contains(Buttons.EUR)){
                        settings.getCurrencies().remove(Buttons.EUR);
                        ChooseCurrency chooseCurrency1 = new ChooseCurrency(settings);
                        execute(chooseCurrency1.editButtonMessage(settings, chatId, messageId));
                    }
                    else {
                        settings.getCurrencies().add(Buttons.EUR);
                        ChooseCurrency chooseCurrency1 = new ChooseCurrency(settings);
                        execute(chooseCurrency1.editButtonMessage(settings, chatId, messageId));
                    }
                    setSettingsChanged(true);
                    break;
                case "USD":
                    if(settings.getCurrencies().contains(Buttons.USD)){
                        settings.getCurrencies().remove(Buttons.USD);
                        ChooseCurrency chooseCurrency1 = new ChooseCurrency(settings);
                        execute(chooseCurrency1.editButtonMessage(settings, chatId, messageId));
                    }
                    else {
                        settings.getCurrencies().add(Buttons.USD);
                        ChooseCurrency chooseCurrency1 = new ChooseCurrency(settings);
                        execute(chooseCurrency1.editButtonMessage(settings, chatId, messageId));
                    }
                    setSettingsChanged(true);
                    break;

                case "DISABLE_ALERT":
                    settings.setAlertTime("DISABLE_ALERT");
                    execute(setAlarmTimeButtons.editButtonMessage(chatId,messageId));
                    stopScheduledNotification();
                    setSettingsChanged(true);
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
                    sendMess.setText("Яку суму ви хочете розміняти? \n Напишіть ціле число");
                    try {
                        execute(sendMess);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "EURForSell":
                    calculateData.setWantSellCurrensy(Buttons.EUR);
                    sendMess.setChatId(chatId);
                    sendMess.setText("Якy суму ви хочете розміняти?\n Напишіть ціле число");
                    try {
                        execute(sendMess);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "UANForSell":
                    calculateData.setWantSellCurrensy(Buttons.UAN);
                    sendMess.setChatId(chatId);
                    sendMess.setText("Якy суму ви хочете розміняти?\n Напишіть ціле число");
                    try {
                        execute(sendMess);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        } // Update(updateId=346172210, message=Message(messageId=2692, messageThreadId=null, from=User(id=475043906, firstName=Веселовский, isBot=false, lastName=Владимир, userName=RoWy000, languageCode=en, canJoinGroups=null, canReadAllGroupMessages=null, supportInlineQueries=null, isPremium=null, addedToAttachmentMenu=null), date=1703857071, chat=Chat(id=475043906, type=private, title=null, firstName=Веселовский, lastName=Владимир, userName=RoWy000, photo=null, description=null, inviteLink=null, pinnedMessage=null, stickerSetName=null, canSetStickerSet=null, permissions=null, slowModeDelay=null, bio=null, linkedChatId=null, location=null, messageAutoDeleteTime=null, hasPrivateForwards=null, HasProtectedContent=null, joinToSendMessages=null, joinByRequest=null, hasRestrictedVoiceAndVideoMessages=null, isForum=null, activeUsernames=null, emojiStatusCustomEmojiId=null, hasAggressiveAntiSpamEnabled=null, hasHiddenMembers=null), forwardFrom=null, forwardFromChat=null, forwardDate=null, text=/start, entities=[MessageEntity(type=bot_command, offset=0, length=6, url=null, user=null, language=null, customEmojiId=null, text=/start)], captionEntities=null, audio=null, document=null, photo=null, sticker=null, video=null, contact=null, location=null, venue=null, animation=null, pinnedMessage=null, newChatMembers=[], leftChatMember=null, newChatTitle=null, newChatPhoto=null, deleteChatPhoto=null, groupchatCreated=null, replyToMessage=null, voice=null, caption=null, superGroupCreated=null, channelChatCreated=null, migrateToChatId=null, migrateFromChatId=null, editDate=null, game=null, forwardFromMessageId=null, invoice=null, successfulPayment=null, videoNote=null, authorSignature=null, forwardSignature=null, mediaGroupId=null, connectedWebsite=null, passportData=null, forwardSenderName=null, poll=null, replyMarkup=null, dice=null, viaBot=null, senderChat=null, proximityAlertTriggered=null, messageAutoDeleteTimerChanged=null, isAutomaticForward=null, hasProtectedContent=null, webAppData=null, videoChatStarted=null, videoChatEnded=null, videoChatParticipantsInvited=null, videoChatScheduled=null, isTopicMessage=null, forumTopicCreated=null, forumTopicClosed=null, forumTopicReopened=null, forumTopicEdited=null, generalForumTopicHidden=null, generalForumTopicUnhidden=null, writeAccessAllowed=null, hasMediaSpoiler=null, userShared=null, chatShared=null), inlineQuery=null, chosenInlineQuery=null, callbackQuery=null, editedMessage=null, channelPost=null, editedChannelPost=null, shippingQuery=null, preCheckoutQuery=null, poll=null, pollAnswer=null, myChatMember=null, chatMember=null, chatJoinRequest=null)
        if (isSettingsChanged()==true && !settings.equals(null) ) {
            if (!(savedSettings == null))
                savedSettings.writeSettingsToFile();
            setSettingsChanged(false);
        }
    }
    private static ScheduledExecutorService scheduler;
    public void scheduledNotification(UserSettings settings) {
        setSettingsChanged(true);
        scheduler = Executors.newScheduledThreadPool(1);
        // Визначте час, коли потрібно відправити повідомлення
        Calendar scheduledTime = Calendar.getInstance();
        scheduledTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(settings.getAlertTime())); // Година (24-годинний формат)
        scheduledTime.set(Calendar.MINUTE, 0);       // Хвилина
        scheduledTime.set(Calendar.SECOND, 0);       // Секунда

        // Отримайте різницю в часі між поточним часом і визначеним часом
        long initialDelay = scheduledTime.getTimeInMillis() - System.currentTimeMillis();
        if (initialDelay < 0) {
            // Якщо він від'ємний, додайте 24 години, щоб запланувати його на наступний день
            initialDelay += TimeUnit.DAYS.toMillis(1);
        }

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
