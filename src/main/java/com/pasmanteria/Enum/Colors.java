package com.pasmanteria.Enum;

/**
 * Created by Adrian on 18/11/2017.
 */
public enum Colors {
    BIALY ("BIAŁY"),
    BRAZOWY ("BRĄZOWY"),
    CZARNY ("CZARNY"),
    CZERWONY ("CZERWONY"),
    FIOLETOWY ("FIOLETOWY"),
    NIEBIESKI ("NIEBIESKI"),
    ROZOWY ("RÓŻOWY"),
    SZARY ("SZARY"),
    SREBRNY ("SREBRNY"),
    ZIELONY ("ZIELONY"),
    ZLOTY ("ZŁOTY"),
    ZOLTY ("ŻÓŁTY");

    private String displayName;

    Colors(String displayName) {
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
