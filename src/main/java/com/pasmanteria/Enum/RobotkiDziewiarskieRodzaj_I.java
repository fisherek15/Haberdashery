package com.pasmanteria.Enum;

/**
 * Created by Adrian on 19/11/2017.
 */
public enum RobotkiDziewiarskieRodzaj_I {
    SZYDELKA ("SZYDEŁKA"),
    DRUTY_DZIEWIARSKIE ("DRUTY DZIEWIARSKIE"),
    DRUTY_DO_SKARPET ("DRUTY DO SKARPET"),
    DRUTY_NA_ZYLCE ("DRUTY NA ŻYŁCE");

    private String displayName;

    RobotkiDziewiarskieRodzaj_I(String displayName) {
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

