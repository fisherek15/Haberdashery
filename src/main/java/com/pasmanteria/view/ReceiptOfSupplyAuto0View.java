package com.pasmanteria.view;

import com.pasmanteria.model.StaticVariables;
import com.pasmanteria.repository.StaticVariablesRep;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by Mike on 29/06/2017.
 */
@Theme("valo")
@SpringUI(path = "/receiptOfSupplyAuto")
public class ReceiptOfSupplyAuto0View extends UI {



    // we can use either constructor autowiring or field autowiring
    private SpringViewProvider viewProvider;
    private StaticVariablesRep staticVariablesRep;
    private InformationSubWin informationSubWin;

    @Autowired
    public ReceiptOfSupplyAuto0View(SpringViewProvider viewProvider,
                                    StaticVariablesRep staticVariablesRep) {
        this.viewProvider = viewProvider;
        this.staticVariablesRep = staticVariablesRep;

    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        this.setContent(root);

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

        int optionAuto = staticVariablesRep.findByName("saveofrosa").getValue();
        System.out.println("saveofrosa :" + optionAuto);

        switch (optionAuto) {
            case 0:
                if("".equals((navigator.getState()))) {
                    getPage().setUriFragment("!" + ReceiptOfSupplyAuto1View.VIEW_NAME, false);
                }
                break;
            case 1:
                break;
            case 2:
                if("".equals((navigator.getState()))) {
                    getPage().setUriFragment("!" + ReceiptOfSupplyAuto2View.VIEW_NAME, false);
                }
                break;
            case 3:
                if("".equals((navigator.getState()))) {
                    getPage().setUriFragment("!" + ReceiptOfSupplyAuto3View.VIEW_NAME, false);
                }
                break;
            default:
                System.out.println("Coś poszło nie tak w trakcie wznowienia automatycznego dodawania nowego towaru.");
        }
    }
}
