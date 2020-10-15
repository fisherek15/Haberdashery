package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum AkcesoriaObuwniczeRodzaj {
    SZNUROWKI ("SZNURÓWKI"),
    POZOSTALE ("POZOSTAŁE");


    private String displayName;

    AkcesoriaObuwniczeRodzaj(String displayName) {
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
