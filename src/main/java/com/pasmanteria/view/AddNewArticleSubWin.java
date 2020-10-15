package com.pasmanteria.view;

import com.pasmanteria.BarcodeGenerator;
import com.pasmanteria.ComponentOfInvoice;
import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.model.CategoryTmp;
import com.pasmanteria.repository.CategoryTmpRep;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.MouseEvent;
import java.math.BigDecimal;

/**
 * Created by adrian on 26.06.2017.
 */
public class AddNewArticleSubWin extends Window {

    private VerticalLayout mainLayout;
    private HorizontalLayout textFieldsLayoutTop;
    private HorizontalLayout textFieldsLayoutMiddle;
    private HorizontalLayout tfLayoutBottom;
    private HorizontalLayout buttonsLayout;

    private TextField tfBarcode;
    private TextField tfCatalogNumber;
    private TextField tfArticleName;
    private TextField tfQuantity;
    //private TextField tfUnitOfMeasurement;
    private TextField tfDiscountPercent;
    private TextField tfTaxPercent;
    private TextField tfPriceGrossTotality;
    private TextField tfMarginPercent;
    private TextField tfPricePerUnit;
    private ComboBox cbSelectUnitOfMeasurement;
    private String captions[] = {"m", "szt.", "opak."};
    private Label labCategory;

    private Button butCancel;
    private Button butAccept;
    private Button butAssignCategory;

    @Setter @Getter private String barcode;
    @Setter @Getter private String catalogNr;
    @Setter @Getter private String articleName;
    @Setter @Getter private BigDecimal quantity;
    @Setter @Getter private String unitOfMeasurement;
    @Setter @Getter private Integer discountPercent;
    @Setter @Getter private Integer taxPercent;
    @Setter @Getter private BigDecimal priceGrossTotality;
    @Setter @Getter private Integer marginPercent;
    @Setter @Getter private BigDecimal priceMargin;
    @Setter @Getter private Boolean isAccept = false;
    @Setter @Getter private CategoryTmp categoryTmp;
    @Setter @Getter private String categoryTxt;
    private Boolean categoryIsSet;

    private HelpfulMethods hm;
    private ComponentOfInvoice coi;
    private SetCategoryOfArticleSubWin setCategoryOfArticleSubWin;

    public AddNewArticleSubWin() {
        super("Dodawanie nowego artykułu"); // Set window caption
        this.center();
        //init();
    }

    //funkcja wykorzystywana przy recznym wprowadzaniu faktury
    public void init(String generatedBarcode) {
        // Create
        categoryIsSet = false;
        categoryTxt = null;
        this.barcode = generatedBarcode;
        hm = new HelpfulMethods();

        mainLayout = new VerticalLayout();
        textFieldsLayoutTop = new HorizontalLayout();
        textFieldsLayoutMiddle = new HorizontalLayout();
        tfLayoutBottom = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        butCancel = new Button("Anuluj");
        butAccept = new Button("Zapisz");
        butAssignCategory = new Button("Nadaj kategorię");

        cbSelectUnitOfMeasurement = new ComboBox("JM");
        tfBarcode = new TextField("Kod kreskowy");
        tfCatalogNumber = new TextField("Nr katalogowy");
        tfArticleName = new TextField("Nazwa artykułu");
        tfQuantity = new TextField("Ilość");
        //tfUnitOfMeasurement = new TextField("JM");
        tfDiscountPercent = new TextField("Rabat %");
        tfTaxPercent = new TextField("VAT %");
        tfPriceGrossTotality = new TextField("Wartość brutto");
        tfMarginPercent = new TextField("Marża %");
        tfPricePerUnit = new TextField("Cena");
        labCategory = new Label("Kategoria: ");

        setCategoryOfArticleSubWin = new SetCategoryOfArticleSubWin();

        for (String caption : captions)
            cbSelectUnitOfMeasurement.addItem(caption);
        //cbSelectUnitOfMeasurement.select("BafPol");

        // Add
        tfBarcode.addValidator(new RegexpValidator("[0-9]*", "Wprowadzona wartość musi zawierać tylko cyfry."));
        tfBarcode.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfCatalogNumber.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfArticleName.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 300, true));
        tfQuantity.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfQuantity.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfDiscountPercent.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfDiscountPercent.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfTaxPercent.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfTaxPercent.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfPriceGrossTotality.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfPriceGrossTotality.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfMarginPercent.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfMarginPercent.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        cbSelectUnitOfMeasurement.addValidator(new NullValidator("Pole nie może być puste.", false));

        textFieldsLayoutTop.addComponents(tfBarcode, tfCatalogNumber);
        textFieldsLayoutMiddle.addComponents(tfArticleName);
        tfLayoutBottom.addComponents(tfQuantity, cbSelectUnitOfMeasurement, tfDiscountPercent, tfTaxPercent, tfPriceGrossTotality, tfMarginPercent, tfPricePerUnit);
        buttonsLayout.addComponents(butAssignCategory, butCancel, butAccept);
        mainLayout.addComponents(textFieldsLayoutTop,textFieldsLayoutMiddle, tfLayoutBottom, labCategory, buttonsLayout);

        // Set
        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        //mainLayout.setHeight(100, Unit.PERCENTAGE);
        //mainLayout.setWidth(100, Unit.PERCENTAGE);

        textFieldsLayoutTop.setSpacing(true);
        textFieldsLayoutMiddle.setSpacing(true);
        tfLayoutBottom.setSpacing(true);

        //buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);

        tfArticleName.setWidth("500");
        tfArticleName.setResponsive(true);
        tfQuantity.setWidth("75");
        cbSelectUnitOfMeasurement.setWidth("100");
        tfDiscountPercent.setWidth("50");
        tfTaxPercent.setWidth("50");
        tfPriceGrossTotality.setWidth("120");
        tfMarginPercent.setWidth("50");
        tfPricePerUnit.setWidth("70");
        //tfPricePerUnit.setEnabled(false);

        tfBarcode.setValue(barcode);

        cbSelectUnitOfMeasurement.setNullSelectionAllowed(false);

        // Listeners

        //void mouseClicked(MouseEvent event) {


        tfPricePerUnit.addValueChangeListener(event -> {
            if(countPricePerUnitAtClick()){
                tfPricePerUnit.setValue(priceMargin.toString());
            }
        });

        tfPricePerUnit.addFocusListener(event -> {
            if(countPricePerUnitAtClick()){
                tfPricePerUnit.setValue(priceMargin.toString());
            }
        });
                /*
                event -> {
            if(countPricePerUnitAtClick()) {
                tfPricePerUnit.setValue(priceMargin.toString());
            }
        });
*/
        butCancel.addClickListener(event -> {
            isAccept = false;
            close();
        });
        System.out.println("2");
        butAssignCategory.addClickListener(event -> {
            System.out.println("3");
            setCategoryOfArticleSubWin.init();
            UI.getCurrent().addWindow(setCategoryOfArticleSubWin);
        });

        setCategoryOfArticleSubWin.addCloseListener(e -> {
            if(setCategoryOfArticleSubWin.getIsAccept()) {
                System.out.println("6");
                categoryIsSet = true;
                categoryTxt = setCategoryOfArticleSubWin.getCategoryTxt();
                categoryTmp = setCategoryOfArticleSubWin.getCt();
                labCategory.setValue("Kategoria: " + categoryTxt);
            } else {
                categoryIsSet = false;
                //komunikat ze kategoria nie zotala nadana
            }
        });

        butAccept.addClickListener(event -> {
            if(isValidated()) {
                barcode = tfBarcode.getValue();
                catalogNr = tfCatalogNumber.getValue();
                articleName = tfArticleName.getValue();
                unitOfMeasurement = cbSelectUnitOfMeasurement.getValue().toString();
                discountPercent = hm.convertStringToInteger(tfDiscountPercent.getValue());
                taxPercent = hm.convertStringToInteger(tfTaxPercent.getValue());
                marginPercent = hm.convertStringToInteger(tfMarginPercent.getValue());
                quantity = new BigDecimal(tfQuantity.getValue().replace(",", "."));
                priceGrossTotality = new BigDecimal(tfPriceGrossTotality.getValue().replace(",", "."));
                categoryTmp = setCategoryOfArticleSubWin.getCt();

                coi = new ComponentOfInvoice(priceGrossTotality, quantity, new BigDecimal(discountPercent),
                        new BigDecimal(taxPercent), new BigDecimal(marginPercent));

                priceMargin = coi.getPriceMargin();
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);

            }
        });
    }


//funkcja wykorzystywana przy recznym i automatycznym wczytywaniu faktury
    public void init(String oldBarcode, String oldCatalogNr, String oldArticleName,
                     String oldQuantity, String oldUnitOfMeasurement, String oldDiscountPercent,
                     String oldTaxPercent, String oldPriceGrossTotality, String oldMarginPercent,
                     String oldPriceMargin, String oldCategoryTxt/*, CategoryTmp oldCategoryTmp*/) {
        // Create
        System.out.println("edit2");
        categoryIsSet = false;
        hm = new HelpfulMethods();

        mainLayout = new VerticalLayout();
        textFieldsLayoutTop = new HorizontalLayout();
        textFieldsLayoutMiddle = new HorizontalLayout();
        tfLayoutBottom = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        butCancel = new Button("Anuluj");
        butAccept = new Button("Zapisz");
        butAssignCategory = new Button("Nadaj kategorię");

        tfBarcode = new TextField("Kod kreskowy");
        cbSelectUnitOfMeasurement = new ComboBox("JM");
        tfCatalogNumber = new TextField("Nr katalogowy");
        tfArticleName = new TextField("Nazwa artykułu");
        tfQuantity = new TextField("Ilość");
        //tfUnitOfMeasurement = new TextField("JM");
        tfDiscountPercent = new TextField("Rabat %");
        tfTaxPercent = new TextField("VAT %");
        tfPriceGrossTotality = new TextField("Wartość brutto");
        tfMarginPercent = new TextField("Marża %");
        tfPricePerUnit = new TextField("Cena");
        labCategory = new Label("Kategoria: " + oldCategoryTxt);
        System.out.println("edit3");

        setCategoryOfArticleSubWin = new SetCategoryOfArticleSubWin();

        for (String caption : captions)
            cbSelectUnitOfMeasurement.addItem(caption);

        // Add
        tfBarcode.addValidator(new RegexpValidator("[0-9]*", "Wprowadzona wartość musi zawierać tylko cyfry."));
        tfBarcode.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfCatalogNumber.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfArticleName.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 300, true));
        tfQuantity.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfQuantity.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfDiscountPercent.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfDiscountPercent.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfTaxPercent.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfTaxPercent.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        System.out.println("edit4");
        tfPriceGrossTotality.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        System.out.println("edit5");
        tfPriceGrossTotality.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        System.out.println("edit6");
        tfMarginPercent.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfMarginPercent.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        cbSelectUnitOfMeasurement.addValidator(new NullValidator("Pole nie może być puste", false));

        textFieldsLayoutTop.addComponents(tfBarcode, tfCatalogNumber);
        System.out.println("edit7");
        textFieldsLayoutMiddle.addComponents(tfArticleName);
        tfLayoutBottom.addComponents(tfQuantity, cbSelectUnitOfMeasurement, tfDiscountPercent, tfTaxPercent, tfPriceGrossTotality, tfMarginPercent, tfPricePerUnit);
        System.out.println("edit8");
        buttonsLayout.addComponents(butAssignCategory, butCancel, butAccept);
        mainLayout.addComponents(textFieldsLayoutTop,textFieldsLayoutMiddle, tfLayoutBottom, labCategory, buttonsLayout);

        // Set
        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        //mainLayout.setHeight(100, Unit.PERCENTAGE);
        //mainLayout.setWidth(100, Unit.PERCENTAGE);

        textFieldsLayoutTop.setSpacing(true);
        textFieldsLayoutMiddle.setSpacing(true);
        tfLayoutBottom.setSpacing(true);

        //buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);

        tfArticleName.setWidth("500");
        tfQuantity.setWidth("75");
        cbSelectUnitOfMeasurement.setWidth("100");
        //tfUnitOfMeasurement.setWidth("50");
        tfDiscountPercent.setWidth("50");
        tfTaxPercent.setWidth("50");
        tfPriceGrossTotality.setWidth("120");
        tfMarginPercent.setWidth("50");
        tfPricePerUnit.setWidth("50");
        tfPricePerUnit.setEnabled(false);
        tfBarcode.setEnabled(false);
        cbSelectUnitOfMeasurement.setNullSelectionAllowed(false);

        tfBarcode.setValue(oldBarcode.trim());
        tfCatalogNumber.setValue(oldCatalogNr.replaceAll("\\s",""));
        tfArticleName.setValue(oldArticleName);
        tfQuantity.setValue(oldQuantity);
        cbSelectUnitOfMeasurement.setValue(oldUnitOfMeasurement);
        //tfUnitOfMeasurement.setValue(oldUnitOfMeasurement);
        tfDiscountPercent.setValue(oldDiscountPercent.replaceAll("\\s",""));
        tfTaxPercent.setValue(oldTaxPercent.replaceAll("\\s",""));
        tfPriceGrossTotality.setValue(oldPriceGrossTotality.replaceAll("\\s",""));
        tfMarginPercent.setValue(oldMarginPercent);
        tfPricePerUnit.setValue(oldPriceMargin);

        // Listeners

        butCancel.addClickListener(event -> {
                isAccept = false;
                close();
        });

        butAssignCategory.addClickListener(event -> {
            setCategoryOfArticleSubWin.init();
            UI.getCurrent().addWindow(setCategoryOfArticleSubWin);
        });

        setCategoryOfArticleSubWin.addCloseListener(e -> {
            if(setCategoryOfArticleSubWin.getIsAccept()) {
                categoryIsSet = true;
                categoryTxt = setCategoryOfArticleSubWin.getCategoryTxt();
                categoryTmp = setCategoryOfArticleSubWin.getCt();
                labCategory.setValue("Kategoria: " + categoryTxt);
            } else {
                categoryIsSet = false;
                categoryTxt = oldCategoryTxt;
                //categoryTmp = oldCategoryTmp;
            }
        });

        butAccept.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if(isValidated()) {
                    barcode = tfBarcode.getValue();
                    catalogNr = tfCatalogNumber.getValue();
                    articleName = tfArticleName.getValue();
                    unitOfMeasurement = cbSelectUnitOfMeasurement.getValue().toString();
                    discountPercent = hm.convertStringToInteger(tfDiscountPercent.getValue());
                    taxPercent = hm.convertStringToInteger(tfTaxPercent.getValue());
                    marginPercent = hm.convertStringToInteger(tfMarginPercent.getValue());
                    quantity = new BigDecimal(tfQuantity.getValue().replace(",", "."));
                    priceGrossTotality = new BigDecimal(tfPriceGrossTotality.getValue().replace(",", "."));
                    categoryTmp = setCategoryOfArticleSubWin.getCt();
                    categoryTxt = setCategoryOfArticleSubWin.getCategoryTxt();

                    coi = new ComponentOfInvoice(priceGrossTotality, quantity, new BigDecimal(discountPercent),
                            new BigDecimal(taxPercent), new BigDecimal(marginPercent));

                    priceMargin = coi.getPriceMargin();
                    isAccept = true;
                    close();
                }
            }
        });
    }

    private boolean isValidated() {
        if(tfArticleName.isValid()
                && tfBarcode.isValid()
                && tfCatalogNumber.isValid()
                && tfQuantity.isValid()
                && !cbSelectUnitOfMeasurement.isEmpty()
                && tfDiscountPercent.isValid()
                && tfTaxPercent.isValid()
                && tfPriceGrossTotality.isValid()
                && tfMarginPercent.isValid()
                && cbSelectUnitOfMeasurement.isValid()
                && categoryTxt != null
                ) {
            return true;
        } else return false;
    }

    private boolean countPricePerUnitAtClick() {
        if(tfPriceGrossTotality.isValid()
                && tfQuantity.isValid()
                && tfDiscountPercent.isValid()
                && tfTaxPercent.isValid()
                && tfMarginPercent.isValid()) {
            priceGrossTotality = new BigDecimal(tfPriceGrossTotality.getValue().replace(",", "."));
            quantity = new BigDecimal(tfQuantity.getValue().replace(",", "."));
            discountPercent = hm.convertStringToInteger(tfDiscountPercent.getValue());
            taxPercent = hm.convertStringToInteger(tfTaxPercent.getValue());
            marginPercent = hm.convertStringToInteger(tfMarginPercent.getValue());
            coi = new ComponentOfInvoice(priceGrossTotality, quantity, new BigDecimal(discountPercent),
                    new BigDecimal(taxPercent), new BigDecimal(marginPercent));
            priceMargin = coi.getPriceMargin();
            return true;
        } else {
            return false;
        }
    }

}

