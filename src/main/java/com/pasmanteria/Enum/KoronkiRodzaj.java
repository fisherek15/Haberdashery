package com.pasmanteria.Enum;

/**
 * Created by Adrian on 18/11/2017.
 */
public enum KoronkiRodzaj {
    BAWELNIANE ("BAWEŁNIANE"),
    ELASTYCZNE ("ELASTYCZNE"),
    GIPIURY ("GIPIURY"),
    STYLONOWE ("STYLONOWE");

    private String displayName;

    KoronkiRodzaj(String displayName) {
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
