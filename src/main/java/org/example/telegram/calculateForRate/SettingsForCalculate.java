package org.example.telegram.calculateForRate;

import lombok.Data;
import org.example.telegram.buttons.Buttons;

@Data
public  class SettingsForCalculate {
    private Buttons wantBuyCurrency;
    private Buttons wantSellCurrensy;
    private String haveSum;

    public boolean isDigit(String data){
        try {
            Integer.parseInt(data);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

}
