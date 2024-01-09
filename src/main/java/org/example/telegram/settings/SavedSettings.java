package org.example.telegram.settings;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SavedSettings {

    private List<UserSettings> listSettings;
    private final String fileName = "userSettings.json";
    private UserSettings currentSettings;
    private final Long chatId;
    public SavedSettings(Long chatId) throws IOException {
        this.chatId = chatId;
        if (isFileExists()) {
            listSettings = readSettingsFromJsonFile();
            // якщо вилучити ініціювання currentSettings то видає помилку при спробі присвоєння getCurrentSettings()
            currentSettings = new UserSettings(chatId);
            currentSettings = getCurrentSettings();
        }
        else {
            currentSettings = new UserSettings(chatId);
            listSettings = new ArrayList<>();
            listSettings.add(currentSettings);
        }
        writeSettingsToFile();
    }
    public UserSettings getCurrentSettings (){
        isInListSettings();
        return currentSettings;
    }
    private void isInListSettings(){
        if(!listSettings.isEmpty()){
            for (UserSettings settings: listSettings) {
                long curChatId = settings.getChatId();
                if(curChatId==chatId){
                    currentSettings.setChatId(settings.getChatId());
                    currentSettings.setBank(settings.getBank());
                    currentSettings.setAlertTime(settings.getAlertTime());
                    currentSettings.setCurrencies(settings.getCurrencies());
                    currentSettings.setNumbersAfterPoint(settings.getNumbersAfterPoint());
                    return;
                }
            }
        }
        else{
            listSettings.add(currentSettings );
        }
    }
    private boolean isFileExists() {
        File file = new File(fileName);
        return file.exists();
    }
    private List<UserSettings> readSettingsFromJsonFile() {
        try { // [{"chatId":475043906,"alertTime":"9","bank":"МОНОБАНК","currencies":["USD"],"numbersAfterPoint":3}]
            JsonReader reader = new JsonReader(new FileReader(fileName));
            Type collectionType = new TypeToken<List<UserSettings>>(){}.getType();
            return new Gson().fromJson(reader, collectionType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String convertSettingsToJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(listSettings);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void writeJsonToFile(String jsonString) {
        try {
            FileWriter file = new FileWriter(fileName);
            file.write(jsonString);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSettingsToFile() {
        String jsonString = convertSettingsToJson();
        if (!jsonString.isEmpty() && jsonString.length()>2)
            writeJsonToFile(jsonString);
    }

}
