package com.pasmanteria;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by adrian on 28.06.2017.
 */
public class LosowanieBezPowtorzen {

    // Lista liczb
    private List<Integer> liczby;

    // wielkość początkowa
    private Integer size;

    // inicjacja obiektu
    public void init() {
        // tworzymy zbiór liczb
        liczby = new LinkedList<Integer>();
        // dodajemy kolejne liczby do zbioru
        for (int i = 1; i < this.getSize() + 1; i++) {
            liczby.add(new Integer(i));
        }
    }

    // losowanie
    public Integer get() {
        Integer i = (int) (Math.random() * size);
        i = liczby.get(i);
        liczby.remove(i);
        this.size = liczby.size();
        return i;
    }

    // zwraca wielkość zbioru
    private Integer getSize() {
        return size;
    }

    // konstruktor jako parametr przyjmujw wielkość zbioru.
    public LosowanieBezPowtorzen(Integer size) {
        if (size <= 0)
            throw new IllegalArgumentException("Wielkość musi być większa od 0");
        this.size = size;
        init();
    }

}
