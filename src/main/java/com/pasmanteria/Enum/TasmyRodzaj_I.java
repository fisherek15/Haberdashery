package com.pasmanteria.Enum;

/**
 * Created by Adrian on 19/11/2017.
 */
public enum TasmyRodzaj_I {
    LAMOWKI ("LAMÓWKI"),
    TASMA_BAWELNIANA ("TAŚMA BAWEŁNIANA"),
    TASMA_INNA ("TAŚMA INNA"),
    TASMA_NOSNA ("TAŚMA NOŚNA"),
    TASMA_ODBLASKOWA ("TAŚMA ODBLASKOWA"),
    TASMA_OZDOBNA ("TAŚMA OZDOBNA"),
    TASMA_RYPSOWA ("TAŚMA RYPSOWA"),
    TASMA_RZEP ("TAŚMA RZEP"),
    TASMA_SPODNIOWA ("TAŚMA SPODNIOWA"),
    TASMA_SPODNICOWA ("TAŚMA SPÓDNICOWA"),
    WYPOSTKA ("WYPÓSTKA");

    private String displayName;

    TasmyRodzaj_I(String displayName) {
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