package org.example.telegram.commands;

import org.example.Settings;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class BankButtonMethods {

    public SendMessage sendMassageButton (Long chatId){
        String text = "Оберіть необхідний банк";

        SendMessage sm = new SendMessage();
        sm.setText(text);
        // треба спробувати використати статичний сетінгс
        sm.setChatId(chatId);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        //  ці кнопки підтримують емоджі.
        InlineKeyboardButton privatBank = new InlineKeyboardButton();
        privatBank.setText("Приват банк ✔\uFE0F");
        privatBank.setCallbackData("PRIVAT");



        InlineKeyboardButton monoBank = new InlineKeyboardButton();
        monoBank.setText("Монобанк");
        monoBank.setCallbackData("MONO");

        InlineKeyboardButton nationalBank = new InlineKeyboardButton();
        nationalBank.setText("НБУ");
        nationalBank.setCallbackData("NATIONAL");

        rowInline.add(privatBank);
        rowInline.add(monoBank );
        rowInline.add(nationalBank);

        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);

        return sm;

    }

}
