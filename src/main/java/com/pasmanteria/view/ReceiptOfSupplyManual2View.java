package com.pasmanteria.view;

import com.pasmanteria.BarcodeGenerator;
import com.pasmanteria.ComponentOfInvoice;
import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.model.*;
import com.pasmanteria.repository.*;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 30/12/2016.
 */

//@SpringUI(path = "/newItemManualUI")
@SpringView(name= ReceiptOfSupplyManual2View.VIEW_NAME)
@Theme("valo")
public class ReceiptOfSupplyManual2View extends VerticalLayout implements View {
    public static final String VIEW_NAME = "ROSM2";
    private String barcode;
    private String catalogNumber;
    private String articleName;
    private BigDecimal quantity;
    private String unitOfMeasurement;
    private Integer discountPercent;
    private Integer taxPercent;
    private BigDecimal priceGrossTotality;
    private Integer marginPercent;
    private BigDecimal priceNetWithoutDiscount;
    private BigDecimal priceNetPcs;
    private BigDecimal priceGrossPcs;
    private BigDecimal valueNet;
    private BigDecimal valueVat;
    private BigDecimal priceMargin;

    private HorizontalLayout gridOptionsLayout;
    private HorizontalLayout buttonsLayout;
    private HorizontalLayout chooseFieldLayout;
    private HorizontalLayout hlBanner;

    private Image imgHeader;
    private Label labInvoiceName;

    private TextField tfBarcode;
    private TextField tfCatalogNr;

    private Button butChooseArticle;
    private Button butCancelNewItems;
    private Button butAddNewItems;
    private Button butDeleteSelected;
    private Button butEditSelected;
    private Button butAddNewItem;

    private Grid gridTable;
    private Grid.MultiSelectionModel selection;
    private IndexedContainer container;

    private ArticlesTmpRep articlesTmpRep;
    private ArticlesRep articlesRep;
    private CategoryTmpRep categoryTmpRep;
    private CategoryRep categoryRep;
    private InvoicesSupplyRep invoicesSupplyRep;
    private InvoicesSupplyTmpRep invoicesSupplyTmpRep;
    private ArticlesOcrTmpRep articlesOcrTmpRep;
    private StaticVariablesRep staticVariablesRep;
    private ArticlesAdditionalInfoRep articlesAdditionalInfoRep;

    private Integer quantityRowsFromQuery;
    private HelpfulMethods hm;
    private AddNewArticleSubWin subWindowAddNew;
    private ChooseArticleSubWin chooseArticleSubWin;
    private Selection3OptionsSubWin selection3OptionsSubWin;
    private InformationSubWin informationSubWin;

    private int indexEditedField;
    private String previousBarcode;
    //private String categoryTxt;
    Item editedItem;
    Item selectItem;

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    @Autowired
    public ReceiptOfSupplyManual2View(ArticlesTmpRep articlesTmpRep,
                                      ArticlesRep articlesRep,
                                      CategoryTmpRep categoryTmpRep,
                                      InvoicesSupplyRep invoicesSupplyRep,
                                      InvoicesSupplyTmpRep invoicesSupplyTmpRep,
                                      ArticlesOcrTmpRep articlesOcrTmpRep,
                                      StaticVariablesRep staticVariablesRep,
                                      CategoryRep categoryRep,
                                      ArticlesAdditionalInfoRep articlesAdditionalInfoRep) {
        this.articlesTmpRep = articlesTmpRep;
        this.articlesRep = articlesRep;
        this.categoryTmpRep = categoryTmpRep;
        this.invoicesSupplyRep = invoicesSupplyRep;
        this.invoicesSupplyTmpRep = invoicesSupplyTmpRep;
        this.articlesOcrTmpRep = articlesOcrTmpRep;
        this.staticVariablesRep = staticVariablesRep;
        this.categoryRep = categoryRep;
        this.articlesAdditionalInfoRep = articlesAdditionalInfoRep;
        gridTable = new Grid();
        container = new IndexedContainer();
        hm = new HelpfulMethods();
        selection3OptionsSubWin = new Selection3OptionsSubWin();
    }

    @PostConstruct
    void init() {
        // Create
        //mainLayout = new VerticalLayout();
        gridOptionsLayout = new HorizontalLayout();
        chooseFieldLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        hlBanner = new HorizontalLayout();

        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/banners/reczne_dodawanie_towaru_m.png"));
        imgHeader = new Image("",resource);
        labInvoiceName = new Label("Nr faktury itd");

        tfBarcode = new TextField("Kod kreskowy");
        tfCatalogNr = new TextField("Numer katalogowy");

        //butAddCategory = new Button("Dodaj kategorie");
        butChooseArticle = new Button("Szukaj artykułu");
        butCancelNewItems = new Button("Anuluj dodawanie towaru");
        butAddNewItems = new Button("Dodaj towar do sklepu");
        butDeleteSelected = new Button("Usuń zaznaczone");
        butEditSelected = new Button("Edytuj zaznaczone");
        butAddNewItem = new Button("Dodaj nowy / Edytuj");

        subWindowAddNew = new AddNewArticleSubWin();
        chooseArticleSubWin = new ChooseArticleSubWin();

        // Add
        container.addContainerProperty("KodKreskowy", String.class, ""); //barcode
        container.addContainerProperty("NrKatalogowy", String.class, ""); //catalogNr
        container.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
        container.addContainerProperty("Ilość", BigDecimal.class, 0.0); //quantity
        container.addContainerProperty("JM", String.class, ""); //unitOfMeasurement
        container.addContainerProperty("Rabat", Integer.class, 0); //discountPercent
        container.addContainerProperty("VAT", Integer.class, 0); //taxPercent
        container.addContainerProperty("Wartość brutto", BigDecimal.class, 0.0); //priceGrossTotality
        container.addContainerProperty("Marża", Integer.class, 0); //marginPercent
        container.addContainerProperty("Cena", BigDecimal.class, 0.0f); //priceMargin

        butCancelNewItems.addStyleName("danger");
        butAddNewItems.addStyleName("friendly");
        //
        hlBanner.addComponent(imgHeader);
        gridOptionsLayout.addComponents(butDeleteSelected, butChooseArticle, butAddNewItem/*, butEditSelected*/);
        buttonsLayout.addComponents(butCancelNewItems, butAddNewItems);
        chooseFieldLayout.addComponents(tfBarcode, tfCatalogNr, butChooseArticle);
        addComponents(hlBanner, chooseFieldLayout, gridTable, gridOptionsLayout,  buttonsLayout);

        // Set
        //setContent(mainLayout);

        this.setMargin(true);
        this.setSpacing(true);


        hlBanner.setComponentAlignment(imgHeader, Alignment.MIDDLE_CENTER);
        hlBanner.setWidth(100, Unit.PERCENTAGE);

        gridTable.setHeight(100, Unit.PERCENTAGE);
        gridTable.setWidth(100, Unit.PERCENTAGE);
        gridTable.setSelectionMode(Grid.SelectionMode.MULTI);

        selection = (Grid.MultiSelectionModel) gridTable.getSelectionModel();

        gridOptionsLayout.setSpacing(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        chooseFieldLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancelNewItems, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butAddNewItems, Alignment.MIDDLE_CENTER);
        chooseFieldLayout.setComponentAlignment(butChooseArticle, Alignment.BOTTOM_CENTER);

        butDeleteSelected.setEnabled(gridTable.getSelectedRows().size() > 0);
        butChooseArticle.setEnabled(gridTable.getSelectedRows().size() == 1);
        butEditSelected.setEnabled(gridTable.getSelectedRows().size() == 1);
        butAddNewItem.setEnabled(gridTable.getSelectedRows().size() == 1);

        butAddNewItem.setEnabled(true);
        butChooseArticle.setEnabled(true);
        butDeleteSelected.setEnabled(false);

        // end Set

        //dodawanie artykulow z komletnymi informacjami (zaczytanie na nowo np. po odświeżeniu strony)
        List<ArticlesTmp> queryContent = articlesTmpRep.findAll();
        if(queryContent != null) {
            for (ArticlesTmp row : queryContent) {
                addNewItemToContainer(row);
            }
        }
        listArticles();

        //sprawdzam czy istnieje rozpoczeta sesja recznego dodawania towaru
        int rosmIsSaved = staticVariablesRep.findByName("rosmissaved").getValue();
        if (rosmIsSaved == 1) {
            String info = "Wczytano zapisana sesje recznego dodawania towaru.";
            String caption = "Informacja";
            informationSubWin = new InformationSubWin();
            informationSubWin.init(caption, info);
            UI.getCurrent().addWindow(informationSubWin);
            informationSubWin.setModal(true);
        }

        // Listeners
        butDeleteSelected.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                for (Object itemId : selection.getSelectedRows()) {
                    //tutaj dodać usuniecie z bazy
                    gridTable.getContainerDataSource().removeItem(itemId);
                }
                butDeleteSelected.setEnabled(false);
                gridTable.getSelectionModel().reset();
            }
        });

        gridTable.addSelectionListener(select -> { // Java 8
            // Allow deleting only if there's any selected
            butDeleteSelected.setEnabled(gridTable.getSelectedRows().size() > 0);
        });


        //otwiera okno edycji wybranego artykulu i dodawania nowego
        butAddNewItem.addClickListener(event -> {
            String newBarcode;
            Articles a1;
            ArticlesTmp at1;
            if (gridTable.getSelectedRows().size() == 1) {
                System.out.println("edit1");
                //Item editedItem = null;
                for (Object itemId : selection.getSelectedRows())
                    editedItem = container.getItem(itemId);
                //previousBarcode = new String("");
                previousBarcode = editedItem.getItemProperty("KodKreskowy").getValue().toString();
                //String ctt = new String("");
                String ctt = articlesTmpRep.getCategoryTxtByBarcode(previousBarcode);
                //CategoryTmp ct = articlesTmpRep.getCategoryIdByBarcode(previousBarcode);
                System.out.println("edit1a");
                do {
                    newBarcode = BarcodeGenerator.generateBarcode();
                    a1 = articlesRep.findOneByBarcode(newBarcode);
                    at1 = articlesTmpRep.findOneByBarcode(newBarcode);
                } while(a1 != null || at1 != null);
                subWindowAddNew.init(
                        //sprawdzic czy nie lepiej by bylo podstawiac te wartosci bezposrednio z zapytania zamist z pobierac z grida
                        newBarcode,
                        editedItem.getItemProperty("NrKatalogowy").getValue().toString(),
                        editedItem.getItemProperty("NazwaArtykułu").getValue().toString(),
                        editedItem.getItemProperty("Ilość").getValue().toString(),
                        editedItem.getItemProperty("JM").getValue().toString(),
                        editedItem.getItemProperty("Rabat").getValue().toString(),
                        editedItem.getItemProperty("VAT").getValue().toString(),
                        editedItem.getItemProperty("Wartość brutto").getValue().toString(),
                        editedItem.getItemProperty("Marża").getValue().toString(),
                        editedItem.getItemProperty("Cena").getValue().toString(),
                        ctt/*, ct*/);
                subWindowAddNew.setHeight("350px");
                subWindowAddNew.setWidth("700px");
                System.out.println("edit1b");
                UI.getCurrent().addWindow(subWindowAddNew);
            } else {
                System.out.println("1");
                do {
                    newBarcode = BarcodeGenerator.generateBarcode();
                    a1 = articlesRep.findOneByBarcode(newBarcode);
                    at1 = articlesTmpRep.findOneByBarcode(newBarcode);
                } while(a1 != null || at1 != null);
                subWindowAddNew.init(newBarcode);
                UI.getCurrent().addWindow(subWindowAddNew);

            }
        });

        // listener sprawdzajacy czy subokno dodawania nowego artykulu zostalo zamkniete
        subWindowAddNew.addCloseListener(e -> {
            if(subWindowAddNew.getIsAccept()) {
                System.out.println("edit2");
                gridTable.getSelectionModel().reset();
                barcode = subWindowAddNew.getBarcode();
                catalogNumber = subWindowAddNew.getCatalogNr();
                articleName = subWindowAddNew.getArticleName();
                quantity = subWindowAddNew.getQuantity();
                unitOfMeasurement = subWindowAddNew.getUnitOfMeasurement();
                discountPercent = subWindowAddNew.getDiscountPercent();
                taxPercent = subWindowAddNew.getTaxPercent();
                priceGrossTotality = subWindowAddNew.getPriceGrossTotality();
                marginPercent =  subWindowAddNew.getMarginPercent();
                priceMargin = subWindowAddNew.getPriceMargin();

                CategoryTmp categoryTmp = subWindowAddNew.getCategoryTmp();
                String categoryTxt = subWindowAddNew.getCategoryTxt();

                ComponentOfInvoice c = new ComponentOfInvoice(
                        priceGrossTotality, quantity,
                        new BigDecimal(subWindowAddNew.getDiscountPercent()),
                        new BigDecimal(subWindowAddNew.getTaxPercent()),
                        new BigDecimal(subWindowAddNew.getMarginPercent()));

                priceNetWithoutDiscount = c.getPriceNetWithoutDiscount();
                priceNetPcs = c.getPriceNetPcs();
                priceGrossPcs = c.getPriceGrossPcs();
                valueNet = c.getValueNet();
                valueVat = c.getValueVat();

                if(editedItem != null) {
                    System.out.println("edit3");
                    editedItem.getItemProperty("KodKreskowy").setValue(barcode);
                    editedItem.getItemProperty("NrKatalogowy").setValue(catalogNumber);
                    editedItem.getItemProperty("NazwaArtykułu").setValue(articleName);
                    editedItem.getItemProperty("Ilość").setValue(quantity);
                    editedItem.getItemProperty("JM").setValue(unitOfMeasurement);
                    editedItem.getItemProperty("Rabat").setValue(discountPercent);
                    editedItem.getItemProperty("VAT").setValue(taxPercent);
                    editedItem.getItemProperty("Wartość brutto").setValue(priceGrossTotality);
                    editedItem.getItemProperty("Marża").setValue(marginPercent);
                    editedItem.getItemProperty("Cena").setValue(priceMargin);
                    Long catTmpId = categoryTmpRep.getIdByBarcode(previousBarcode);
                    categoryTmpRep.editOneByBarcode(catTmpId, barcode, categoryTmp.getMainCategory(),
                            categoryTmp.getType1(), categoryTmp.getType2(), categoryTmp.getType3(),
                            categoryTmp.getColor(), categoryTmp.getColorNr(), categoryTmp.getEmpty1(),
                            categoryTmp.getEmpty2(), categoryTmp.getSize(), categoryTmp.getThickness(),
                            categoryTmp.getDesignation(), categoryTmp.getWidth(), categoryTmp.getLength(),
                            categoryTmp.getWeight(), categoryTmp.getLengthInt());
                    saveItem(previousBarcode, categoryTxt, categoryTmp);
                    previousBarcode = null;
                    editedItem = null;
                    //categoryTxt = "Kategoria: ";
                } else {
                    Item newItem = container.getItem(container.addItem());
                    newItem.getItemProperty("KodKreskowy").setValue(barcode);
                    newItem.getItemProperty("NrKatalogowy").setValue(catalogNumber);
                    newItem.getItemProperty("NazwaArtykułu").setValue(articleName);
                    newItem.getItemProperty("Ilość").setValue(quantity);
                    newItem.getItemProperty("JM").setValue(unitOfMeasurement);
                    newItem.getItemProperty("Rabat").setValue(discountPercent);
                    newItem.getItemProperty("VAT").setValue(taxPercent);
                    newItem.getItemProperty("Wartość brutto").setValue(priceGrossTotality);
                    newItem.getItemProperty("Marża").setValue(marginPercent);
                    newItem.getItemProperty("Cena").setValue(priceMargin);
                    categoryTmp.setBarcode(barcode);
                    categoryTmpRep.save(categoryTmp);
                    saveItem(barcode, categoryTxt, categoryTmp);
                }
                gridTable.getSelectionModel().reset();
                listArticles();
                Notification.show("Dodanie artykułu" + barcode + " przebiegło pomyślnie.", Notification.Type.TRAY_NOTIFICATION);
            }
        });

        butChooseArticle.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (!tfBarcode.getValue().equals("")){
                    String barcode = tfBarcode.getValue();
                    Articles queryContent = articlesRep.findOneByBarcode(barcode);
                    if(queryContent == null) {
                        Notification.show("Nie znaleziono artykułu o podanym kodzie kreskowym.", Notification.Type.ERROR_MESSAGE);
                    } else {
                        // otwarcie subokna z tabelką artykułów o tym samym kodzie kreskowym
                        List<Articles> content = new ArrayList<>();
                        content.add(queryContent);
                        chooseArticleSubWin.init(content);
                        chooseArticleSubWin.setHeight("500px");
                        chooseArticleSubWin.setWidth("1000px");
                        UI.getCurrent().addWindow(chooseArticleSubWin);
                    }
                } else if(!tfCatalogNr.getValue().equals("")) {
                    String catalogNr = tfCatalogNr.getValue();
                    List<Articles> queryContent = articlesRep.findByCatalogNumber(catalogNr);
                    if(queryContent == null) {
                        Notification.show("Nie znaleziono artykułu o podanym numerze katalogowym", Notification.Type.ERROR_MESSAGE);
                    } else {
                        // otwarcie subokna z tabelką artykułów o tym samym numerze katalogowym
                        chooseArticleSubWin.init(queryContent);
                        chooseArticleSubWin.setHeight("500px");
                        chooseArticleSubWin.setWidth("1000px");
                        UI.getCurrent().addWindow(chooseArticleSubWin);
                    }
                } else if(tfCatalogNr.getValue().equals("") && tfBarcode.getValue().equals("")) {
                    Notification.show("Najpierw wprowadź numer katalogowy lub kod kreskowy.", Notification.Type.ERROR_MESSAGE);

                }
            }
        });

        // listener sprawdzajacy czy subokno wybierania artykulu zostalo zamkniete
        chooseArticleSubWin.addCloseListener(e -> {
            if(chooseArticleSubWin.getIsAccept()) {
                System.out.println("4a");
                gridTable.getSelectionModel().reset();
                barcode = chooseArticleSubWin.getBarcode();
                catalogNumber = chooseArticleSubWin.getCatalogNr();
                articleName = chooseArticleSubWin.getArticleName();
                quantity = chooseArticleSubWin.getQuantity();
                unitOfMeasurement = chooseArticleSubWin.getUnitOfMeasurement();
                discountPercent = chooseArticleSubWin.getDiscountPercent();
                taxPercent = chooseArticleSubWin.getTaxPercent();
                priceGrossTotality = chooseArticleSubWin.getPriceMargin().multiply(chooseArticleSubWin.getQuantity());
                marginPercent = chooseArticleSubWin.getMarginPercent();
                priceMargin = chooseArticleSubWin.getPriceMargin();
                String categoryTxt = chooseArticleSubWin.getCategoryTxt();

                ComponentOfInvoice c = new ComponentOfInvoice(
                        priceGrossTotality, quantity,
                        new BigDecimal(chooseArticleSubWin.getDiscountPercent()),
                        new BigDecimal(chooseArticleSubWin.getTaxPercent()),
                        new BigDecimal(chooseArticleSubWin.getMarginPercent()));

                priceNetWithoutDiscount = c.getPriceNetWithoutDiscount();
                priceNetPcs = c.getPriceNetPcs();
                priceGrossPcs = c.getPriceGrossPcs();
                valueNet = c.getValueNet();
                valueVat = c.getValueVat();

                saveItem(barcode, categoryTxt,null);

                Item newItem = container.getItem(container.addItem());
                newItem.getItemProperty("KodKreskowy").setValue(barcode);
                newItem.getItemProperty("NrKatalogowy").setValue(catalogNumber);
                newItem.getItemProperty("NazwaArtykułu").setValue(articleName);
                newItem.getItemProperty("Ilość").setValue(quantity);
                newItem.getItemProperty("JM").setValue(unitOfMeasurement);
                newItem.getItemProperty("Rabat").setValue(discountPercent);
                newItem.getItemProperty("VAT").setValue(taxPercent);
                newItem.getItemProperty("Wartość brutto").setValue(priceGrossTotality);
                newItem.getItemProperty("Marża").setValue(marginPercent);
                newItem.getItemProperty("Cena").setValue(priceMargin);
                listArticles();

                Notification.show("Dodanie artykułu " + barcode + " przebiegło pomyślnie.", Notification.Type.TRAY_NOTIFICATION);
            }
        });

        butAddNewItems.addClickListener(event -> {
            //przenoszenie danych z InvoiceSupplyTmp do InvoiceSupply
            InvoicesSupplyTmp ist = invoicesSupplyTmpRep.findOne(1L);
            InvoicesSupply is = new InvoicesSupply(
                    null,
                    ist.getInvoiceNr(),
                    ist.getWholesale(),
                    HelpfulMethods.convertStringToDate("data"),
                    ist.getDateOfIssue(),
                    null
            );
            invoicesSupplyRep.save(is);

            //przenoszenie danych z encji ArticlesTmp do encji Articles
            List<ArticlesTmp> tmpArticles = articlesTmpRep.findAll();
            for (ArticlesTmp row : tmpArticles) {
                Articles articlesOfWarehouse = articlesRep.findOneByBarcode(row.getBarcode());
                if(articlesOfWarehouse == null) {
                    //jesli artykul o podanym barcode nie istnieje w bazie
                    Articles a = new Articles(
                            null,
                            row.getBarcode(),
                            row.getCatalogNumber(),
                            row.getArticleName(),
                            row.getQuantity(),
                            row.getUnitOfMeasurement(),
                            row.getPriceNetWithoutDiscount(),
                            row.getDiscountPercent(),
                            row.getPriceNetPcs(),
                            row.getPriceGrossPcs(),
                            row.getValueNet(),
                            row.getTaxPercent(),
                            row.getValueVat(),
                            row.getPriceGrossTotality(),
                            hm.convertStringToDate("data"),
                            row.getMarginPercent(),
                            row.getPriceMargin(),
                            row.getCategoryTxt(),
                            null,
                            null,
                            null,
                            null,
                            null
                    );
                    articlesRep.save(a);
                    ArticlesAdditionalInfo aai = new ArticlesAdditionalInfo(
                            null,
                            row.getQuantity(),
                            hm.convertStringToDate("data"),
                            a,
                            is
                    );
                    articlesAdditionalInfoRep.save(aai);
                } else {
                    //jesli istnieje taki artykul w bazie
                    BigDecimal oldQuantity = articlesOfWarehouse.getQuantity();
                    articlesRep.editOneByBarcode(row.getBarcode(), oldQuantity.add(row.getQuantity()));
                }
            }

            //przenoszenie danych z CategoryTmp do Category
            List<CategoryTmp> categoryList = categoryTmpRep.findAll();
            for (CategoryTmp row : categoryList) {
                Articles article = articlesRep.findOneByBarcode(row.getBarcode()); //po kolumnie Barcode pobiera obiekt i zapisuje go do opiektu konkretnej kategorii poniżej
                Category category = new Category(null, row.getBarcode(), row.getMainCategory(),
                        row.getType1(),row.getType2(),row.getType3(),row.getColor(),
                        row.getColorNr(),row.getEmpty1(),row.getEmpty2(),row.getSize(),
                        row.getThickness(),row.getDesignation(),row.getWidth(),row.getLength(),
                        row.getWeight(), row.getLengthInt(), article);
                categoryRep.save(category);
            }

            //czyszczenie encji zawierajacych dane tymczasowe
            invoicesSupplyTmpRep.deleteAll();
            categoryTmpRep.deleteAll();
            articlesTmpRep.deleteAll();
            articlesOcrTmpRep.deleteAll();

            //zapisanie strony postepu
            staticVariablesRep.editOneByName("saveofrosm",0);

            InformationSubWin informationSubWin = new InformationSubWin();
            String info = "Towar został przyjęty.";
            String caption = "Informacja";
            informationSubWin.init(caption, info);
            UI.getCurrent().addWindow(informationSubWin);
            informationSubWin.setModal(true);

            informationSubWin.addCloseListener(e -> {
                JavaScript.eval("close()");
                getUI().close();
            });
        });

        butCancelNewItems.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                //subokno z opcja wyboru wyjscia z zapisaniem lub bez
                String info = "Wybierz sposób w jaki chcesz przerwać wprowadzanie towaru. ";
                String caption = "Informacja";
                String nameOfBut1 = "Anuluj";
                String nameOfBut2 = "Zamknij bez zapisywania";
                String nameOfBut3 = "Zapisz i zamknij";
                selection3OptionsSubWin.init(nameOfBut1, nameOfBut2, nameOfBut3, caption, info);
                UI.getCurrent().addWindow(selection3OptionsSubWin);
                selection3OptionsSubWin.setModal(true);
                selection3OptionsSubWin.setWidth("700px");
                selection3OptionsSubWin.setHeightUndefined();
                selection3OptionsSubWin.setClosable(false);
            }
        });

        selection3OptionsSubWin.addCloseListener(e -> {
            if (selection3OptionsSubWin.getSelectedOption() == 1) {
                //nic nie zostanie zapisane
                invoicesSupplyTmpRep.deleteAll();
                categoryTmpRep.deleteAll();
                articlesTmpRep.deleteAll();
                articlesOcrTmpRep.deleteAll();
                staticVariablesRep.editOneByName("saveofrosm",0);
                staticVariablesRep.editOneByName("rosmissaved",0);
                JavaScript.eval("close()");
                getUI().close();
            } else if(selection3OptionsSubWin.getSelectedOption() == 2) {
                //wprowadzanie towaru zostanie wznowione od widoku ROSA3
                staticVariablesRep.editOneByName("saveofrosm",2);
                staticVariablesRep.editOneByName("rosmissaved",1);
                JavaScript.eval("close()");
                getUI().close();
            }
        });


    }

    void addNewItemToContainer(ArticlesTmp row) {
        Item newItem = container.getItem(container.addItem());
        newItem.getItemProperty("KodKreskowy").setValue(row.getBarcode());
        newItem.getItemProperty("NrKatalogowy").setValue(row.getCatalogNumber());
        newItem.getItemProperty("NazwaArtykułu").setValue(row.getArticleName());
        newItem.getItemProperty("Ilość").setValue(row.getQuantity());
        newItem.getItemProperty("JM").setValue(row.getUnitOfMeasurement());
        newItem.getItemProperty("Rabat").setValue(row.getDiscountPercent());
        newItem.getItemProperty("VAT").setValue(row.getTaxPercent());
        newItem.getItemProperty("Wartość brutto").setValue(row.getPriceGrossTotality());
        newItem.getItemProperty("Marża").setValue(row.getMarginPercent());
        newItem.getItemProperty("Cena").setValue(row.getPriceMargin());
    }

    void listArticles() {
        gridTable.setContainerDataSource(container);
    }

    private void findAndCheckArticle(String catalogNr) {
        //szukaj w bazie artykułu o podanym CatalogNumber
        List<Articles> queryContent = articlesRep.findByCatalogNumber(catalogNr);
        if (queryContent != null) {
            if (queryContent.size() == 1) {
                quantityRowsFromQuery = 1;
                //queryContent = content;
                /*
                for (int i = 0; i < arg.length; i++) {
                    tfBarcode.setValue(arg[0].toString());
                    tfArticleName.setValue(arg[2].toString());
                    tfUnitOfMeasurement.setValue(arg[3].toString());
                    tfDiscountPercent.setValue(arg[4].toString());
                    tfTaxPercent.setValue(arg[5].toString());
                    tfMarginPercent.setValue(arg[6].toString());

                    tfBarcode.setEnabled(false);
                    tfArticleName.setEnabled(false);
                    tfUnitOfMeasurement.setEnabled(false);
                    tfDiscountPercent.setEnabled(false);
                    tfTaxPercent.setEnabled(false);
                    tfMarginPercent.setEnabled(false);

                    butChooseArticle.setEnabled(false);
                    */
            } else if (queryContent.size() > 1) {
                quantityRowsFromQuery = queryContent.size();
                //queryContent = content;
            }

        } else {
            quantityRowsFromQuery = 0;

            // komunikat że nie ma produktu o podanym CatalogNumber w bazie
            // (komunikat w rogu okna - coś jak powiadomienia)
        }
    }

    private void saveItem(String itemBarcode, String ctt, CategoryTmp ct) {
        Long newSuppId = articlesTmpRep.existByBarcode(itemBarcode);
        if (newSuppId == null) {
            //dodaj nowy
            ArticlesTmp articlesTmp = new ArticlesTmp(null, barcode, catalogNumber,
                    articleName, quantity, unitOfMeasurement, priceNetWithoutDiscount,
                    discountPercent, priceNetPcs, priceGrossPcs, valueNet, taxPercent, valueVat,
                    priceGrossTotality, marginPercent, priceMargin, ctt, ct);
            articlesTmpRep.save(articlesTmp);
        } else if (newSuppId != null) {
            //edytuj istniejacy
            articlesTmpRep.editOneById(newSuppId, barcode,
                    articleName, quantity, unitOfMeasurement, priceNetWithoutDiscount,
                    discountPercent, priceNetPcs, priceGrossPcs, valueNet, taxPercent, valueVat,
                    priceGrossTotality, marginPercent, priceMargin, ctt);
        }
        //jesli istnieje taki artylul to usunac go z ArticlesOcrTmpRep
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }


}
