package com.pasmanteria.view;

import com.pasmanteria.Filess;
import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.LosowanieBezPowtorzen;
import com.pasmanteria.model.ArticlesOcrTmp;
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
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by adrian on 08.05.2017.
 */

//@SpringUI(path = "/newInvoiceOcrAuto")
@SpringView(name = ReceiptOfSupplyAuto2View.VIEW_NAME)
@Theme("valo")
public class ReceiptOfSupplyAuto2View extends VerticalLayout implements View {//extends UI {
    public static final String VIEW_NAME = "ROSA2";

    //private VerticalLayout mainLayout;
    private HorizontalLayout optionsLayout;
    private HorizontalLayout hlConfirmButtons;
    private HorizontalLayout checkBoxLayout;
    private HorizontalLayout editButtonsLayout;
    private HorizontalLayout hlBanner;
    private Image imgHeader;

    private Grid gridTable;
    private Grid.MultiSelectionModel selectTable;
    private IndexedContainer containerTable;
    private IndexedContainer containerTableTemp;


    private Button butDeleteSelected;
    private Button butCancelNewInvoice;
    private Button butAddNewInvoice;
    private Button butMergeCells;
    private Button butRemoveCell;
    private Button butAddNewCell;
    private Button butEditRow;

    private HelpfulMethods hm;
    private Filess filess = new Filess();
    private LosowanieBezPowtorzen losowanieBezPowtorzen = new LosowanieBezPowtorzen(1000);
    private ArticlesOcrTmpRep articlesOcrTmpRep;
    private StaticVariablesRep staticVariablesRep;
    private InvoicesSupplyTmpRep invoicesSupplyTmpRep;
    private CategoryTmpRep categoryTmpRep;
    private ArticlesTmpRep articlesTmpRep;

    private CheckBox cbCatalogNr;
    private CheckBox cbArticleName;
    private CheckBox cbDiscountPercent;
    private CheckBox cbTaxPercent;
    private CheckBox cbPriceGrossTotality;

    private EditArticleSubWin editArticleSubWin;
    private Selection3OptionsSubWin selection3OptionsSubWin;
    private InformationSubWin informationSubWin;


    private int indexEditedField;

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    @Autowired
    public ReceiptOfSupplyAuto2View(ArticlesOcrTmpRep articlesOcrTmpRep,
                                    StaticVariablesRep staticVariablesRep,
                                    InvoicesSupplyTmpRep invoicesSupplyTmpRep,
                                    CategoryTmpRep categoryTmpRep,
                                    ArticlesTmpRep articlesTmpRep) {
        this.articlesOcrTmpRep = articlesOcrTmpRep;
        this.staticVariablesRep = staticVariablesRep;
        this.invoicesSupplyTmpRep = invoicesSupplyTmpRep;
        this.categoryTmpRep = categoryTmpRep;
        this.articlesTmpRep = articlesTmpRep;
        gridTable = new Grid();
        containerTable = new IndexedContainer();
        containerTableTemp = new IndexedContainer();
        hm = new HelpfulMethods();
        selection3OptionsSubWin = new Selection3OptionsSubWin();
        informationSubWin = new InformationSubWin();
    }

    @PostConstruct
    void init() {
        // Create
        //mainLayout = new VerticalLayout();
        optionsLayout = new HorizontalLayout();
        hlConfirmButtons = new HorizontalLayout();
        checkBoxLayout = new HorizontalLayout();
        editButtonsLayout = new HorizontalLayout();
        hlBanner = new HorizontalLayout();

        editArticleSubWin = new EditArticleSubWin();

        butCancelNewInvoice = new Button("Anuluj przyjęcie towaru");
        butAddNewInvoice = new Button("Dalej");
        butDeleteSelected = new Button("Usuń zaznaczony wiersz");
        butMergeCells = new Button("Scal zaznaczone komórki");
        butRemoveCell = new Button("Usuń zaznaczone komórki");
        butAddNewCell = new Button("Dodaj nową komórkę powyżej");
        butEditRow = new Button("Edytuj zaznaczony wiersz");

        cbCatalogNr = new CheckBox("Nr katalogowy", false);
        cbArticleName = new CheckBox("Nazwa artykułu", false);
        cbDiscountPercent = new CheckBox("Rabat", false);
        cbTaxPercent = new CheckBox("VAT", false);
        cbPriceGrossTotality = new CheckBox("Wartość brutto", false);

        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/banners/automatyczne_dodawanie_towaru_m.png"));
        imgHeader = new Image("",resource);

        // Add
        containerTable.addContainerProperty("NrKatalogowy", String.class, ""); //catalogNr
        containerTable.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
        containerTable.addContainerProperty("Rabat", String.class, 0.0f); //discountPercent
        containerTable.addContainerProperty("VAT", String.class, 0.0f); //taxPercent
        containerTable.addContainerProperty("Wartość brutto", String.class, 0.0); //priceGrossTotality

        hlBanner.addComponent(imgHeader);
        optionsLayout.addComponents(butEditRow, butDeleteSelected);
        hlConfirmButtons.addComponents(butCancelNewInvoice, butAddNewInvoice);
        checkBoxLayout.addComponents(cbCatalogNr, cbArticleName, cbDiscountPercent, cbTaxPercent, cbPriceGrossTotality);
        editButtonsLayout.addComponents(butMergeCells, butRemoveCell/*, butAddNewCell*/); //ten przycisk chyba nie bdzie ptrzebny, na koniec zecydowac o jego losie
        addComponents(hlBanner, gridTable, checkBoxLayout, editButtonsLayout, optionsLayout, hlConfirmButtons);

        // Set
        //setContent(mainLayout);

        hlBanner.setComponentAlignment(imgHeader, Alignment.MIDDLE_CENTER);
        hlBanner.setWidth(100, Unit.PERCENTAGE);

        butCancelNewInvoice.addStyleName("danger");
        butAddNewInvoice.addStyleName("friendly");

        this.setMargin(true);
        this.setSpacing(true);

        gridTable.setHeight(100, Unit.PERCENTAGE);
        gridTable.setWidth(100, Unit.PERCENTAGE);
        gridTable.setSelectionMode(Grid.SelectionMode.MULTI);

        selectTable = (Grid.MultiSelectionModel) gridTable.getSelectionModel();
        optionsLayout.setSpacing(true);
        editButtonsLayout.setSpacing(true);
        checkBoxLayout.setSpacing(true);

        hlConfirmButtons.setMargin(true);
        hlConfirmButtons.setSpacing(true);
        hlConfirmButtons.setWidth(100, Unit.PERCENTAGE);
        hlConfirmButtons.setComponentAlignment(butCancelNewInvoice, Alignment.MIDDLE_CENTER);
        hlConfirmButtons.setComponentAlignment(butAddNewInvoice, Alignment.MIDDLE_CENTER);
/*
        butEditRow.setEnabled(gridTable.getSelectedRows().size() == 1);
        butDeleteSelected.setEnabled(gridTable.getSelectedRows().size() > 0);
        butMergeCells.setEnabled((gridTable.getSelectedRows().size() > 1) && (isCheckBoxSelected()));
        butAddNewCell.setEnabled((gridTable.getSelectedRows().size() == 1) && (isCheckBoxSelected()));
        butRemoveCell.setEnabled((gridTable.getSelectedRows().size() > 0) && (isCheckBoxSelected()));
*/
        // end Set

        //odczytaj wszystkie dane z pliku
        String[][] tab = filess.readCsv(".\\invoice_data\\faktura_ocr.csv");

        for(int i = 0; i < tab.length; i++) {
            Item newItem = containerTable.getItem(containerTable.addItem());
            newItem.getItemProperty("NrKatalogowy").setValue(tab[i][0]);
            newItem.getItemProperty("NazwaArtykułu").setValue(tab[i][1]);
            newItem.getItemProperty("Rabat").setValue(tab[i][2]);
            newItem.getItemProperty("VAT").setValue(tab[i][3]);
            newItem.getItemProperty("Wartość brutto").setValue(tab[i][4]);

        }
        listArticles();

        //sprawdzam czy istnieje rozpoczeta sesja automatycznego dodawania towaru
        int rosaIsSaved = staticVariablesRep.findByName("rosaissaved").getValue();
        if (rosaIsSaved == 1) {
            String info = "Wczytano zapisana sesje automatycznego dodawania towaru.";
            String caption = "Informacja";
            informationSubWin.init(caption, info);
            UI.getCurrent().addWindow(informationSubWin);
            informationSubWin.setModal(true);
        }
/*
        informationSubWin.addCloseListener(e -> {
            JavaScript.eval("close()");
            getUI().close();
        });
*/
        gridTable.addSelectionListener(select -> { // Java 8
            Notification.show(select.getAdded().size() +
                    " zaznaczono, " +
                    select.getRemoved().size() +
                    " odznaczono.");
/*
            //odblokuj przycisk jesli dopowiedni warunek jest spelniony
            if(gridTable.getSelectedRows().size() > 0){
                butDeleteSelected.setEnabled(true);
            }else {
                butDeleteSelected.setEnabled(false);
            }

            if(gridTable.getSelectedRows().size() == 1) {
                butEditRow.setEnabled(true);
            } else {
                butEditRow.setEnabled(false);
            }

            if(gridTable.getSelectedRows().size() > 0 && isCheckBoxSelected()) {
                butRemoveCell.setEnabled(true);
            } else {
                butRemoveCell.setEnabled(false);
            }

            if(gridTable.getSelectedRows().size() > 1 && isCheckBoxSelected()) {
                butMergeCells.setEnabled(true);
            } else {
                butMergeCells.setEnabled(false);
            }

            if(gridTable.getSelectedRows().size() == 1 && isCheckBoxSelected()) {
                butAddNewCell.setEnabled(true);
            } else {
                butAddNewCell.setEnabled(false);
            }
            */
        });

        // Buttons listeners

        butDeleteSelected.addClickListener(event -> {
            if (gridTable.getSelectedRows().size() > 0) {
                ArrayList<Integer> nrSelectedRows = new ArrayList<>();
                for (Object itemId : selectTable.getSelectedRows()) {
                    nrSelectedRows.add(gridTable.getContainerDataSource().indexOfId(itemId)); //pobieranie indeksu wiersza ktory zostanie usuniety
                }
                Collections.sort(nrSelectedRows); //sortowanie indeksow od najmniejszego do najwiekszego
                int shift = 0; //po przesunieciu w gore wierszy, inny wiersz do usuniecia tez trzeba najpierw przesunac
                for (int row : nrSelectedRows) {
                    for (int j = row - shift; j < tab.length - 1; j++) {
                        tab[j][0] = tab[j + 1][0]; //przepisywanie wartosci, czyli przesun wszystkie ponizsze komorki o jedna w gore
                        tab[j][1] = tab[j + 1][1];
                        tab[j][2] = tab[j + 1][2];
                        tab[j][3] = tab[j + 1][3];
                        tab[j][4] = tab[j + 1][4];
                    }
                    tab[tab.length - 1][0] = ""; //ostatniej komorce przypisuje nic, aby nie byla zdublowana z ta przesunieta
                    tab[tab.length - 1][1] = "";
                    tab[tab.length - 1][2] = "";
                    tab[tab.length - 1][3] = "";
                    tab[tab.length - 1][4] = "";
                    shift++;
                }
                String[][] tab2 = new String[tab.length - shift][5]; //nowa tablica bez pustych wierszy
                for (int i = 0; i < tab2.length; i++) { //kopiowanie wartosci
                    for (int j = 0; j < 5; j++) {
                        tab2[i][j] = tab[i][j];
                    }
                }
                filess.writeToCsv(".\\invoice_data\\faktura_ocr.csv", tab2);
                reloadGrid(tab);
                gridTable.getSelectionModel().reset();
            } else {
                Notification.show("Zaznacz przynajmniej jeden wiersz, który ma zostać usunięty.", Notification.Type.ERROR_MESSAGE);
            }
        });

        butMergeCells.addClickListener(event -> {
            if(gridTable.getSelectedRows().size() > 1 && isCheckBoxSelected()) {
                ArrayList<Integer> nrSelectedRows = new ArrayList<>();
                for (Object itemId : selectTable.getSelectedRows()) {
                    //pobieranie indeksów wierszy których komorki mają zostać połączone
                    nrSelectedRows.add(gridTable.getContainerDataSource().indexOfId(itemId));
                }
                //sortowanie indeksów od najmniejszego do największego
                Collections.sort(nrSelectedRows);
                boolean isNear = true;
                //pętla sprawdza czy komórki do scalenia leża obok siebie w tabeli
                for (int i = 0; i < nrSelectedRows.size() - 1; i++) {
                    if (nrSelectedRows.get(i) + 1 != nrSelectedRows.get(i + 1)) {
                        // dać komunikat że zaznaczone komórki muszą leżeć obok siebie
                        isNear = false;
                        break;
                    }
                }
                if(isNear) {
                    Integer nrSelectedCol = getSelectedColumn();
                    String mergedLine = "";
                    //scalanie zaznaczonych komórek
                    for (Integer row : nrSelectedRows) {
                        mergedLine = mergedLine.concat(tab[row][nrSelectedCol] + " ");
                    }
                    //zapisanie wartości scalonych komórek do jednej komórki
                    tab[nrSelectedRows.get(0)][nrSelectedCol] = mergedLine;
                    //przesunięcie komórek o tyle ile wynosi liczba scalonych komórek
                    int shift = nrSelectedRows.size();
                    for (int j = nrSelectedRows.get(0) + shift; j < tab.length; j++) {
                        //przesuwanie w górę wszystkich komórek, które były poniżej scalanych
                        tab[j - (shift - 1)][nrSelectedCol] = tab[j][nrSelectedCol];
                        //przypisanie pustej wartości ostatniej komórece,
                        // aby uniknąć zdublowana z przesuniętą komórką
                        tab[j][nrSelectedCol] = "";
                    }
                    filess.writeToCsv(".\\invoice_data\\faktura_ocr.csv", tab);
                    reloadGrid(tab); //ładuje przetworzeone dane do kontenera
                    gridTable.getSelectionModel().reset(); //odświeża tabelę
                }
            } else {
                Notification.show("Zaznacz przynajmniej dwa wiersze, oraz wybierz kolumnę.",
                        Notification.Type.ERROR_MESSAGE);
            }
        });

        butRemoveCell.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (gridTable.getSelectedRows().size() > 0 && isCheckBoxSelected()) {
                    ArrayList<Integer> nrSelectedRows = new ArrayList<>();
                    for (Object itemId : selectTable.getSelectedRows()) {
                        nrSelectedRows.add(gridTable.getContainerDataSource().indexOfId(itemId)); //pobieranie indeksu wiersza ktorego komorka ma zostac usunieta
                    }
                    Collections.sort(nrSelectedRows); //sortowanie indeksow od najmniejszego do najwiekszego
                    Integer nrSelectedCol = getSelectedColumn();
                    int shift = 0; //po przesunieciu w gore wszystkich komorek kolumny, inna komorke do usuniecia tez trzeba przesunac
                    for (int row : nrSelectedRows) {
                        for (int j = row - shift; j < tab.length - 1; j++) {
                            tab[j][nrSelectedCol] = tab[j + 1][nrSelectedCol]; //przepisywanie wartosci, czyli przesun wszystkie ponizsze komorki o jedna w gore
                        }
                        tab[tab.length - 1][nrSelectedCol] = ""; //ostatniej komorce przypisuje nic, aby nie byla zdublowana z ta przesunieta
                        shift++;
                    }
                    reloadGrid(tab); //ładuje przetworzeone dane do kontenera
                    gridTable.getSelectionModel().reset(); //odświeża tabelę
                } else {
                    Notification.show("Zaznacz przynajmniej jeden wiersz, oraz wybierz kolumnę.", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        butAddNewCell.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

            }
        });

        butEditRow.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (gridTable.getSelectedRows().size() == 1) {
                    ArrayList<Integer> nrSelectedRows = new ArrayList<>();
                    for (Object itemId : selectTable.getSelectedRows()) {
                        nrSelectedRows.add(gridTable.getContainerDataSource().indexOfId(itemId)); //pobieranie indeksu wiersza ktory zostanie usuniety
                    }
                    indexEditedField = nrSelectedRows.get(0);
                    // gdy klikniety to otwiera sie subokno ze wszystkimi polami tabeli do edycji
                    System.out.println("index" + indexEditedField);
                    editArticleSubWin.init(tab[indexEditedField][0], tab[indexEditedField][1], tab[indexEditedField][2],
                            tab[indexEditedField][3], tab[indexEditedField][4]);
                    // Set subwindow size
                    editArticleSubWin.setHeight("300px");
                    editArticleSubWin.setWidth("650px");
                    // Add it to the root component
                    UI.getCurrent().addWindow(editArticleSubWin);
                } else {
                    Notification.show("Zaznacz jeden wiersz, który chcesz edytować.", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        // listener sprawdzajacy czy subokno ecycji zostalo zamkniete
        editArticleSubWin.addCloseListener(e -> {
            if(editArticleSubWin.getIsAccept()) {
                tab[indexEditedField][0] = editArticleSubWin.getCatalogNr();
                tab[indexEditedField][1] = editArticleSubWin.getArticleName();
                tab[indexEditedField][2] = editArticleSubWin.getDiscountPercent();
                tab[indexEditedField][3] = editArticleSubWin.getTaxPercent();
                tab[indexEditedField][4] = editArticleSubWin.getPriceGrossTotality();
                filess.writeToCsv(".\\invoice_data\\faktura_ocr.csv", tab);
                reloadGrid(tab);
                gridTable.getSelectionModel().reset();
                Notification.show("Edycja artykułu" + editArticleSubWin.getCatalogNr() + " przebiegła pomyślnie.", Notification.Type.TRAY_NOTIFICATION);
            }
        });

        butAddNewInvoice.addClickListener(event -> {
            //przed dodaniem do encji usunac jej zawartosc lub inaczej to przemyslec
            for(int i = 0; i < tab.length; i++) {
                if(!(tab[i][0].equals("") //nie wczytuj wiersza jesli wszystkie jego komorki sa puste
                        && tab[i][1].equals("")
                        && tab[i][2].equals("")
                        && tab[i][3].equals("")
                        && tab[i][4].equals(""))) {
                    String barcode = losowanieBezPowtorzen.get().toString();
                    articlesOcrTmpRep.save(
                            new ArticlesOcrTmp(null, barcode, tab[i][0], tab[i][1].trim(),
                                    hm.convertStringToInteger(tab[i][2]),
                                    hm.convertStringToInteger(tab[i][3]),
                                    hm.convertStringAmountToBigDecimal(tab[i][4])));
                    //Notification.show("TO DO");
                }
            }
            filess.deleteFile(".\\invoice_data\\faktura_ocr.csv");
            //zapisanie strony postepu
            staticVariablesRep.editOneByName("saveofrosa",3);
            getUI().getNavigator().navigateTo(ReceiptOfSupplyAuto3View.VIEW_NAME);
        });

        butCancelNewInvoice.addClickListener(event -> {
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
        });

        selection3OptionsSubWin.addCloseListener(e -> {
            if (selection3OptionsSubWin.getSelectedOption() == 1) {
                //nic nie zostanie zapisane
                invoicesSupplyTmpRep.deleteAll();
                categoryTmpRep.deleteAll();
                articlesTmpRep.deleteAll();
                articlesOcrTmpRep.deleteAll();
                filess.deleteFile(".\\invoice_data\\faktura_ocr.csv");
                staticVariablesRep.editOneByName("saveofrosa",0);
                staticVariablesRep.editOneByName("rosaissaved",0);
                JavaScript.eval("close()");
                getUI().close();
            } else if(selection3OptionsSubWin.getSelectedOption() == 2) {
                //wprowadzanie towaru zostanie wznowione od widoku ROSA3
                staticVariablesRep.editOneByName("saveofrosa",2);
                staticVariablesRep.editOneByName("rosaissaved",1);
                JavaScript.eval("close()");
                getUI().close();
            }
        });

        // End of buttons listeners

        //dodać jeszcze listenery do CheckBoxow odpowiadajacych kolumnom tabeli
        //listenery te maja odblokowywac przyciski funkcyjne dla komorek
        //wykorzystac do tego funkcje isCheckBoxSelected()
        // - proby wdrozenia wykazaly problemy z listenerami dla CheckBoxow
        // - sluchacze CheckBoxow nie przyjmuja wiecej niz jednej istrukcji, co czyni je bezuzytecznymi w tym kontekscie

    }

    private void listArticles() {
        gridTable.setContainerDataSource(containerTable);
    }


    private Boolean isCheckBoxSelected() {

        if(cbCatalogNr.getValue() || cbArticleName.getValue() || cbDiscountPercent.getValue()
                || cbTaxPercent.getValue() || cbPriceGrossTotality.getValue()) {
            return true;
        }
        else{
            return false;
        }
    }

    private ArrayList<Integer> selectedColumns() {
        ArrayList<Integer> nrSelectedCol = new ArrayList<>();
        if(cbCatalogNr.getValue())
            nrSelectedCol.add(0);
        if(cbArticleName.getValue())
            nrSelectedCol.add(1);
        if(cbDiscountPercent.getValue())
            nrSelectedCol.add(2);
        if(cbTaxPercent.getValue())
            nrSelectedCol.add(3);
        if(cbPriceGrossTotality.getValue())
            nrSelectedCol.add(4);
        return nrSelectedCol;
    }

    private Integer getSelectedColumn() {
        Integer nrSelectedCol = null;
        if(cbCatalogNr.getValue())
            nrSelectedCol = 0;
        if(cbArticleName.getValue())
            nrSelectedCol = 1;
        if(cbDiscountPercent.getValue())
            nrSelectedCol = 2;
        if(cbTaxPercent.getValue())
            nrSelectedCol = 3;
        if(cbPriceGrossTotality.getValue())
            nrSelectedCol = 4;
        return nrSelectedCol;
    }

    private void reloadGrid(String[][] tab) {
        containerTable.removeAllItems(); //usuwa poprzednia zawartosc kontenera przed wczytaniem nowej
        for(int i = 0; i < tab.length; i++) {
            if(!(tab[i][0].equals("") //nie wczytuj wiersza jesli wszystkie jego komorki sa puste
                    && tab[i][1].equals("")
                    && tab[i][2].equals("")
                    && tab[i][3].equals("")
                    && tab[i][4].equals(""))){
                Item newItem = containerTable.getItem(containerTable.addItem());
                newItem.getItemProperty("NrKatalogowy").setValue(tab[i][0]);
                newItem.getItemProperty("NazwaArtykułu").setValue(tab[i][1]);
                newItem.getItemProperty("Rabat").setValue(tab[i][2]);
                newItem.getItemProperty("VAT").setValue(tab[i][3]);
                newItem.getItemProperty("Wartość brutto").setValue(tab[i][4]);
            }
        }
        listArticles();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }

}