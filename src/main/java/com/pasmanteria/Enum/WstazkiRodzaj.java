package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum WstazkiRodzaj {
    OZDOBNE ("OZDOBNE"),
    TASIEMKA_SATYNOWA ("TASIEMKA SATYNOWA"),
    TASIEMKA_ZLOTA_I_SREBRNA ("TASIEMKA ZLOTA I SREBRNA"),
    WSTAZKA_AKSAMITKA ("WSTAZKA AKSAMITKA"),
    WSTAZKA_SZYFONOWA ("WSTĄŻKA SZYFONOWA");


    private String displayName;

    WstazkiRodzaj(String displayName) {
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

