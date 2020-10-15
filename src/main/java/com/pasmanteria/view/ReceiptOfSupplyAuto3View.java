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
import java.util.List;

/**
 * Created by adrian on 03.01.2017.
 */
//@SpringUI(path = "/newItemAutoUI")
@SpringView(name = ReceiptOfSupplyAuto3View.VIEW_NAME)
@Theme("valo")
public class ReceiptOfSupplyAuto3View extends VerticalLayout implements View { //extends UI {
    public static final String VIEW_NAME = "ROSA3";

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

    //private VerticalLayout mainLayout;
    private HorizontalLayout gridOptionsLayout;
    private HorizontalLayout buttonsLayout;
    private HorizontalLayout hlBanner;
    private Image imgHeader;

    private Label labInvoiceName;

    private Button butChooseArticle;
    private Button butCancelNewItems;
    private Button butAddNewItems; // przycisk akceputujacy dodatnie towaru do sklepu
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
    private AddNewArticleSubWin addNewArticleSubWin;
    private ChooseArticleSubWin chooseArticleSubWin;
    private Selection3OptionsSubWin selection3OptionsSubWin;
    private InformationSubWin informationSubWin;

    private int indexEditedField;
    private String previousBarcode;
    Item editedItem;
    Item selectItem;

    private List<Articles> queryContent;

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    @Autowired
    public ReceiptOfSupplyAuto3View(ArticlesTmpRep articlesTmpRep,
                                    ArticlesRep articlesRep,
                                    CategoryTmpRep categoryTmpRep,
                                    CategoryRep categoryRep,
                                    InvoicesSupplyRep invoicesSupplyRep,
                                    InvoicesSupplyTmpRep invoicesSupplyTmpRep,
                                    ArticlesOcrTmpRep articlesOcrTmpRep,
                                    StaticVariablesRep staticVariablesRep,
                                    ArticlesAdditionalInfoRep articlesAdditionalInfoRep) {
        this.articlesTmpRep = articlesTmpRep;
        this.articlesRep = articlesRep;
        this.categoryTmpRep = categoryTmpRep;
        this.categoryRep = categoryRep;
        this.invoicesSupplyRep = invoicesSupplyRep;
        this.invoicesSupplyTmpRep = invoicesSupplyTmpRep;
        this.articlesOcrTmpRep = articlesOcrTmpRep;
        this.staticVariablesRep = staticVariablesRep;
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
        buttonsLayout = new HorizontalLayout();
        hlBanner = new HorizontalLayout();

        labInvoiceName = new Label("Nr faktury itd");

        //butAddCategory = new Button("Dodaj kategorie");
        butChooseArticle = new Button("Wybierz artykuł");
        butCancelNewItems = new Button("Anuluj przyjęcie towaru");
        butAddNewItems = new Button("Dodaj towar do sklepu");
        butDeleteSelected = new Button("Usuń zaznaczone");
        butEditSelected = new Button("Edytuj zaznaczone");
        butAddNewItem = new Button("Dodaj nowy / Edytuj");

        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/banners/automatyczne_dodawanie_towaru_m.png"));
        imgHeader = new Image("",resource);

        addNewArticleSubWin = new AddNewArticleSubWin();
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
        container.addContainerProperty("Cena", BigDecimal.class, 0.0); //priceMargin


        //
        hlBanner.addComponent(imgHeader);
        gridOptionsLayout.addComponents(butDeleteSelected, butChooseArticle, butAddNewItem/*, butEditSelected*/);
        buttonsLayout.addComponents(butCancelNewItems, butAddNewItems);
        addComponents(hlBanner, gridTable, gridOptionsLayout,  buttonsLayout);

        // Set
        //setContent(mainLayout);

        this.setMargin(true);
        this.setSpacing(true);

        butCancelNewItems.addStyleName("danger");
        butAddNewItems.addStyleName("friendly");

        gridTable.setHeight(100, Unit.PERCENTAGE);
        gridTable.setWidth(100, Unit.PERCENTAGE);
        gridTable.setSelectionMode(Grid.SelectionMode.MULTI);

        selection = (Grid.MultiSelectionModel) gridTable.getSelectionModel();

        gridOptionsLayout.setSpacing(true);

        hlBanner.setComponentAlignment(imgHeader, Alignment.MIDDLE_CENTER);
        hlBanner.setWidth(100, Unit.PERCENTAGE);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancelNewItems, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butAddNewItems, Alignment.MIDDLE_CENTER);

        butDeleteSelected.setEnabled(gridTable.getSelectedRows().size() > 0);
        //butAddCategory.setEnabled(grid.getSelectedRows().size() == 1);
        butChooseArticle.setEnabled(gridTable.getSelectedRows().size() == 1);
        butEditSelected.setEnabled(gridTable.getSelectedRows().size() == 1);
        butAddNewItem.setEnabled(gridTable.getSelectedRows().size() == 1);

        // end Set

        //odczytywanie zawartosci encji ArticlesOcrTmpRep, sprawdzanie czy produkt juz istnieje w bazie
        // i dodanie go to gida w odpowiednim kolorze
        List<ArticlesOcrTmp> queryContent = articlesOcrTmpRep.findAll();
        for (ArticlesOcrTmp row : queryContent) {
            //sprawdz jaki jest catalogNr
            findAndCheckArticle(row.getCatalogNumber());
            if(quantityRowsFromQuery == 0) {
                ;
                //daj na czerwono bo artykulu nie ma w bazie
            } else if(quantityRowsFromQuery == 1) {
                ;
                //daj na zolto w celu uzupenienia pol
            } else if(quantityRowsFromQuery > 1) {
                ;
                //daj na zolto bo trzeba wybrac artykul z listy
            }
            addNewItemToContainer1(row);
        }
        listArticles();

        //dodawanie artykulow z komletnymi informacjami (zaczytanie na nowo np. po odświeżeniu strony lub gdy proces wprowadzania towaru został przerwany)
        //wszystkie te wiersze w gridzie powinny byc zielone
        List<ArticlesTmp> content = articlesTmpRep.findAll();
        if(content != null) {
            for (ArticlesTmp row : content) {
                addNewItemToContainer2(row);
                //ustaw wiersz na zielono
            }
        }
        listArticles();

        //sprawdzam czy istnieje rozpoczeta sesja automatycznego dodawania towaru
        int rosaIsSaved = staticVariablesRep.findByName("rosaissaved").getValue();
        if (rosaIsSaved == 1) {
            informationSubWin = new InformationSubWin();
            String info = "Wczytano zapisana sesje automatycznego dodawania towaru.";
            String caption = "Informacja";
            informationSubWin.init(caption, info);
            UI.getCurrent().addWindow(informationSubWin);
            informationSubWin.setModal(true);
        }

        // Listeners
        butDeleteSelected.addClickListener(event -> {
            for (Object itemId : selection.getSelectedRows()) {
                //tutaj dodać usuniecie z bazy
                gridTable.getContainerDataSource().removeItem(itemId);
            }
            gridTable.getSelectionModel().reset();
            butDeleteSelected.setEnabled(false);
        });

        gridTable.addSelectionListener(select -> { // Java 8
            // Allow deleting only if there's any selected
            butDeleteSelected.setEnabled(gridTable.getSelectedRows().size() > 0);
            butAddNewItem.setEnabled(gridTable.getSelectedRows().size() == 1);
            butEditSelected.setEnabled(gridTable.getSelectedRows().size() == 1);
            butChooseArticle.setEnabled(gridTable.getSelectedRows().size() == 1);
        });

        //otwiera okno edycji wybranego artykulu
        butAddNewItem.addClickListener(event -> {
            if (gridTable.getSelectedRows().size() == 1) {
                for (Object itemId : selection.getSelectedRows())
                    editedItem = container.getItem(itemId);
                previousBarcode = editedItem.getItemProperty("KodKreskowy").getValue().toString();
                String ctt = articlesTmpRep.getCategoryTxtByBarcode(previousBarcode);
                ctt = (ctt == null) ? "nie przypisano" : ctt;
                //CategoryTmp ct = articlesTmpRep.getCategoryIdByBarcode(previousBarcode);
                String newBarcode;
                Articles a1;
                ArticlesTmp at1;
                do {
                    newBarcode = BarcodeGenerator.generateBarcode();
                    a1 = articlesRep.findOneByBarcode(newBarcode);
                    at1 = articlesTmpRep.findOneByBarcode(newBarcode);
                } while(a1 != null || at1 != null);
                addNewArticleSubWin.init(
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
                addNewArticleSubWin.setHeight("350px");
                addNewArticleSubWin.setWidth("700px");
                UI.getCurrent().addWindow(addNewArticleSubWin);
            } else {
                Notification.show("Zaznacz jeden wiersz, który chcesz edytować.", Notification.Type.WARNING_MESSAGE);
            }
        });

        // listener sprawdzajacy czy subokno edycji artykulu zostalo zamkniete
        addNewArticleSubWin.addCloseListener(event -> {
            if(addNewArticleSubWin.getIsAccept()) {

                gridTable.getSelectionModel().reset();
                barcode = addNewArticleSubWin.getBarcode();
                catalogNumber = addNewArticleSubWin.getCatalogNr();
                articleName = addNewArticleSubWin.getArticleName();
                quantity = addNewArticleSubWin.getQuantity();
                unitOfMeasurement = addNewArticleSubWin.getUnitOfMeasurement();
                discountPercent = addNewArticleSubWin.getDiscountPercent();
                taxPercent = addNewArticleSubWin.getTaxPercent();
                priceGrossTotality = addNewArticleSubWin.getPriceGrossTotality();
                marginPercent =  addNewArticleSubWin.getMarginPercent();
                priceMargin = addNewArticleSubWin.getPriceMargin();

                CategoryTmp categoryTmp = addNewArticleSubWin.getCategoryTmp();
                categoryTmp.setBarcode(barcode);
                String categoryTxt = addNewArticleSubWin.getCategoryTxt();

                ComponentOfInvoice c = new ComponentOfInvoice(
                        priceGrossTotality, quantity,
                        new BigDecimal(addNewArticleSubWin.getDiscountPercent()),
                        new BigDecimal(addNewArticleSubWin.getTaxPercent()),
                        new BigDecimal(addNewArticleSubWin.getMarginPercent()));

                priceNetWithoutDiscount = c.getPriceNetWithoutDiscount();
                priceNetPcs = c.getPriceNetPcs();
                priceGrossPcs = c.getPriceGrossPcs();
                valueNet = c.getValueNet();
                valueVat = c.getValueVat();

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

                gridTable.getSelectionModel().reset();
                //listArticles();
                categoryTmpRep.save(categoryTmp); //upewnic sie czy na pewno nie trzeba podmieniac jesli bylo edytowane
                saveItem(previousBarcode, categoryTxt, categoryTmp);
                Notification.show("Dodanie artykułu " + barcode + " przebiegło pomyślnie.", Notification.Type.TRAY_NOTIFICATION);
            }
        });

        butChooseArticle.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                // wystąpienie innego warunku zostało wykonane przez blokadę przycisków,
                // jednak tutaj zastosowałem podwójne zabezpieczenie
                if(gridTable.getSelectedRows().size() == 1 /* && produkt liczny */) {
                    for (Object itemId : selection.getSelectedRows())
                        selectItem = container.getItem(itemId);
                    String itemCatalogNr = selectItem.getItemProperty("NrKatalogowy").getValue().toString();
                    previousBarcode = selectItem.getItemProperty("KodKreskowy").getValue().toString();
                    List<Articles> queryContent = articlesRep.findByCatalogNumber(itemCatalogNr);
                    // otwarcie subokna z tabelką artykułów o tym samym numerze katalogowym
                    chooseArticleSubWin.init(queryContent);
                    chooseArticleSubWin.setHeight("500px");
                    chooseArticleSubWin.setWidth("1000px");
                    UI.getCurrent().addWindow(chooseArticleSubWin);
                } else {
                    // aby wybrać lub edytować artykuł musi być zaznaczony tylko jeden wiersz
                }
            }
        });

        // listener sprawdzajacy czy subokno wybierania artykulu zostalo zamkniete
        chooseArticleSubWin.addCloseListener(event -> {
            if(chooseArticleSubWin.getIsAccept()) {
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

                selectItem.getItemProperty("KodKreskowy").setValue(barcode);
                //selectItem.getItemProperty("NrKatalogowy").setValue(catalogNumber);
                selectItem.getItemProperty("NazwaArtykułu").setValue(articleName);
                selectItem.getItemProperty("Ilość").setValue(quantity);
                selectItem.getItemProperty("JM").setValue(unitOfMeasurement);
                selectItem.getItemProperty("Rabat").setValue(discountPercent);
                selectItem.getItemProperty("VAT").setValue(taxPercent);
                selectItem.getItemProperty("Wartość brutto").setValue(priceGrossTotality);
                selectItem.getItemProperty("Marża").setValue(marginPercent);
                selectItem.getItemProperty("Cena").setValue(priceMargin);
                gridTable.getSelectionModel().reset();
                //listArticles();

                Notification.show("Dodanie artykułu" + barcode + " przebiegło pomyślnie.", Notification.Type.TRAY_NOTIFICATION);
            }
        });

        //dodanie towaru do sklepu
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
            staticVariablesRep.editOneByName("saveofrosa",0);

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
                staticVariablesRep.editOneByName("saveofrosa",0);
                staticVariablesRep.editOneByName("rosaissaved",0);
                JavaScript.eval("close()");
                getUI().close();
            } else if(selection3OptionsSubWin.getSelectedOption() == 2) {
                //wprowadzanie towaru zostanie wznowione od widoku ROSA3
                staticVariablesRep.editOneByName("saveofrosa",3);
                staticVariablesRep.editOneByName("rosaissaved",1);
                JavaScript.eval("close()");
                getUI().close();
            }
        });

    }
    void listArticles() {
        gridTable.setContainerDataSource(container);
    }

    void addNewItemToContainer1(ArticlesOcrTmp row) {
        Item newItem = container.getItem(container.addItem());
        newItem.getItemProperty("KodKreskowy").setValue(row.getBarcode());
        newItem.getItemProperty("NrKatalogowy").setValue(row.getCatalogNumber());
        newItem.getItemProperty("NazwaArtykułu").setValue(row.getArticleName());
        newItem.getItemProperty("Rabat").setValue(row.getDiscountPercent());
        newItem.getItemProperty("VAT").setValue(row.getTaxPercent());
        newItem.getItemProperty("Wartość brutto").setValue(row.getPriceGrossTotality());
    }

    void addNewItemToContainer2(ArticlesTmp row) {
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


    private void findAndCheckArticle(String catalogNr) {
        //szukaj w bazie artykułu o podanym CatalogNumber
        System.out.println("CatalogNumbver przekazany do funkcji: " + catalogNr);
        List<Articles> content = articlesRep.findByCatalogNumber(catalogNr);
        System.out.println("Wynik zapytania: " + content.size());
        //System.out.println("catalogNr: " + content.get(0).getCatalogNumber());
        if (content.size() == 0) {
            // komunikat że nie ma produktu o podanym CatalogNumber w bazie
            // (komunikat w rogu okna - coś jak powiadomienia)
            quantityRowsFromQuery = 0;
        } else if (content.size() == 1) {
            quantityRowsFromQuery = 1;
                queryContent = content;
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
                } else if (content.size() > 1) {
                    quantityRowsFromQuery = content.size();
                    queryContent = content;
                }

        System.out.println("ilosc wierszy: " + quantityRowsFromQuery);
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
            //usuwanie artykulo z ArticlesOcrTmpRep bo zostal przeniesiony do Articles
            Long newInvOcrTempId = articlesOcrTmpRep.existByBarcode(itemBarcode);
            articlesOcrTmpRep.delete(newInvOcrTempId);
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
