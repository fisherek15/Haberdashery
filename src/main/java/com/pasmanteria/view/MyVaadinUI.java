package com.pasmanteria.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mike on 18/06/2017.
 */

//////////-------------- RACZEJ DO USUNIECIA JESLI ZASADA OTWIERANIA KART POZOSTANIE TA SAMA ----------
//niepotrzebne bo zasada dzialania menu i otwierania kart zostala zmieniona

//@Theme("valo")
//@SpringUI(path = "/fffff")
public class MyVaadinUI /*extends UI*/ {
    // we can use either constructor autowiring or field autowiring
    /*
    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        //root.setMargin(true);
        //root.setSpacing(true);
        setContent(root);

        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("Strona startowa",
                MainView.VIEW_NAME, true));
        navigationBar.addComponent(createNavigationButton("ReceiptOfSupplyManual1View",
                ReceiptOfSupplyManual1View.VIEW_NAME, true));
        navigationBar.addComponent(createNavigationButton("ReceiptOfSupplyManual2View",
                ReceiptOfSupplyManual2View.VIEW_NAME, false));
        navigationBar.addComponent(createNavigationButton("ReceiptOfSupplyAuto1View",
                ReceiptOfSupplyAuto1View.VIEW_NAME, true));
        navigationBar.addComponent(createNavigationButton("ReceiptOfSupplyAuto2View",
                ReceiptOfSupplyAuto2View.VIEW_NAME, true));
        navigationBar.addComponent(createNavigationButton("ReceiptOfSupplyAuto3View",
                ReceiptOfSupplyAuto3View.VIEW_NAME, false));
        navigationBar.addComponent(createNavigationButton("RaportsView",
                RaportsView.VIEW_NAME, true));
        navigationBar.addComponent(createNavigationButton("WarehouseView",
                WarehouseView.VIEW_NAME, true));
        navigationBar.addComponent(createNavigationButton("NewSaleView",
                NewSaleView.VIEW_NAME, true));

        root.addComponent(navigationBar);

        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
    }

    private Button createNavigationButton(String caption, final String viewName, boolean visible) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        // If you didn't choose Java 8 when creating the project, convert this to an anonymous listener class
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        button.setVisible(visible);
        return button;
    }
    */
}
