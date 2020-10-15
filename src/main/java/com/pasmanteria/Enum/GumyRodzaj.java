package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum GumyRodzaj {
    OKRAGLA ("OKRĄGŁA"),
    PLASKA ("PŁASKA"),
    Z_FALBANKA ("Z FALBANKĄ");


    private String displayName;

    GumyRodzaj(String displayName) {
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
