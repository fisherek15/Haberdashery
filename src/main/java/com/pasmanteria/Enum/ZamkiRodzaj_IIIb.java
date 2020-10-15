package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum ZamkiRodzaj_IIIb {
    ROZDZIELCZY ("ROZDZIELCZY"),
    NIEROZDZIELCZY ("NIEROZDZIELCZY");

    private String displayName;

    ZamkiRodzaj_IIIb(String displayName) {
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
