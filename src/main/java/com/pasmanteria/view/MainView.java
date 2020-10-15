package com.pasmanteria.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import sun.applet.Main;

import java.io.File;

/**
 * Created by Mike on 18/06/2017.
 */
@Theme("valo")
@SpringUI
public class MainView extends UI{
    public static final String VIEW_NAME = "";

    VerticalLayout mainLayout = new VerticalLayout();
    HorizontalLayout iconsLayout = new HorizontalLayout();
    HorizontalLayout bannerLayout = new HorizontalLayout();
    GridLayout gridLayout = new GridLayout(3,3);

    private Button butNewReceiptOfSupplyAuto;
    private Button butNewReceiptOfSupplyManual;
    private Button butStock;
    private Button butToOrder;
    private Button butStatistics;
    private Button butSale;

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    @Override
    protected void init(VaadinRequest request) {
        butNewReceiptOfSupplyAuto = new Button();
        butNewReceiptOfSupplyManual = new Button();
        butStock = new Button();
        butToOrder = new Button();
        butStatistics = new Button();
        butSale = new Button();

        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/banners/strona_domowa.png"));
        Image image = new Image("",resource);

        butNewReceiptOfSupplyAuto.setHeight("200");
        butNewReceiptOfSupplyAuto.setWidth("250");
        butNewReceiptOfSupplyManual.setHeight("200");
        butNewReceiptOfSupplyManual.setWidth("250");
        butStock.setHeight("200");
        butStock.setWidth("250");
        butToOrder.setHeight("200");
        butToOrder.setWidth("250");
        butStatistics.setHeight("200");
        butStatistics.setWidth("250");
        butSale.setHeight("200");
        butSale.setWidth("250");

        FileResource resourceNrosa = new FileResource(new File(basepath + "/WEB-INF/images/add_auto.png"));
        butNewReceiptOfSupplyAuto.setIcon(resourceNrosa,"");
        FileResource resourceNrosm = new FileResource(new File(basepath + "/WEB-INF/images/add_manual.png"));
        butNewReceiptOfSupplyManual.setIcon(resourceNrosm,"");
        FileResource resourceOrders = new FileResource(new File(basepath + "/WEB-INF/images/orders.png"));
        butToOrder.setIcon(resourceOrders,"");
        FileResource resourceStatistics = new FileResource(new File(basepath + "/WEB-INF/images/statistics.png"));
        butStatistics.setIcon(resourceStatistics,"");
        FileResource resourceSale = new FileResource(new File(basepath + "/WEB-INF/images/sale.png"));
        butSale.setIcon(resourceSale,"");
        FileResource resourceStock = new FileResource(new File(basepath + "/WEB-INF/images/stock.png"));
        butStock.setIcon(resourceStock,"");

        BrowserWindowOpener openerNewInvoiceAuto = new BrowserWindowOpener("/receiptOfSupplyAuto");
        BrowserWindowOpener openerNewInvoiceManual = new BrowserWindowOpener("/receiptOfSupplyManual");
        BrowserWindowOpener openerStock = new BrowserWindowOpener("/warehouse");
        BrowserWindowOpener openerToOrder = new BrowserWindowOpener("/articlesToOrder");
        BrowserWindowOpener openerStatistics = new BrowserWindowOpener("/raports");
        BrowserWindowOpener openerSale = new BrowserWindowOpener("/newSale");

        openerNewInvoiceAuto.extend(butNewReceiptOfSupplyAuto);
        openerNewInvoiceManual.extend(butNewReceiptOfSupplyManual);
        openerStock.extend(butStock);
        openerToOrder.extend(butToOrder);
        openerStatistics.extend(butStatistics);
        openerSale.extend(butSale);

        mainLayout.setSpacing(true);

        setContent(mainLayout);

        gridLayout.addComponent(butNewReceiptOfSupplyAuto,0,0);
        gridLayout.addComponent(butNewReceiptOfSupplyManual,1,0);
        gridLayout.addComponent(butStock,2,0);

        gridLayout.addComponent(butToOrder,0,1);
        gridLayout.addComponent(butStatistics,1,1);
        gridLayout.addComponent(butSale,2,1);

        //gridLayout.setMargin(true);
        gridLayout.setSpacing(true);
        //gridLayout.setWidth(100, Unit.PERCENTAGE);
        gridLayout.setComponentAlignment(butNewReceiptOfSupplyAuto, Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(butNewReceiptOfSupplyManual, Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(butStock, Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(butSale, Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(butStatistics, Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(butToOrder, Alignment.MIDDLE_CENTER);

        bannerLayout.addComponent(image);
        bannerLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        bannerLayout.setWidth(100, Unit.PERCENTAGE);

        iconsLayout.addComponent(gridLayout);
        iconsLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
        iconsLayout.setWidth(100, Unit.PERCENTAGE);

        mainLayout.addComponents(bannerLayout, iconsLayout);

    }
}
