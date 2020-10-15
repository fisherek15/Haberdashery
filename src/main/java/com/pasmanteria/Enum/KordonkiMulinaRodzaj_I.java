package com.pasmanteria.Enum;

/**
 * Created by Adrian on 19/11/2017.
 */
public enum KordonkiMulinaRodzaj_I {
    KORDONKI ("KORDONKI"),
    MULINA ("MULINA");

    private String displayName;

    KordonkiMulinaRodzaj_I(String displayName) {
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
