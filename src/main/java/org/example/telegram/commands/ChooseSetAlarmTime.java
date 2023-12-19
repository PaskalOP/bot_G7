package org.example.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ChooseSetAlarmTime {

    public SendMessage sendAlarmTimeOptions(Long chatId){

        // створюємо обєкт повідомлення
        SendMessage sm = new SendMessage();
        sm.setText("Виберіть час сповіщень");
        sm.setChatId(chatId);

        // створюємо обєкт вбудованої клавіатури
        InlineKeyboardMarkup markupInline  = new InlineKeyboardMarkup();

        // створюємо список списків кнопок, який згодом обєднає ряди кнопок
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        // створюємо список з кнопками першого ряду
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        //створюємо кнопки в рядку
        InlineKeyboardButton nineButton = new InlineKeyboardButton();
        nineButton.setText("9");
        nineButton.setCallbackData("9");

        InlineKeyboardButton tenButton = new InlineKeyboardButton();
        tenButton.setText("10");
        tenButton.setCallbackData("10");

        InlineKeyboardButton elevenButton = new InlineKeyboardButton();
        elevenButton.setText("11");
        elevenButton.setCallbackData("11");

        // добавляємо кнопки в перший ряд
        rowInline1.add(nineButton);
        rowInline1.add(tenButton);
        rowInline1.add(elevenButton);

        // створюємо список з кнопками другого ряду
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();

        //створюємо кнопки в рядку
        InlineKeyboardButton twelveButton = new InlineKeyboardButton();
        twelveButton.setText("12");
        twelveButton.setCallbackData("12");

        InlineKeyboardButton thirteenButton = new InlineKeyboardButton();
        thirteenButton.setText("13");
        thirteenButton.setCallbackData("13");

        InlineKeyboardButton fourteenButton = new InlineKeyboardButton();
        fourteenButton.setText("14");
        fourteenButton.setCallbackData("14");

        // добавляємо кнопки в другий ряд
        rowInline2.add(twelveButton);
        rowInline2.add(thirteenButton);
        rowInline2.add(fourteenButton);

        // створюємо список з кнопками третього ряду
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();

        //створюємо кнопки в рядку
        InlineKeyboardButton fifteenButton = new InlineKeyboardButton();
        fifteenButton.setText("15");
        fifteenButton.setCallbackData("15");

        InlineKeyboardButton sixteenButton = new InlineKeyboardButton();
        sixteenButton.setText("16");
        sixteenButton.setCallbackData("16");

        InlineKeyboardButton seventeenButton = new InlineKeyboardButton();
        seventeenButton.setText("17");
        seventeenButton.setCallbackData("17");

        InlineKeyboardButton eighteenButton = new InlineKeyboardButton();
        eighteenButton.setText("18");
        eighteenButton.setCallbackData("18");

        // добавляємо кнопки в третій ряд
        rowInline3.add(fifteenButton);
        rowInline3.add(sixteenButton);
        rowInline3.add(seventeenButton);
        rowInline3.add(eighteenButton);

        // створюємо список з кнопками четвертого ряду
        List<InlineKeyboardButton> rowInline4 = new ArrayList<>();

        //створюємо кнопки в рядку
        InlineKeyboardButton disableAlertButton = new InlineKeyboardButton();
        disableAlertButton.setText("Вимкнути повідомлення");
        disableAlertButton.setCallbackData("DISABLE_ALERT");

        // добавляємо кнопки в четвертий ряд
        rowInline4.add(disableAlertButton);

        // налаштовуємо розмітку клавіатури
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        rowsInline.add(rowInline4);

        // добаляємо щойно створену клавіатуру в повідомлення
        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);

        return sm;

    }

}
