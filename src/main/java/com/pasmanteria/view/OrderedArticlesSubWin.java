package com.pasmanteria.view;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.*;

import java.math.BigDecimal;

/**
 * Created by adrian on 06.07.2017.
 */
public class OrderedArticlesSubWin extends Window {

    private VerticalLayout mainLayout;
    private HorizontalLayout buttonsLayout;
    private HorizontalLayout labelLayout;

    private Button butClose;

    private Grid gridTable;
    //private Grid.SingleSelectionModel selection;
    private IndexedContainer container;
    private Object[][] queryContent;

    public OrderedArticlesSubWin() {
        super("Szczegóły zamówienia");
        center();
    }

    // subokno obsługujące widok ArticlesToOrderView
    public void init(Object[][] queryContent) {
        this.queryContent = queryContent;
        System.out.println(queryContent);
        // Create
        mainLayout = new VerticalLayout();
        buttonsLayout = new HorizontalLayout();
        labelLayout = new HorizontalLayout();
        butClose = new Button("Zamknij okno");
        gridTable = new Grid();
        container = new IndexedContainer();

        // Add
        container.addContainerProperty("KodKreskowy", String.class, ""); //barcode
        container.addContainerProperty("NrKatalogowy", String.class, ""); //catalogNr
        container.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
        container.addContainerProperty("JM", String.class, ""); //unitOfMeasurement
        container.addContainerProperty("Ilość", BigDecimal.class, 0.0f); //quantity

        mainLayout.addComponents(gridTable, butClose);

        // Set
        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        //buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        //buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        ///buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        //gridTable.setHeight(100, Unit.PERCENTAGE);
        gridTable.setHeight("350px");
        gridTable.setWidth(100, Unit.PERCENTAGE);
        gridTable.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridTable.setContainerDataSource(container);
        Grid.HeaderRow filterRow = gridTable.appendHeaderRow();

        // Set up a filter for all columns
        for (Object pid : gridTable.getContainerDataSource().getContainerPropertyIds()) {
            Grid.HeaderCell cell = filterRow.getCell(pid);

            // Have an input field to use for filter
            TextField filterField = new TextField();
            filterField.setColumns(5);

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

        //selection = (Grid.SingleSelectionModel) gridTable.getSelectionModel();
        //((Grid.SingleSelectionModel) gridTable.getSelectionModel()).setDeselectAllowed(false);

        //butSelect.setEnabled(gridTable.getSelectedRows().size() > 0);

        Object content[][] = queryContent;
        if(content != null) {
            for (Object[] row : content) {
                addNewItemToContainer(row);
            }
        }
        //listArticles();

        // Listeners
        butClose.addClickListener(event -> {
            close();
        });

    }

    /*void listArticles() {
        gridTable.setContainerDataSource(container);
    }
    */

    void addNewItemToContainer(Object[] row) {
        Item newItem = container.getItem(container.addItem());
        System.out.println(row[0].toString());
        newItem.getItemProperty("KodKreskowy").setValue(row[1]);
        newItem.getItemProperty("NrKatalogowy").setValue(row[2]);
        newItem.getItemProperty("NazwaArtykułu").setValue(row[3]);
        newItem.getItemProperty("Ilość").setValue(row[4]);
        newItem.getItemProperty("JM").setValue(row[5]);
    }

}
