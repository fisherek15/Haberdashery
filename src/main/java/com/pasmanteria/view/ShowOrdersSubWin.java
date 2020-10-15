package com.pasmanteria.view;

import com.pasmanteria.CreatePdfDocument;
import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.QueryMapper;
import com.pasmanteria.repository.OrdersArticlesRep;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by adrian on 06.07.2017.
 */
public class ShowOrdersSubWin extends Window {

    private VerticalLayout mainLayout;
    private HorizontalLayout buttonsLayout;
    private HorizontalLayout labelLayout;

    private Button butClose;
    private Button butDetails;
    private Button butPrintOrder;

    private Grid grid;
    private Grid.SingleSelectionModel selection;
    private IndexedContainer container;

    @Getter @Setter private Boolean isAccept = false;
    private HelpfulMethods hm;

    private OrderedArticlesSubWin orderedArticlesSubWin;

    public ShowOrdersSubWin() {
        super("Zamówienia");
        center();
    }

    // subokno obsługujące widok ArticlesToOrderView
    public void init(List<Object[]> queryContent, OrdersArticlesRep ordersArticlesRep) {

        orderedArticlesSubWin = new OrderedArticlesSubWin();

        hm = new HelpfulMethods();

        // Create
        mainLayout = new VerticalLayout();
        buttonsLayout = new HorizontalLayout();
        labelLayout = new HorizontalLayout();
        butClose = new Button("Zamknij okno");
        butDetails = new Button("Pokaż szczegóły");
        butPrintOrder = new Button("Drukuj zamównie");
        grid = new Grid();
        container = new IndexedContainer();

        // Add
        container.addContainerProperty("NumerZamówienia", String.class, ""); //orderNumber
        container.addContainerProperty("DataUtworzenia", Date.class, ""); //createDate

        buttonsLayout.addComponents(butClose, butDetails, butPrintOrder);
        mainLayout.addComponents(grid, buttonsLayout);

        // Set
        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        //buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        //buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        //buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        //grid.setHeight(100, Unit.PERCENTAGE);
        grid.setHeight("300px");
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setContainerDataSource(container);

        selection = (Grid.SingleSelectionModel) grid.getSelectionModel();
        ((Grid.SingleSelectionModel) grid.getSelectionModel()).setDeselectAllowed(false);

        //butDetails.setEnabled(grid.getSelectedRows().size() == 1);
        butPrintOrder.setEnabled(grid.getSelectedRows().size() == 1);


        if(queryContent.size() != 0) {
            Object tab[][] = QueryMapper.readDataFromQuery(queryContent);
            for (Object[] row : tab) {
                grid.addRow(row);
            }
        }

        // Listeners
        butClose.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });

        butDetails.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Object selected = ((Grid.SingleSelectionModel)
                        grid.getSelectionModel()).getSelectedRow();
                System.out.println("Selected: " + selected);
                if (selected != null) {
                    //pobierz numer zamowienia
                    String orderNumber  = grid.getContainerDataSource().getItem(selected).getItemProperty("NumerZamówienia").getValue().toString();
                    Object[][] queryContent = QueryMapper.readDataFromQuery(ordersArticlesRep.findByOrderNumber(orderNumber));
                    System.out.println("OrderNumber: " + orderNumber);
                    orderedArticlesSubWin.init(queryContent);
                    orderedArticlesSubWin.setHeight("500px");
                    orderedArticlesSubWin.setWidth("800px");
                    UI.getCurrent().addWindow(orderedArticlesSubWin);
                } else {
                    //Notification.show("Wybierz zamówienie, aby zobaczyć jego szczegóły.", Notification.Type.ERROR_MESSAGE);

                }
            }
        });

        butPrintOrder.addClickListener(event -> {
            Object selected = ((Grid.SingleSelectionModel)grid.getSelectionModel()).getSelectedRow();
            if (selected != null) {
                String nameOfOrder = "zamowienie_" + HelpfulMethods.convertDateToNameOfDocument(new Date());
                String orderNumber  = grid.getContainerDataSource().getItem(selected).getItemProperty("NumerZamówienia").getValue().toString();
                String documentTitle = "Zamówienie nr " + orderNumber;
                Object[][] queryArticlesOfOrders = QueryMapper.readDataFromQuery(ordersArticlesRep.findArticlesToOrder(orderNumber));
                CreatePdfDocument.createPdf(queryArticlesOfOrders, nameOfOrder, documentTitle);
            }
        });

        grid.addSelectionListener(select -> { // Java 8
            butDetails.setEnabled(grid.getSelectedRows().size() == 1);
            butPrintOrder.setEnabled(grid.getSelectedRows().size() == 1);
        });

        //return itemId;
    }
/*
    void listArticles() {
        grid.setContainerDataSource(container);
    }
    */
/*
    void addNewItemToContainer(Object row) {
        Item newItem = container.getItem(container.addItem());
        newItem.getItemProperty("NumerZamówienia").setValue(row.getOrderNumber());
        newItem.getItemProperty("DataUtworzenia").setValue(row.getDateAdded());
    }
    */
}
