package com.pasmanteria.view;

import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.CreatePdfDocument;
import com.pasmanteria.QueryMapper;
import com.pasmanteria.repository.SoldArticlesRep;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * Created by adrian on 03.01.2017.
 */
@SpringUI(path = "/raports")
//@SpringView(name = RaportsView.VIEW_NAME)
@Theme("valo")
public class RaportsView extends UI {
    public static final String VIEW_NAME = "RaportsView";

    private VerticalLayout mainLayout;
    private HorizontalLayout optionsLayout;
    private HorizontalLayout buttonsLayout;

    private HorizontalLayout hlBanner;
    private Image imgHeader;

    private ComboBox cbSelectRaport;
    private String raports[] = {"Sprzedane", "Najczęściej sprzedawane", "Najrzadziej sprzedawane", "Najwięcej sprzedanych", "Najmniej sprzedanych"};
    private PopupDateField pdfDateFrom;
    private PopupDateField pdfDateTo;

    private Button butPrint;
    private Button butFilter;

    private Grid gridTable;
    private IndexedContainer container;

    private SoldArticlesRep soldArticlesRep;

    private HelpfulMethods hm;
    private CreatePdfDocument createPdfDoc;

    private Object queryResult1[][];

    private String nameOfDocument;
    private String documentTitle;

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    @Autowired
    public RaportsView(SoldArticlesRep soldArticlesRep) {
        this.soldArticlesRep = soldArticlesRep;
        this.gridTable = new Grid();
        container = new IndexedContainer();
    }

    @Override
    protected void init(VaadinRequest request) {
        // Create
        mainLayout = new VerticalLayout();
        optionsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        hlBanner = new HorizontalLayout();
        cbSelectRaport = new ComboBox("Rodzaj raportu");
        //cbSelectMonth = new ComboBox("Miesiąc");
        pdfDateFrom = new PopupDateField("Od dnia");
        pdfDateTo = new PopupDateField("Do dnia");
        butPrint = new Button("Drukuj");
        butFilter = new Button("Szukaj");
        nameOfDocument = "Raport";

        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/banners/raporty_m.png"));
        imgHeader = new Image("",resource);

        // Add
        for (String raport : raports)
            cbSelectRaport.addItem(raport);

        hlBanner.addComponent(imgHeader);
        optionsLayout.addComponents(cbSelectRaport, pdfDateFrom, pdfDateTo, butFilter);
        buttonsLayout.addComponents(butPrint);
        mainLayout.addComponents(hlBanner, optionsLayout, gridTable, buttonsLayout);

        // Set
        this.setContent(mainLayout);

        hlBanner.setComponentAlignment(imgHeader, Alignment.MIDDLE_CENTER);
        hlBanner.setWidth(100, Unit.PERCENTAGE);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        optionsLayout.setSpacing(true);
        buttonsLayout.setSpacing(true);

        pdfDateFrom.setLocale(Locale.forLanguageTag("pl"));
        pdfDateFrom.setDateFormat("dd-MM-yyyy");
        pdfDateTo.setLocale(Locale.forLanguageTag("pl"));
        pdfDateTo.setDateFormat("dd-MM-yyyy");

        cbSelectRaport.setWidth("300");
        //cbSelectMonth.setWidth("300");
        cbSelectRaport.setNullSelectionAllowed(false);
        Collection<?> itemsIds = cbSelectRaport.getItemIds();
        //Object defaultValue = cbSelectRaport.getItem(itemsIds.iterator().next());
        cbSelectRaport.setValue(itemsIds.iterator().next());

        optionsLayout.setComponentAlignment(butFilter, Alignment.BOTTOM_CENTER);

        gridTable.setHeight(100, Unit.PERCENTAGE);
        gridTable.setWidth(100, Unit.PERCENTAGE);
        gridTable.setLocale(Locale.forLanguageTag("pl"));

        showSoldItemsFromDateToDate(hm.convertStringToDate("01-01-1900"), hm.convertStringToDate("01-01-3000"));

        //Listeners
        butPrint.addClickListener(event -> {
                createPdfDoc.createPdf(queryResult1, nameOfDocument, documentTitle);
        });

        butFilter.addClickListener(event -> {
            String option = cbSelectRaport.getValue().toString();
            switch(option) {
                case "Sprzedane": {

                    if(pdfDateFrom.isEmpty() && pdfDateTo.isEmpty()) {
                        showSoldItemsFromDateToDate(hm.convertStringToDate("01-01-1900"), hm.convertStringToDate("01-01-3000"));
                    } else if(pdfDateFrom.isEmpty() && !pdfDateTo.isEmpty()) {
                        showSoldItemsFromDateToDate(hm.convertStringToDate("01-01-1900"), pdfDateTo.getValue());
                    } else if(!pdfDateFrom.isEmpty() && pdfDateTo.isEmpty()) {
                        showSoldItemsFromDateToDate(pdfDateFrom.getValue(), hm.convertStringToDate("01-01-3000"));
                    } else if(!pdfDateFrom.isEmpty() && !pdfDateTo.isEmpty()) {
                        showSoldItemsFromDateToDate(pdfDateFrom.getValue(), pdfDateTo.getValue());
                    }
                    break;
                }
                case "Najczęściej sprzedawane": {
                    if(pdfDateFrom.isEmpty() && pdfDateTo.isEmpty()) {
                        showMostOftenSold(hm.convertStringToDate("01-01-1900"), hm.convertStringToDate("01-01-3000"));
                    } else if(pdfDateFrom.isEmpty() && !pdfDateTo.isEmpty()) {
                        showMostOftenSold(hm.convertStringToDate("01-01-1900"), pdfDateTo.getValue());
                    } else if(!pdfDateFrom.isEmpty() && pdfDateTo.isEmpty()) {
                        showMostOftenSold(pdfDateFrom.getValue(), hm.convertStringToDate("01-01-3000"));
                    } else if(!pdfDateFrom.isEmpty() && !pdfDateTo.isEmpty()) {
                        showMostOftenSold(pdfDateFrom.getValue(), pdfDateTo.getValue());
                    }
                    break;
                }
                case "Najrzadziej sprzedawane": {
                    if(pdfDateFrom.isEmpty() && pdfDateTo.isEmpty()) {
                        showLeastOftenSold(hm.convertStringToDate("01-01-1900"), hm.convertStringToDate("01-01-3000"));
                    } else if(pdfDateFrom.isEmpty() && !pdfDateTo.isEmpty()) {
                        showLeastOftenSold(hm.convertStringToDate("01-01-1900"), pdfDateTo.getValue());
                    } else if(!pdfDateFrom.isEmpty() && pdfDateTo.isEmpty()) {
                        showLeastOftenSold(pdfDateFrom.getValue(), hm.convertStringToDate("01-01-3000"));
                    } else if(!pdfDateFrom.isEmpty() && !pdfDateTo.isEmpty()) {
                        showLeastOftenSold(pdfDateFrom.getValue(), pdfDateTo.getValue());
                    }
                    break;
                }
                case "Najwięcej sprzedanych": {
                    if(pdfDateFrom.isEmpty() && pdfDateTo.isEmpty()) {
                        showMostSold(hm.convertStringToDate("01-01-1900"), hm.convertStringToDate("01-01-3000"));
                    } else if(pdfDateFrom.isEmpty() && !pdfDateTo.isEmpty()) {
                        showMostSold(hm.convertStringToDate("01-01-1900"), pdfDateTo.getValue());
                    } else if(!pdfDateFrom.isEmpty() && pdfDateTo.isEmpty()) {
                        showMostSold(pdfDateFrom.getValue(), hm.convertStringToDate("01-01-3000"));
                    } else if(!pdfDateFrom.isEmpty() && !pdfDateTo.isEmpty()) {
                        showMostSold(pdfDateFrom.getValue(), pdfDateTo.getValue());
                    }
                    break;
                }
                case "Najmniej sprzedanych": {
                    if(pdfDateFrom.isEmpty() && pdfDateTo.isEmpty()) {
                        showLeastSold(hm.convertStringToDate("01-01-1900"), hm.convertStringToDate("01-01-3000"));
                    } else if(pdfDateFrom.isEmpty() && !pdfDateTo.isEmpty()) {
                        showLeastSold(hm.convertStringToDate("01-01-1900"), pdfDateTo.getValue());
                    } else if(!pdfDateFrom.isEmpty() && pdfDateTo.isEmpty()) {
                        showLeastSold(pdfDateFrom.getValue(), hm.convertStringToDate("01-01-3000"));
                    } else if(!pdfDateFrom.isEmpty() && !pdfDateTo.isEmpty()) {
                        showLeastSold(pdfDateFrom.getValue(), pdfDateTo.getValue());
                    }
                    break;
                }
                default: {
                    System.out.println("Nie ma takiej opcji.");
                    break;
                }
            }
        });
    }



    private void showSoldItemsFromDateToDate(Date dateFrom, Date dateTo) {
        nameOfDocument = "sprzedane_" + hm.convertDateToNameOfDocument(new Date());
        documentTitle = "Artykuły sprzedane od " + hm.convertDateToString(dateFrom) + " do " + hm.convertDateToString(dateTo);

        container.removeAllItems();
        for(Object col: container.getContainerPropertyIds().toArray())  {
            container.removeContainerProperty(col);
        }

        container.addContainerProperty("NrTransakcji", Integer.class, ""); //transactionId
        container.addContainerProperty("KodKreskowy", String.class, ""); //barcode
        container.addContainerProperty("NrKatalogowy", String.class, ""); //catalogNr
        container.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
        container.addContainerProperty("Ilość", BigDecimal.class, 0.0); //quantity
        container.addContainerProperty("JM", String.class, ""); //unitOfMeasurement
        container.addContainerProperty("Rabat", Integer.class, 0); //discountForCustomerPercent
        container.addContainerProperty("Cena", BigDecimal.class, 0.0); //endPrice
        container.addContainerProperty("DataSprzedania", Date.class, null); //dateAdded
        gridTable.setContainerDataSource(container);

        Object content[][] = QueryMapper.readDataFromQuery(soldArticlesRep.findAllFromDateToDate(dateFrom, dateTo));
        queryResult1 = content;
        if(content != null) {
            for (Object[] row : content) {
                gridTable.addRow(row);
            }
        }
    }

    private void showLeastOftenSold(Date dateFrom, Date dateTo) {
        nameOfDocument = "najrzadziej_sprzedawane_" + hm.convertDateToNameOfDocument(new Date());
        documentTitle = "Artykuły najrzadziej sprzedawane od " + hm.convertDateToString(dateFrom) + " do " + hm.convertDateToString(dateTo);

        container.removeAllItems();
        for(Object col: container.getContainerPropertyIds().toArray())  {
            container.removeContainerProperty(col);
        }

        container.addContainerProperty("KodKreskowy", String.class, ""); //barcode
        container.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
        container.addContainerProperty("Ilość", BigDecimal.class, 0.0); //quantity
        container.addContainerProperty("JM", String.class, ""); //unitOfMeasurement
        container.addContainerProperty("Transakcje", Object.class, 0.0); //Transaction
        gridTable.setContainerDataSource(container);

        Object content[][] = QueryMapper.readDataFromQuery(soldArticlesRep.findLeastOftenSold(dateFrom, dateTo));
        queryResult1 = content;
        if(content != null) {
            for (Object[] row : content) {
                gridTable.addRow(row);
            }
        }
    }

    private void showMostOftenSold(Date dateFrom, Date dateTo) {
        nameOfDocument = "najczesciej_sprzedawane_" + hm.convertDateToNameOfDocument(new Date());
        documentTitle = "Artykuły najczęściej sprzedawane od " + hm.convertDateToString(dateFrom) + " do " + hm.convertDateToString(dateTo);

        container.removeAllItems();
        for(Object col: container.getContainerPropertyIds().toArray())  {
            container.removeContainerProperty(col);
        }
        container.addContainerProperty("KodKreskowy", String.class, ""); //barcode
        container.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
        container.addContainerProperty("Ilość", BigDecimal.class, 0.0); //quantity
        container.addContainerProperty("JM", String.class, ""); //unitOfMeasurement
        container.addContainerProperty("Transakcje", Object.class, 0.0); //Transaction
        gridTable.setContainerDataSource(container);

        Object content[][] = QueryMapper.readDataFromQuery(soldArticlesRep.findMostOftenSold(dateFrom, dateTo));
        queryResult1 = content;
        if(content != null) {
            for (Object[] row : content) {
                gridTable.addRow(row);
            }
        }
    }

    private void showLeastSold(Date dateFrom, Date dateTo) {
        nameOfDocument = "najmniej_sprzedanych_" + hm.convertDateToNameOfDocument(new Date());
        documentTitle = "Artykuły najgorzej sprzedające się od " + hm.convertDateToString(dateFrom) + " do " + hm.convertDateToString(dateTo);

        container.removeAllItems();
        for(Object col: container.getContainerPropertyIds().toArray())  {
            container.removeContainerProperty(col);
        }
        container.addContainerProperty("KodKreskowy", String.class, ""); //barcode
        container.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
        container.addContainerProperty("Ilość", BigDecimal.class, 0.0); //quantity
        container.addContainerProperty("JM", String.class, ""); //unitOfMeasurement
        container.addContainerProperty("Transakcje", Object.class, 0.0); //Transaction
        gridTable.setContainerDataSource(container);

        Object content[][] = QueryMapper.readDataFromQuery(soldArticlesRep.findLeastSold(dateFrom, dateTo));
        queryResult1 = content;
        if(content != null) {
            for (Object[] row : content) {
                gridTable.addRow(row);
            }
        }
    }

    private void showMostSold(Date dateFrom, Date dateTo) {
        nameOfDocument = "najwiecej_sprzedanych_" + hm.convertDateToNameOfDocument(new Date());
        documentTitle = "Artykuły najlepiej sprzedające się od " + hm.convertDateToString(dateFrom) + " do " + hm.convertDateToString(dateTo);

        container.removeAllItems();
        for(Object col: container.getContainerPropertyIds().toArray())  {
            container.removeContainerProperty(col);
        }
        container.addContainerProperty("KodKreskowy", String.class, ""); //barcode
        container.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
        container.addContainerProperty("Ilość", BigDecimal.class, 0.0); //quantity
        container.addContainerProperty("JM", String.class, ""); //unitOfMeasurement
        container.addContainerProperty("Transakcje", Object.class, 0.0); //Transaction
        gridTable.setContainerDataSource(container);

        Object content[][] = QueryMapper.readDataFromQuery(soldArticlesRep.findMostSold(dateFrom, dateTo));
        queryResult1 = content;
        if(content != null) {
            for (Object[] row : content) {
                gridTable.addRow(row);
            }
        }
    }

/* //do usuniecia jesli wszystko dziala

    public Object[][] listArg() {
        List<Object[]> result = articlesToOrderRep.findAllToGrid();
        //System.out.println(result);
        Object[][] tabArt = new Object[result.size()][result.get(0).length];
        int i = 0;
        for (Object[] objects : result) {
            int j = 0;
            for (Object object : objects) {
                tabArt[i][j] = object;
                //System.out.println(object);
                j++;
            }
            i++;
        }
        return tabArt;
    }
*/
/*
    void addNewItemToContainer(Object[] row) {
        Item newItem = container.getItem(container.addItem());
        newItem.getItemProperty("NrTransakcji").setValue(row[0]);
        newItem.getItemProperty("KodKreskowy").setValue(row[1]);
        newItem.getItemProperty("NrKatalogowy").setValue(row[2]);
        newItem.getItemProperty("NazwaArtykułu").setValue(row[3]);
        newItem.getItemProperty("Ilość").setValue(row[4]);
        newItem.getItemProperty("JM").setValue(row[5]);
        newItem.getItemProperty("Rabat").setValue(row[6]);
        newItem.getItemProperty("Cena").setValue(row[7]);
        newItem.getItemProperty("DataSprzedania").setValue(row[8]);
    }


    void addNewItemToContainer2(Object[] row) {
        Item newItem = container.getItem(container.addItem());
        newItem.getItemProperty("KodKreskowy").setValue(row[0]);
        newItem.getItemProperty("NazwaArtykułu").setValue(row[1]);
        newItem.getItemProperty("Ilość").setValue(row[2]);
        newItem.getItemProperty("JM").setValue(row[3]);
        newItem.getItemProperty("Transakcje").setValue(row[4]);

    }
*/
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        // the view is constructed in the init() method()
//    }

}
