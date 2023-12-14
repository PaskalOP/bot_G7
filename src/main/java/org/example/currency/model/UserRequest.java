package org.example.currency.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.UserSettings;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(force = true)
public class UserRequest {
    private final List<Currency> currencies;
    private final BankNames bankName;
    private final int numbersAfterPoint;

    public UserRequest (UserSettings settings) {
        switch (settings.getBank()) {
            case МОНОБАНК -> this.bankName = BankNames.MONO;
            case НБУ -> this.bankName = BankNames.NATIONAL;
            default -> this.bankName = BankNames.PRIVAT;
        }
        this.numbersAfterPoint = settings.getNumbersAfterPoint();
        this.currencies = settings.getCurrencies().stream()
                .map(c -> Currency.valueOf(c.name()))
                .collect(Collectors.toList());

    }
}
