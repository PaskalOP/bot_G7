package org.example.telegram.buttons;

import org.example.telegram.settings.UserSettings;
import org.example.currency.CurrencyService;
import org.example.currency.model.UserRequest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class RateDataPrint {
    public SendMessage printRate(UserSettings settings, Long chatId)
    {

        UserRequest userRequest = new UserRequest(settings);
        CurrencyService currencyService = new CurrencyService();
        String getRate = currencyService.getRate(userRequest);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(getRate);
        sendMessage.setChatId(chatId);

        return sendMessage;
    }


}
