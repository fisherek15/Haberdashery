package com.pasmanteria.Enum;

/**
 * Created by Adrian on 18/11/2017.
 */
public enum NiciRodzaj_I {
    BAWELNIANE ("BAWEŁNIANE"),
    POLIESTROWE ("POLIESTROWE"),
    SPECJALNE ("SPECJALNE");

    private String displayName;

    NiciRodzaj_I(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
