package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum ZamkiRodzaj_IIIc {
    ROZDZIELCZY ("ROZDZIELCZY"),
    NIEROZDZIELCZY ("NIEROZDZIELCZY"),
    Rx2 ("Rx2");

    private String displayName;

    ZamkiRodzaj_IIIc(String displayName) {
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
