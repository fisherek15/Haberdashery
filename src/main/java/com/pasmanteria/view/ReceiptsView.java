package com.pasmanteria.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

/**
 * Created by adrian on 27.07.2017.
 */

//to bedzie widok z paragonami zrealizownaymi dla klientow z wyszczegolnieniem kupionych artykolow
// i mozliwoscia wydrukowania faktury dla klienta

@SpringUI(path = "/receipts")
@Theme("valo")
public class ReceiptsView extends UI {
    @Override
    protected void init(VaadinRequest request) {

    }
}
