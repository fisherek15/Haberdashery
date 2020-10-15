package com.pasmanteria.view;

import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.model.Articles;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Mike on 06/02/2017.
 */
public class ChooseArticleSubWin extends Window {

    private VerticalLayout mainLayout;
    private HorizontalLayout buttonsLayout;
    private HorizontalLayout tfLayout;

    private Button butCancel;
    private Button butSelect;

    private TextField tfQuantity;

    private Grid grid;
    private Grid.SingleSelectionModel selection;
    private IndexedContainer container;
    ;
    private Object itemId;

    @Setter @Getter private String barcode;
    @Setter @Getter private String catalogNr;
    @Setter @Getter private String articleName;
    @Setter @Getter private BigDecimal quantity;
    @Setter @Getter private String unitOfMeasurement;
    @Setter @Getter private Integer discountPercent;
    @Setter @Getter private Integer taxPercent;
    @Setter @Getter private Integer marginPercent;
    @Setter @Getter private BigDecimal priceMargin;
    @Setter @Getter private Boolean isAccept = false;
    @Setter @Getter private String categoryTxt;


    public ChooseArticleSubWin() {
        super("Wybieranie artykułu");
        center();
        //itemId = null;
    }

    // subokno obsługujące widok ReceiptOfSupplyAuto3View
    public void init(List<Articles> queryContent) {
        // Create
        mainLayout = new VerticalLayout();
        buttonsLayout = new HorizontalLayout();
        tfLayout = new HorizontalLayout();
        tfQuantity = new TextField("Ilość");
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        grid = new Grid();
        container = new IndexedContainer();

        tfQuantity.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfQuantity.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));

        // Add
        container.addContainerProperty("KodKreskowy", String.class, ""); //barcode
        container.addContainerProperty("NrKatalogowy", String.class, ""); //catalogNr
        container.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
        container.addContainerProperty("JM", String.class, ""); //unitOfMeasurement
        container.addContainerProperty("Ilość", BigDecimal.class, 0.0f); //quantity
        container.addContainerProperty("Rabat", Integer.class, 0.0f); //discountPercent
        container.addContainerProperty("VAT", Integer.class, 0.0f); //taxPercent
        container.addContainerProperty("Marża", Integer.class, 0.0); //marginPercent
        container.addContainerProperty("Cena", BigDecimal.class, 0.0f); //priceMargin
        container.addContainerProperty("Kategoria", String.class, ""); //category

        tfLayout.addComponent(tfQuantity);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(grid, tfLayout, buttonsLayout);

        // Set
        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        //buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        //grid.setHeight(100, Unit.PERCENTAGE);
        grid.setHeight("300px");
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        selection = (Grid.SingleSelectionModel) grid.getSelectionModel();
        ((Grid.SingleSelectionModel) grid.getSelectionModel()).setDeselectAllowed(false);

        butSelect.setEnabled(grid.getSelectedRows().size() > 0);

        List<Articles> content = queryContent;
        if(content != null) {
            for (Articles row : content) {
                System.out.println("2a");
                addNewItemToContainer(row);
            }
        }
        listArticles();

        // Listeners
        butCancel.addClickListener(event -> {
            isAccept = false;
            close();
        });
        butSelect.addClickListener(event -> {
            Object selected = ((Grid.SingleSelectionModel)
                    grid.getSelectionModel()).getSelectedRow();
            if(isValidated()) {
                if (selected != null) {
                    barcode = grid.getContainerDataSource().getItem(selected).getItemProperty("KodKreskowy").getValue().toString();
                    catalogNr = grid.getContainerDataSource().getItem(selected).getItemProperty("NrKatalogowy").getValue().toString();
                    articleName = grid.getContainerDataSource().getItem(selected).getItemProperty("NazwaArtykułu").getValue().toString();
                    unitOfMeasurement = grid.getContainerDataSource().getItem(selected).getItemProperty("JM").getValue().toString();
                    discountPercent = HelpfulMethods.convertStringToInteger(grid.getContainerDataSource().getItem(selected).getItemProperty("Rabat").getValue().toString());
                    taxPercent = HelpfulMethods.convertStringToInteger(grid.getContainerDataSource().getItem(selected).getItemProperty("VAT").getValue().toString());
                    marginPercent = HelpfulMethods.convertStringToInteger(grid.getContainerDataSource().getItem(selected).getItemProperty("Marża").getValue().toString());
                    priceMargin = HelpfulMethods.convertStringAmountToBigDecimal(grid.getContainerDataSource().getItem(selected).getItemProperty("Cena").getValue().toString());
                    quantity = HelpfulMethods.convertStringAmountToBigDecimal(tfQuantity.getValue().replace(",", "."));
                    categoryTxt = grid.getContainerDataSource().getItem(selected).getItemProperty("Kategoria").getValue().toString();
                    isAccept = true;
                    System.out.println("3a");
                }
                close();
            } else {
                Notification.show("Uwaga!", "Pole 'Ilość' nie zostało poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });

        grid.addSelectionListener(select -> { // Java 8
            Notification.show(select.getAdded().size() +
                    " zaznaczono, " +
                    select.getRemoved().size() +
                    " odznaczono.");
            // Allow deleting only if there's any selected
            butSelect.setEnabled(
                    grid.getSelectedRows().size() == 1);
        });

        //return itemId;
    }

    void addNewItemToContainer(Articles row) {
        Item newItem = container.getItem(container.addItem());
        newItem.getItemProperty("KodKreskowy").setValue(row.getBarcode());
        newItem.getItemProperty("NrKatalogowy").setValue(row.getCatalogNumber());
        newItem.getItemProperty("NazwaArtykułu").setValue(row.getArticleName());
        newItem.getItemProperty("Ilość").setValue(row.getQuantity());
        newItem.getItemProperty("JM").setValue(row.getUnitOfMeasurement());
        newItem.getItemProperty("Rabat").setValue(row.getDiscountPercent());
        newItem.getItemProperty("VAT").setValue(row.getTaxPercent());
        newItem.getItemProperty("Marża").setValue(row.getMarginPercent());
        newItem.getItemProperty("Cena").setValue(row.getPriceMargin());
        newItem.getItemProperty("Kategoria").setValue(row.getCategoryTxt());
    }

    void listArticles() {
        grid.setContainerDataSource(container);
    }

    private boolean isValidated() {
        if(tfQuantity.isValid()) {
            return true;
        } else return false;
    }

}

