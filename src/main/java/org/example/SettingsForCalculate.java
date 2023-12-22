package org.example;

import lombok.Data;
import org.example.telegram.buttons.Buttons;

@Data
public  class SettingsForCalculate {
    private Buttons wantBuyCurrency;
    private Buttons wantSellCurrensy;
    private int haveSum;

}
