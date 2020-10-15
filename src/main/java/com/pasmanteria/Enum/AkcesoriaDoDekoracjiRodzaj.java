package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum AkcesoriaDoDekoracjiRodzaj {
    NASZYWKI_I_APLIKACJE ("NASZYWKI I APLIKACJE"),
    KORALIKI ("KORALIKI"),
    AKCESORIA_DO_BIZUTERII ("AKCESORIA DO BIŻUTERII"),
    FREDZLE ("FRĘDZLE"),
    OZDOBNIKI ("OZDOBNIKI"),
    POZOSTALE ("POZOSTAŁE");


    private String displayName;

    AkcesoriaDoDekoracjiRodzaj(String displayName) {
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
