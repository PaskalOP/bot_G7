package org.example.telegram.commands;

import org.example.UserSettings;
import org.example.currency.CurrencyService;
import org.example.currency.model.UserRequest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

public class RateDataPrint {
    public SendMessage printRate(UserSettings settings, Chat chat)
    {

        UserRequest userRequest = new UserRequest(settings);
        CurrencyService currencyService = new CurrencyService();
        String getRate = currencyService.getRate(userRequest);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(getRate );
        sendMessage.setChatId(chat.getId());

        return sendMessage;
    }


}
