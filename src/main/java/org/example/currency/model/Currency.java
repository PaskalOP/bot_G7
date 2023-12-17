package org.example.currency.model;

public enum Currency {
    UAH(980),
    USD(840),
    EUR(978);

    private final int code;

    Currency(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Currency valueOfCode(int code) {
        for (Currency e : values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }

}
