package com.pasmanteria.Enum;

/**
 * Created by Adrian on 25/11/2017.
 */
public enum ZamkiTworzywo {
    METAL ("METAL"),
    PLASTIK ("PLASTIK");

    private String displayName;

    ZamkiTworzywo(String displayName) {
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
