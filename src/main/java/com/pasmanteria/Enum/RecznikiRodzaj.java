package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum RecznikiRodzaj {
    BAWELNIANE ("BAWEŁNIANE"),
    BAMBUSOWE ("BAMBUSOWE"),
    SCIERKI_KUCHENNE ("ŚCIERKI KUCHENNE");

    private String displayName;

    RecznikiRodzaj(String displayName) {
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
