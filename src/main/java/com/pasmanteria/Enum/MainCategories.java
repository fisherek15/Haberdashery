package com.pasmanteria.Enum;

/**
 * Created by Adrian on 16/11/2017.
 */
public enum MainCategories {
    AKCESORIA_DO_BIELIZNY("AKCESORIA DO BIELIZNY"),
    AKCESORIA_DO_DEKORACJI("AKCESORIA DO DEKORACJI"),
    AKCESORIA_DO_FIRAN("AKCESORIA DO FIRAN"),
    AKCESORIA_KRAWIECKIE("AKCESORIA KRAWIECKIE"),
    AKCESORIA_OBUWNICZE("AKCESORIA OBUWNICZE"),
    DODATKI_PLASTIKOWE("DODATKI PLASTIKOWE"),
    GALANTERIA("GALANTERIA"),
    GUMY("GUMY"),
    GUZIKI("GUZIKI"),
    KORDONKI_I_MULINA("KORDONKI I MULINA"),
    KORONKI("KORONKI"),
    LATY_I_LATKI("ŁATY I ŁATKI"),
    NICI("NICI"),
    POZOSTALE("POZOSTAŁE"),
    RECZNIKI("RĘCZNIKI"),
    ROBOTKI_DZIEWIARSKIE("ROBÓTKI DZIEWIARSKIE"),
    SZNURKI("SZNURKI"),
    TASMY("TAŚMY"),
    WLOCZKA("WŁÓCZKA"),
    WSTAZKI("WSTĄŻKI"),
    ZAMKI_I_AKCESORIA("ZAMKI I AKCESORIA");

    private String displayName;

    MainCategories(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public String getCaption(){
        return name();
    }
    public int getId(){
        return ordinal();
    }

}
