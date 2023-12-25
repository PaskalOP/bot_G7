package org.example.telegram.calculateForRate;

import org.example.telegram.settings.UserSettings;
import org.example.currency.model.Currency;
import org.example.currency.model.UserRequest;
import org.example.currency.service.ICurrencyService;
import org.example.currency.service.MonoBankICurrencyService;
import org.example.currency.service.NationalBankICurrencyService;
import org.example.currency.service.PrivatBankICurrencyService;
import org.example.telegram.buttons.Buttons;

public class CalculateForRate {
    private SettingsForCalculate dataFromUser;
    ICurrencyService currencyService;
    private UserRequest userRequest;
    private int summBase;
    public CalculateForRate(SettingsForCalculate dataFromUser, UserSettings settings){
        this.dataFromUser=dataFromUser;
        summBase = Integer.parseInt(dataFromUser.getHaveSum());
        userRequest = new UserRequest(settings);

        switch (userRequest.getBankName()) {
            case MONO -> currencyService = new MonoBankICurrencyService();
            case NATIONAL -> currencyService = new NationalBankICurrencyService();
            case PRIVAT -> currencyService = new PrivatBankICurrencyService();
        }
    }
    public String calculate(){
        String result = "";
        double summResult = 0;
        if(dataFromUser.getWantSellCurrensy()==Buttons.UAN){
            summResult = summBase/currencyService
                    .getRate(dataFromUser.getWantBuyCurrency()==Buttons.EUR?Currency.EUR:Currency.USD).get(1);

        }
        if (dataFromUser.getWantBuyCurrency()==Buttons.UAN){
            summResult = summBase*currencyService
                    .getRate(dataFromUser.getWantSellCurrensy()==Buttons.EUR?Currency.EUR:Currency.USD).get(0);
        }
        result = "Ви отримаєте " + String.format("%.2f",summResult) + " "+ dataFromUser.getWantBuyCurrency();

        return result;
    }


}
