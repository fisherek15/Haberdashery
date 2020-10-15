package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum AkcesoriaDoFiranRodzaj {
    MOCOWANIA_DO_FIRAN ("MOCOWANIA DO FIRAN"),
    TASMY_DO_ZASLON_I_FIRAN ("TAŚMY DO ZASŁON I FIRAN");


    private String displayName;

    AkcesoriaDoFiranRodzaj(String displayName) {
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

//"mocowania do firan", "taśmy do zasłon i firan"
