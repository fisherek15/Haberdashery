package com.pasmanteria.Enum;

/**
 * Created by Adrian on 19/11/2017.
 */
public enum ZamkiRodzaj_Ia {
    SUWAKI_KONIKI ("SUWAKI I KONIKI"),
    TASMY_SUWAKOWE ("TAŚMY SUWAKOWE"),
    ZAMKI_BLYSKAWICZNE ("ZAMKI BŁYSKAWICZNE");

    private String displayName;

    ZamkiRodzaj_Ia(String displayName) {
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
