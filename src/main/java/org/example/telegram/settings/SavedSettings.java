package org.example.telegram.settings;

import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.ArrayList;
import java.util.List;

public class SavedSettings {

    private List<UserSettings> userSettings;
    private UserSettings currentSettings;
    private Long chatId;
    public SavedSettings(Long chatId){
        userSettings = new ArrayList<>();
        this.chatId = chatId;
        currentSettings = new UserSettings(chatId);
    }
    public UserSettings getCurrentSettings (){
        isInListSettings();
        return currentSettings;
    }
    private void isInListSettings(){
        if(!userSettings.isEmpty()){
            for (UserSettings settings: userSettings) {
                if(settings.getChatId()==chatId){
                    currentSettings.setChatId(settings.getChatId());
                    currentSettings.setBank(settings.getBank());
                    currentSettings.setAlertTime(settings.getAlertTime());
                    currentSettings.setCurrencies(settings.getCurrencies());
                    currentSettings.setNumbersAfterPoint(settings.getNumbersAfterPoint());
                }
            }

        }
        else{
            userSettings.add(currentSettings );
        }

    }
}
