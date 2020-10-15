package com.pasmanteria.view;

import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.QueryMapper;
import com.pasmanteria.model.Articles;
import com.pasmanteria.model.ArticlesToOrder;
import com.pasmanteria.model.OrdersArticles;
import com.pasmanteria.repository.ArticlesRep;
import com.pasmanteria.repository.ArticlesToOrderRep;
import com.pasmanteria.repository.OrdersArticlesRep;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by adrian on 03.01.2017.
 */

@SpringUI(path = "/articlesToOrder")
@Theme("valo")
public class ArticlesToOrderView extends UI {

    private final ArticlesToOrderRep articlesToOrderRep;
    private OrdersArticlesRep ordersArticlesRep;
    private ArticlesRep articlesRep;
    private Grid gridTable;
    private Grid.MultiSelectionModel selection;
    private IndexedContainer container;
    private Button butDeleteSelected;
    private Button butCreateOrder;
    private Button butShowAllOrder;
    private ShowOrdersSubWin showOrdersSubWin;
    private HelpfulMethods hm;
    private HorizontalLayout hlBanner;
    private Image imgHeader;

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    @Autowired
    public ArticlesToOrderView(ArticlesToOrderRep articlesToOrderRep,
                               OrdersArticlesRep ordersArticlesRep,
                               ArticlesRep articlesRep) {
        this.articlesToOrderRep = articlesToOrderRep;
        this.ordersArticlesRep = ordersArticlesRep;
        this.articlesRep = articlesRep;
        this.gridTable = new Grid();
        container = new IndexedContainer();
    }

    @Override
    protected void init(VaadinRequest request) {

        showOrdersSubWin = new ShowOrdersSubWin();
        hm = new HelpfulMethods();

        // build layout
        VerticalLayout mainLayout = new VerticalLayout(gridTable);
        HorizontalLayout buttonLayout = new HorizontalLayout();
        hlBanner = new HorizontalLayout();
        butDeleteSelected = new Button("Usuń zaznaczone");
        butCreateOrder = new Button("Utwórz zamówienie");
        butShowAllOrder = new Button("Pokaż wszystkie zamówienia");

        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/banners/artykuly_do_zamowienia_m.png"));
        imgHeader = new Image("",resource);

        setContent(mainLayout);

        //Add
        container.addContainerProperty("KodKreskowy", String.class, ""); //barcode
        container.addContainerProperty("NrKatalogowy", String.class, ""); //catalogNr
        container.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
        container.addContainerProperty("Ilość", BigDecimal.class, 0.0); //quantity
        container.addContainerProperty("JM", String.class, ""); //unitOfMeasurement
        container.addContainerProperty("Hurtownia", String.class, 0.0f); //wholesale

        //
        hlBanner.addComponent(imgHeader);
        buttonLayout.addComponents(butDeleteSelected, butCreateOrder, butShowAllOrder);
        mainLayout.addComponents(hlBanner, gridTable, buttonLayout);

        //Set

        hlBanner.setComponentAlignment(imgHeader, Alignment.MIDDLE_CENTER);
        hlBanner.setWidth(100, Unit.PERCENTAGE);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        buttonLayout.setSpacing(true);

        gridTable.setHeight(100, Unit.PERCENTAGE);
        gridTable.setWidth(100, Unit.PERCENTAGE);
        gridTable.setSelectionMode(Grid.SelectionMode.MULTI);
        Grid.HeaderRow filterRow = gridTable.appendHeaderRow();
        selection = (Grid.MultiSelectionModel) gridTable.getSelectionModel();
        gridTable.setContainerDataSource(container);

        // Set up a filter for all columns
        for (Object pid : gridTable.getContainerDataSource().getContainerPropertyIds()) {
            Grid.HeaderCell cell = filterRow.getCell(pid);

            // Have an input field to use for filter
            TextField filterField = new TextField();
            filterField.setColumns(6);

            // Update filter When the filter input is changed
            filterField.addTextChangeListener(change -> {
                // Can't modify filters so need to replace
                container.removeContainerFilters(pid);

                // (Re)create the filter if necessary
                if (!change.getText().isEmpty())
                    container.addContainerFilter(
                            new SimpleStringFilter(pid, change.getText(), true, false));
            });
            cell.setComponent(filterField);
        }

        //butDeleteSelected.setEnabled(gridTable.getSelectedRows().size() > 0);

        Object content[][] = QueryMapper.readDataFromQuery(articlesToOrderRep.findAllToGrid());
        if(content != null) {
            for (Object[] row : content) {
                addNewItemToContainer(row);
            }
        }

        //Listeners
        butDeleteSelected.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                //dodac tutaj usuniecie z encji articles_to_order zaznaczony produkt
                if(gridTable.getSelectedRows().size() > 0) {
                    for (Object itemId : selection.getSelectedRows())
                        gridTable.getContainerDataSource().removeItem(itemId);

                    gridTable.getSelectionModel().reset();
                    butDeleteSelected.setEnabled(false);
                } else {
                    //komunikat
                    System.out.println("komunikat");
                }
            }
        });

        butCreateOrder.addClickListener(event -> {
            if(gridTable.getSelectedRows().size() > 0) {
                Item item;
                //poprawic/sprawdzic generowanie nr_zamowienia
                String orderNumber = hm.generateOrderNumber();
                for (Object itemId : selection.getSelectedRows()) {

                    //dodawanie artykulu do utworzonego zamowienia
                    item = container.getItem(itemId);
                    String barcode = item.getItemProperty("KodKreskowy").getValue().toString();
                    BigDecimal quantity = new BigDecimal(item.getItemProperty("Ilość").getValue().toString());
                    Articles articles = articlesRep.findOneByBarcode(barcode);
                    ordersArticlesRep.save(new OrdersArticles(null, orderNumber, quantity, new Date(), articles));

                    //usuwanie artykulu z encji
                    ArticlesToOrder articlesToOrder = articlesToOrderRep.findOneByBarcode(barcode);
                    articlesToOrderRep.delete(articlesToOrder);
                    gridTable.getContainerDataSource().removeItem(itemId);
                }
                gridTable.getSelectionModel().reset();
                Notification.show("Zamówienie zostało utworzone.", Notification.Type.ERROR_MESSAGE);
            } else {
                //komunikat dodac
                System.out.println("komunikat");
            }
        });

        butShowAllOrder.addClickListener(event -> {
            List<Object[]> queryContent = ordersArticlesRep.findOrders();
            showOrdersSubWin.init(queryContent, ordersArticlesRep);
            showOrdersSubWin.setHeight("470px");
            showOrdersSubWin.setWidth("600px");
            UI.getCurrent().addWindow(showOrdersSubWin);
        });

        gridTable.addSelectionListener(select -> { // Java 8
            //Notification.show("...");
            butDeleteSelected.setEnabled(gridTable.getSelectedRows().size() > 0);
        });

    }

    void addNewItemToContainer(Object[] row) {
        Item newItem = container.getItem(container.addItem());
        newItem.getItemProperty("KodKreskowy").setValue(row[1]);
        newItem.getItemProperty("NrKatalogowy").setValue(row[2]);
        newItem.getItemProperty("NazwaArtykułu").setValue(row[3]);
        newItem.getItemProperty("Ilość").setValue(row[4]);
        newItem.getItemProperty("JM").setValue(row[5]);
        newItem.getItemProperty("Hurtownia").setValue(row[6]);
    }

    /*void listArticles() {
        gridTable.setContainerDataSource(container);
    }*/

}
