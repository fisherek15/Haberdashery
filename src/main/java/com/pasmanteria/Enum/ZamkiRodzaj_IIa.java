package com.pasmanteria.Enum;

/**
 * Created by Adrian on 19/11/2017.
 */
public enum ZamkiRodzaj_IIa {
    KOSTKOWY ("KOSTKOWY"),
    KRYTY ("KRYTY"),
    METALOWY ("METALOWY"),
    POSCIELOWY ("POŚCIELOWY"),
    ZYLKOWY ("ZYŁKOWY");

    private String displayName;

    ZamkiRodzaj_IIa(String displayName) {
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