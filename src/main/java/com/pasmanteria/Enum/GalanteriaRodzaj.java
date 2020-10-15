package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum GalanteriaRodzaj {
    HAFTKI ("HAFTKI"),
    ZATRZASKI ("ZATRZASKI"),
    NAPY_I_CWIEKI ("NAPY I ĆWIEKI"),
    BROSZKI ("BROSZKI"),
    STOPERY ("STOPERY"),
    KARABINCZYKI_I_ZAPIECIA ("KARABIŃCZYKI I ZAPIĘCIA"),
    OZDOBNIKI ("OZDOBNIKI");


    private String displayName;

    GalanteriaRodzaj(String displayName) {
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