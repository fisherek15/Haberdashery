package com.pasmanteria.Enum;

/**
 * Created by Adrian on 18/11/2017.
 */
public enum NiciRodzaj_II {
    GUMOWE ("GUMOWE"),
    LNIANE ("LNIANE"),
    METALIZOWANE ("METALIZOWANE"),
    NYLONOWE ("NYLONOWE"),
    ZYLKOWE ("ŻYŁKOWE");

    private String displayName;

    NiciRodzaj_II(String displayName) {
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