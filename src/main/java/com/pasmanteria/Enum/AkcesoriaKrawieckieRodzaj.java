package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum AkcesoriaKrawieckieRodzaj {
    AGRAFKI ("AGRAFKI"),
    AKCESORIA_DO_SZYCIA ("AKCESORIA DO SZYCIA"),
    NOZYCZKI_I_OBCINAKI ("NOŻYCZKI I OBCINAKI"),
    MIARY_KRAWIECKIE ("MIARY KRAWIECKIE"),
    SZPILKI ("SZPILKI");



    private String displayName;

    AkcesoriaKrawieckieRodzaj(String displayName) {
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