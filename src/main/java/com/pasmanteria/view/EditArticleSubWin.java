package com.pasmanteria.view;

import com.pasmanteria.HelpfulMethods;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Mike on 16/02/2017.
 */
public class EditArticleSubWin extends Window {

    private VerticalLayout mainLayout;
    private HorizontalLayout textFieldsLayoutTop;
    private HorizontalLayout textFieldsLayoutMiddle;
    private HorizontalLayout textFieldsLayoutBottom;
    private HorizontalLayout tfLayoutBottom;
    private HorizontalLayout buttonsLayout;

    private TextField tfBarcode;
    private TextField tfCatalogNumber;
    private TextField tfArticleName;
    private TextField tfQuantity;
    private TextField tfUnitOfMeasurement;
    private TextField tfDiscountPercent;
    private TextField tfTaxPercent;
    private TextField tfPriceGrossTotality;
    private TextField tfMarginPercent;

    private Button butCancel;
    private Button butAccept;

    @Setter @Getter private String catalogNr;
    @Setter @Getter private String articleName;
    @Setter @Getter private BigDecimal quantity;
    @Setter @Getter private String unitOfMeasurement;
    @Setter @Getter private String discountPercent;
    @Setter @Getter private String taxPercent;
    @Setter @Getter private String priceGrossTotality;
    @Setter @Getter private Integer marginPercent;
    @Setter @Getter private Boolean isAccept = false;

    public EditArticleSubWin() {
        super("Edytuj produkt"); // Set window caption
        this.center();
    }

    //subokno wywolywane z widoku ReceiptOfSupplyAuto2View
    public void init(String oldCatalogNr, String oldArticleName, String oldDiscountPercent, String oldTaxPercent, String oldPriceGrossTotality) {
        // Create
        mainLayout = new VerticalLayout();
        textFieldsLayoutTop = new HorizontalLayout();
        textFieldsLayoutBottom = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        butCancel = new Button("Anuluj");
        butAccept = new Button("Akceptuj");

        tfCatalogNumber = new TextField("Nr katalogowy");
        tfArticleName = new TextField("Nazwa artykułu");
        tfDiscountPercent = new TextField("Rabat %");
        tfTaxPercent = new TextField("VAT %");
        tfPriceGrossTotality = new TextField("Wartość brutto");

        // Add

        tfCatalogNumber.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfArticleName.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 300, true));
        tfDiscountPercent.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfDiscountPercent.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfTaxPercent.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfTaxPercent.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfPriceGrossTotality.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfPriceGrossTotality.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));

        textFieldsLayoutTop.addComponents(tfCatalogNumber, tfArticleName);
        textFieldsLayoutBottom.addComponents(tfDiscountPercent, tfTaxPercent, tfPriceGrossTotality);
        buttonsLayout.addComponents(butCancel, butAccept);
        mainLayout.addComponents(textFieldsLayoutTop,textFieldsLayoutBottom,buttonsLayout);

        // Set
        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        textFieldsLayoutTop.setSpacing(true);
        textFieldsLayoutBottom.setSpacing(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butAccept, Alignment.MIDDLE_CENTER);

        tfArticleName.setWidth("400");

        tfCatalogNumber.setValue(oldCatalogNr.replaceAll("\\s",""));
        tfArticleName.setValue(oldArticleName);
        tfDiscountPercent.setValue(oldDiscountPercent.replaceAll("\\s",""));
        tfTaxPercent.setValue(oldTaxPercent.replaceAll("\\s",""));
        tfPriceGrossTotality.setValue(oldPriceGrossTotality.replaceAll("\\s",""));

        // Listeners

        butCancel.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                isAccept = false;
                close();
            }
        });

        butAccept.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if(isValidated()) {
                    catalogNr = tfCatalogNumber.getValue();
                    articleName = tfArticleName.getValue();
                    discountPercent = tfDiscountPercent.getValue();
                    taxPercent = tfTaxPercent.getValue();
                    priceGrossTotality = tfPriceGrossTotality.getValue().replace(",", ".");
                    isAccept = true;
                    close();
                } else {
                    Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
                }
            }
        });
    }

    private boolean isValidated() {
        if(tfCatalogNumber.isValid()
                && tfArticleName.isValid()
                && tfDiscountPercent.isValid()
                && tfTaxPercent.isValid()
                && tfPriceGrossTotality.isValid()
                ) {
            return true;
        } else return false;
    }

}

