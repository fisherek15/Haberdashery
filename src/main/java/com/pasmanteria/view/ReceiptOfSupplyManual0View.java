package com.pasmanteria.view;

import com.pasmanteria.model.StaticVariables;
import com.pasmanteria.repository.StaticVariablesRep;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mike on 29/06/2017.
 */
@Theme("valo")
@SpringUI(path = "/receiptOfSupplyManual")
public class ReceiptOfSupplyManual0View extends UI {

    // we can use either constructor autowiring or field autowiring
    private SpringViewProvider viewProvider;
    private StaticVariablesRep staticVariablesRep;

    @Autowired
    public ReceiptOfSupplyManual0View(SpringViewProvider viewProvider,
                                      StaticVariablesRep staticVariablesRep) {
        this.viewProvider = viewProvider;
        this.staticVariablesRep = staticVariablesRep;
        System.out.println("konstruktor ROSM0");
    }

    @Override
    protected void init(VaadinRequest request) {
        System.out.println("init ROSM0");
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        //root.setMargin(true);
        //root.setSpacing(true);
        setContent(root);

        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);

        if(staticVariablesRep.findByName("saveofrosa") == null) {
            StaticVariables sv = new StaticVariables(null, "saveofrosa", 0, null);
            staticVariablesRep.save(sv);
        }
        if(staticVariablesRep.findByName("rosaissaved") == null) {
            StaticVariables sv2 = new StaticVariables(null, "rosaissaved", 0, null);
            staticVariablesRep.save(sv2);
        }
        if(staticVariablesRep.findByName("saveofrosm") == null) {
            StaticVariables sv1 = new StaticVariables(null, "saveofrosm", 0, null);
            staticVariablesRep.save(sv1);
        }
        if(staticVariablesRep.findByName("rosmissaved") == null) {
            StaticVariables sv3 = new StaticVariables(null, "rosmissaved", 0, null);
            staticVariablesRep.save(sv3);
        }

        int optionManual = staticVariablesRep.findByName("saveofrosm").getValue();
        System.out.println("saveofrosm :" + optionManual);

        switch (optionManual) {
            case 0:
                if("".equals((navigator.getState()))) {
                    getPage().setUriFragment("!" + ReceiptOfSupplyManual1View.VIEW_NAME, false);
                }
                break;
            case 1:
                break;
            case 2:
                if("".equals((navigator.getState()))) {
                    getPage().setUriFragment("!" + ReceiptOfSupplyManual2View.VIEW_NAME, false);
                }
                break;
            default:
                System.out.println("Coś poszło nie tak w trakcie wznowienia recznego dodawania nowego towaru.");
        }

    }
}
