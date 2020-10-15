package com.pasmanteria.view;

import com.pasmanteria.Enum.*;
import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.QueryMapper;
import com.pasmanteria.CategorySpecification;
import com.pasmanteria.model.Articles;
import com.pasmanteria.model.Category;
import com.pasmanteria.repository.ArticlesRep;
import com.pasmanteria.repository.CategoryRep;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by adrian on 08.12.2016.
 */
@SpringUI(path = "/warehouse")
//@SpringView(name = WarehouseView.VIEW_NAME)
@Theme("valo")
//@StyleSheet({"http://fonts.googleapis.com/css?family=Cabin+Sketch"})
public class WarehouseView extends UI {
    public static final String VIEW_NAME = "WarehouseView";

    private VerticalLayout mainLayout;
    private HorizontalLayout bodyLayout;
    private VerticalLayout filteringLayout;
    private VerticalLayout specificFilteringLayout;
    private VerticalLayout gridLayout;
    private HorizontalLayout hlBanner;
    private Image imgHeader;

    private Label labCategory;
    private Grid gridTable;
    private IndexedContainer container;
    private Button butFilter;
    private ComboBox cbMainCategory;

    //--- elements for categories
    ComboBox cbList1;
    ComboBox cbList2;
    ComboBox cbList3;
    ComboBox cbColor;
    TextField tfColorNr;
    TextField tfWidth;
    TextField tfThickness;
    TextField tfLength;
    TextField tfDesignation;
    TextField tfSize;
    TextField tfLengthInt;
    TextField tfWeight;
    //--- end elements for categories

    private ArticlesRep articlesRep;
    private CategoryRep categoryRep;

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    @Autowired
    public WarehouseView(ArticlesRep articlesRep, CategoryRep categoryRep) {
        this.articlesRep = articlesRep;
        this.categoryRep = categoryRep;
        this.gridTable = new Grid();
        container = new IndexedContainer();
    }

    @Override
    protected void init(VaadinRequest request) {

        //Create
        mainLayout = new VerticalLayout();
        bodyLayout = new HorizontalLayout();
        filteringLayout = new VerticalLayout();
        hlBanner = new HorizontalLayout();
        gridLayout = new VerticalLayout();
        specificFilteringLayout = new VerticalLayout();
        labCategory = new Label();
        butFilter = new Button("Filtruj");
        cbMainCategory = new ComboBox("Kategoria");

        for(MainCategories item : MainCategories.values()) {
            cbMainCategory.addItem(item.displayName());
        }

        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/banners/asortyment_m.png"));
        imgHeader = new Image("",resource);

        //Add
        container.addContainerProperty("KodKreskowy", String.class, "");
        container.addContainerProperty("NumerKatalogowy", String.class, "");
        container.addContainerProperty("NazwaArtykułu", String.class, "");
        container.addContainerProperty("Ilość", BigDecimal.class, 0.0);
        container.addContainerProperty("JM", String.class, "");
        container.addContainerProperty("Cena", BigDecimal.class, 0.0f); //cena szt. z marżą

        hlBanner.addComponent(imgHeader);
        filteringLayout.addComponents(cbMainCategory, specificFilteringLayout);
        gridLayout.addComponents(gridTable, labCategory);
        bodyLayout.addComponents(filteringLayout, gridLayout);
        mainLayout.addComponents(hlBanner, bodyLayout);

        //Set

        hlBanner.setComponentAlignment(imgHeader, Alignment.MIDDLE_CENTER);
        hlBanner.setWidth(100, Unit.PERCENTAGE);

        Double tableWidth = UI.getCurrent().getPage().getBrowserWindowWidth() * 0.75;
        Double tableHeight = UI.getCurrent().getPage().getBrowserWindowHeight() * 0.8;
        Double filteringWidth = UI.getCurrent().getPage().getBrowserWindowWidth() * 0.2;
        String tableWidthStr = Integer.toString(tableWidth.intValue());
        String tableHeightStr = Integer.toString(tableHeight.intValue());
        String filteringWidthStr = Integer.toString(filteringWidth.intValue());
        gridTable.setWidth(tableWidthStr);
        gridTable.setHeight(tableHeightStr);
        filteringLayout.setWidth(filteringWidthStr);
        gridTable.setContainerDataSource(container);

        Grid.HeaderRow filterRow = gridTable.appendHeaderRow();

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        bodyLayout.setSpacing(true);

        cbMainCategory.setWidth("230");
        cbMainCategory.setNullSelectionAllowed(true);
        specificFilteringLayout.setSpacing(true);
        filteringLayout.setSpacing(true);
        this.setContent(mainLayout);

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

        //end Set

        showAll();

        gridTable.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridTable.addSelectionListener(event -> {
            Object selected = ((Grid.SingleSelectionModel)gridTable.getSelectionModel()).getSelectedRow();
            //Object selected = gridTable.getSelectedRow();
            if(selected != null) {
                String barcode = gridTable.getContainerDataSource().getItem(selected).getItemProperty("KodKreskowy").getValue().toString();
                Articles a = articlesRep.findOneByBarcode(barcode);
                labCategory.setValue("Kategoria: " + a.getCategoryTxt());
            }
        });


        //filtrowanie wg głownych kategorii
        cbMainCategory.addValueChangeListener(event -> {
            if (cbMainCategory.getValue() == null) {
                specificFilteringLayout.removeAllComponents();
                container.removeAllItems();
                showAll();
            } else {
                container.removeAllItems();
                String mainCategory = cbMainCategory.getValue().toString();
                List<Object[]> queryContent1 = articlesRep.findAllFromSpecificCategory(mainCategory);
                if (queryContent1.size() != 0) {
                    Object[][] filteredList = QueryMapper.readDataFromQuery(queryContent1);
                    for (Object[] row : filteredList)
                        gridTable.addRow(row);
                }
                loadFiltersForChoosenCategory();
            }
        });

        //filtrowanie szczególowe
        butFilter.addClickListener(event -> {
            Category criteria = new Category();
            container.removeAllItems();
            if (cbMainCategory instanceof ComboBox) {
                if (cbMainCategory.getValue() == null) {
                    showAll();
                } else {
                    criteria.setMainCategory(cbMainCategory.getValue().toString());
                    if (cbList1 instanceof ComboBox) {
                        criteria.setType1(cbList1.getValue() == null ? null : cbList1.getValue().toString());
                    }
                    if (cbList2 instanceof ComboBox) {
                        criteria.setType2(cbList2.getValue() == null ? null : cbList2.getValue().toString());
                    }
                    if (cbList3 instanceof ComboBox) {
                        criteria.setType3(cbList3.getValue() == null ? null : cbList3.getValue().toString());
                    }
                    if (cbColor instanceof ComboBox) {
                        criteria.setColor(cbColor.getValue() == null ? null : cbColor.getValue().toString());
                    }
                    if (tfColorNr instanceof TextField) {
                        criteria.setColorNr(tfColorNr.getValue().equals("") ? null : tfColorNr.getValue());
                    }
                    if (tfSize instanceof TextField) {
                        criteria.setSize(tfSize.getValue().equals("") ? null : Integer.parseInt(tfSize.getValue()));
                    }
                    if (tfThickness instanceof TextField) {
                        criteria.setThickness(tfThickness.getValue().equals("") ? null : Integer.parseInt(tfThickness.getValue()));
                    }
                    if (tfDesignation instanceof TextField) {
                        criteria.setDesignation(tfDesignation.getValue().equals("") ? null : tfDesignation.getValue());
                    }
                    if (tfWidth instanceof TextField) {
                        criteria.setWidth(tfWidth.getValue().equals("") ? null : Integer.parseInt(tfWidth.getValue()));
                    }
                    if (tfLength instanceof TextField) {
                        criteria.setLength(tfLength.getValue().equals("") ? null : Float.parseFloat(tfLength.getValue()));
                    }
                    if (tfWeight instanceof TextField) {
                        criteria.setWeight(tfWeight.getValue().equals("") ? null : Integer.parseInt(tfWeight.getValue()));
                    }
                    if (tfLengthInt instanceof TextField) {
                        criteria.setLength(tfLengthInt.getValue().equals("") ? null : Float.parseFloat(tfLengthInt.getValue()));
                    }

                    CategorySpecification categorySpecification = new CategorySpecification(criteria);
                    List<Category> fittedCategories = categoryRep.findAll(categorySpecification);
                    System.out.println(fittedCategories.size());
                    List<Articles> fittedArticles = new ArrayList<>();
                    for (Category cat : fittedCategories) {
                        fittedArticles.add(articlesRep.findOneByBarcode(cat.getBarcode()));
                        System.out.println(cat.getBarcode());
                    }

                    for (Articles art : fittedArticles) {
                        addNewItemToContainer(art);
                        System.out.println(art.getArticleName());
                    }
                }
            }
        });
    }



    private void showAll() {
        List<Object[]> queryContent = articlesRep.findAllFromAllCategories();
        if(queryContent.size() != 0) {
            Object[][] filteredList = QueryMapper.readDataFromQuery(queryContent);
            for (Object[] row : filteredList)
                gridTable.addRow(row);
        }
    }

    private void loadFiltersForChoosenCategory() {
        if (cbMainCategory.getValue() != null) {
            specificFilteringLayout.removeAllComponents();
            String category = cbMainCategory.getValue().toString();
            switch (category) {
                case "KORONKI": {
                    cbList1 = new ComboBox("Rodzaj");
                    for(KoronkiRodzaj item : KoronkiRodzaj.values()) {
                        cbList1.addItem(item.displayName());
                    }
                    tfWidth = new TextField("Szerokość [mm]");
                    specificFilteringLayout.addComponents(cbList1, tfWidth, butFilter);
                    tfWidth.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    break;
                }
                case "NICI": {
                    cbList1 = new ComboBox("Rodzaj");
                    for(NiciRodzaj_I item : NiciRodzaj_I.values()) {
                        cbList1.addItem(item.displayName());
                    }
                    cbList2 = new ComboBox("Rodzaj");
                    for(NiciRodzaj_II item : NiciRodzaj_II.values()){
                        cbList2.addItem(item.displayName());
                    }
                    cbColor = new ComboBox("Kolor");

                    for(Colors item : Colors.values()){
                        cbColor.addItem(item.displayName());
                    }
                    tfColorNr = new TextField("Kod koloru");
                    tfThickness = new TextField("Grubość");
                    tfLength = new TextField("Długość [m]");
                    tfDesignation = new TextField("Firma(Nazwa)");
                    specificFilteringLayout.addComponents(cbList1, cbList2, cbColor, tfColorNr, tfThickness, tfLength, tfDesignation, butFilter);

                    String type1 = NiciRodzaj_I.SPECJALNE.displayName();

                    cbList2.setVisible(false);
                    cbColor.setVisible(false);
                    tfColorNr.setVisible(false);
                    tfLength.setVisible(false);
                    tfThickness.setVisible(false);
                    tfDesignation.setVisible(false);

                    cbList1.addValueChangeListener(event -> {
                        if(cbList1.getValue().toString().equals(type1)){
                            cbList2.setVisible(true);
                            cbColor.setVisible(true);
                            tfColorNr.setVisible(true);
                            tfLength.setVisible(true);
                            tfThickness.setVisible(true);
                            tfDesignation.setVisible(true);
                        } else {
                            cbList2.setVisible(false);
                            cbList2.setValue(null);
                            cbColor.setVisible(true);
                            tfColorNr.setVisible(true);
                            tfLength.setVisible(true);
                            tfThickness.setVisible(true);
                            tfDesignation.setVisible(true);
                        }
                    });

                    tfThickness.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    tfLength.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
                    break;
                }
                case "KORDONKI I MULINA": {
                    cbList1 = new ComboBox("Rodzaj");
                    for(KordonkiMulinaRodzaj_I item : KordonkiMulinaRodzaj_I.values()){
                        cbList1.addItem(item.displayName());
                    }
                    cbColor = new ComboBox("Kolor");
                    for(Colors item : Colors.values()){
                        cbColor.addItem(item.displayName());
                    }
                    tfColorNr = new TextField("Kod koloru");
                    tfThickness = new TextField("Grubość");
                    tfDesignation = new TextField("Firma(Nazwa)");
                    tfLength = new TextField("Długość [m]");
                    tfWeight = new TextField("Waga [g]");

                    specificFilteringLayout.addComponents(cbList1, cbColor, tfColorNr, tfThickness, tfLength, tfDesignation, tfWeight, butFilter);

                    String type1a = KordonkiMulinaRodzaj_I.KORDONKI.displayName();
                    String type1b = KordonkiMulinaRodzaj_I.MULINA.displayName();

                    cbColor.setVisible(false);
                    tfColorNr.setVisible(false);
                    tfThickness.setVisible(false);
                    tfDesignation.setVisible(false);
                    tfLength.setVisible(false);
                    tfWeight.setVisible(false);

                    cbList1.addValueChangeListener(event -> {
                        if (cbList1.getValue().toString().equals(type1a)) {
                            cbColor.setVisible(true);
                            tfColorNr.setVisible(true);
                            tfThickness.setVisible(true);
                            tfDesignation.setVisible(true);
                            tfLength.setVisible(true);
                            tfWeight.setVisible(true);
                        } else if (cbList1.getValue().toString().equals(type1b)) {
                            tfThickness.setVisible(false);
                            tfThickness.setValue(null);
                            tfLength.setVisible(false);
                            tfLength.setValue(null);
                            tfWeight.setVisible(false);
                            tfWeight.setValue(null);
                            cbColor.setVisible(true);
                            tfColorNr.setVisible(true);
                            tfDesignation.setVisible(true);
                        }
                    });

                    tfThickness.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    tfWeight.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    tfLength.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
                    break;
                }
                case "WŁÓCZKA": {
                    cbColor = new ComboBox("Kolor");
                    for(Colors item : Colors.values()){
                        cbColor.addItem(item.displayName());
                    }
                    tfColorNr = new TextField("Kod koloru");
                    tfDesignation = new TextField("Firma(Nazwa)");
                    tfLength = new TextField("Długość [m]");
                    tfWeight = new TextField("Waga [g]");
                    specificFilteringLayout.addComponents(cbColor, tfColorNr, tfLength, tfWeight, tfDesignation, butFilter);
                    tfLength.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
                    tfWeight.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    break;
                }
                case "ROBÓTKI DZIEWIARSKIE": {
                    cbList1 = new ComboBox();
                    for(RobotkiDziewiarskieRodzaj_I item : RobotkiDziewiarskieRodzaj_I.values()){
                        cbList1.addItem(item.displayName());
                    }
                    specificFilteringLayout.addComponents(cbList1, butFilter);
                    break;
                }
                case "TAŚMY":{
                    cbList1 = new ComboBox("Rodzaj");
                    for(TasmyRodzaj_I item : TasmyRodzaj_I.values()){
                        cbList1.addItem(item.displayName());
                    }
                    cbColor = new ComboBox("Kolor");
                    for(Colors item : Colors.values()){
                        cbColor.addItem(item.displayName());
                    }
                    tfColorNr = new TextField("Kod koloru");
                    tfWidth = new TextField("Szerokość [mm]");
                    specificFilteringLayout.addComponents(cbList1, tfWidth, cbColor, tfColorNr, butFilter);
                    tfWidth.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    break;
                }
                case "ZAMKI I AKCESORIA":{
                    cbList1 = new ComboBox("Rodzaj");
                    for(ZamkiRodzaj_Ia item : ZamkiRodzaj_Ia.values()){
                        cbList1.addItem(item.displayName());
                    }
                    cbList2 = new ComboBox("");
                    cbList3 = new ComboBox("");
                    cbColor = new ComboBox("Kolor");
                    for(Colors item : Colors.values()){
                        cbColor.addItem(item.displayName());
                    }
                    tfColorNr = new TextField("Kod koloru");
                    tfThickness = new TextField("Grubość");
                    tfLength = new TextField("Długość [cm]");
                    specificFilteringLayout.addComponents(cbList1, cbList2, cbList3, tfThickness, tfLength, cbColor, tfColorNr, butFilter);

                    String type1a = ZamkiRodzaj_Ia.SUWAKI_KONIKI.displayName();
                    String type1b = ZamkiRodzaj_Ia.TASMY_SUWAKOWE.displayName();
                    String type1c = ZamkiRodzaj_Ia.ZAMKI_BLYSKAWICZNE.displayName();
                    String type2a = ZamkiRodzaj_IIa.KOSTKOWY.displayName();
                    String type2b = ZamkiRodzaj_IIa.KRYTY.displayName();
                    String type2c = ZamkiRodzaj_IIa.METALOWY.displayName();
                    String type2d = ZamkiRodzaj_IIa.POSCIELOWY.displayName();
                    String type2e = ZamkiRodzaj_IIa.ZYLKOWY.displayName();

                    cbList2.setVisible(false);
                    cbList3.setVisible(false);
                    tfColorNr.setVisible(false);
                    cbColor.setVisible(false);
                    tfLength.setVisible(false);

                    // Listeners
                    cbList1.addValueChangeListener(event -> {
                        if(cbList1.getValue().toString().equals(type1a)) {
                            cbList2.setValue(null);
                            cbList3.setValue(null);
                            cbList2.setCaption("Tworzywo");
                            for(ZamkiTworzywo item : ZamkiTworzywo.values()){
                                cbList2.addItem(item.displayName());
                            }
                            cbList2.setVisible(true);
                            cbList3.setVisible(false);
                            tfThickness.setVisible(true);
                            tfColorNr.setVisible(true);
                            cbColor.setVisible(true);
                            tfLength.setVisible(false);
                        }
                        if(cbList1.getValue().toString().equals(type1b)) {
                            cbList2.setValue(null);
                            cbList3.setValue(null);
                            cbList2.setVisible(false);
                            cbList3.setVisible(false);
                            tfThickness.setVisible(true);
                            tfColorNr.setVisible(true);
                            cbColor.setVisible(true);
                            tfLength.setVisible(false);
                        }
                        if(cbList1.getValue().toString().equals(type1c)) {
                            cbList2.setValue(null);
                            cbList3.setValue(null);
                            cbList2.setCaption("Rodzaj");
                            for(ZamkiRodzaj_IIa item : ZamkiRodzaj_IIa.values()){
                                cbList2.addItem(item.displayName());
                            }
                            cbList2.setVisible(true);
                            cbList3.setVisible(false);
                            tfColorNr.setVisible(false);
                            cbColor.setVisible(false);
                            tfLength.setVisible(false);
                        }
                    });

                    cbList2.addValueChangeListener(event -> {
                        if(cbList2.getValue().toString().equals(type2a)){
                            cbList3.setValue(null);
                            cbList2.setVisible(true);
                            cbList3.setCaption("Rodzaj");
                            for(ZamkiRodzaj_IIIa item : ZamkiRodzaj_IIIa.values()){
                                cbList3.addItem(item.displayName());
                            }
                            cbList3.setVisible(true);
                            tfThickness.setVisible(true);
                            tfColorNr.setVisible(true);
                            cbColor.setVisible(true);
                            tfLength.setVisible(true);
                        }
                        if(cbList2.getValue().toString().equals(type2b)){
                            cbList3.setValue(null);
                            cbList2.setVisible(true);
                            cbList3.setVisible(false);
                            tfThickness.setVisible(false);
                            tfColorNr.setVisible(true);
                            cbColor.setVisible(true);
                            tfLength.setVisible(true);
                        }
                        if(cbList2.getValue().toString().equals(type2c)){
                            cbList3.setValue(null);
                            cbList2.setVisible(true);
                            cbList3.setCaption("Rodzaj");
                            for(ZamkiRodzaj_IIIb item : ZamkiRodzaj_IIIb.values()){
                                cbList3.addItem(item.displayName());
                            }
                            cbList3.setVisible(true);
                            tfThickness.setVisible(true);
                            tfColorNr.setVisible(true);
                            cbColor.setVisible(true);
                            tfLength.setVisible(true);
                        }
                        if(cbList2.getValue().toString().equals(type2d)){
                            cbList3.setValue(null);
                            cbList2.setVisible(true);
                            cbList3.setVisible(false);
                            tfThickness.setVisible(false);
                            tfColorNr.setVisible(true);
                            cbColor.setVisible(true);
                            tfLength.setVisible(true);
                        }
                        if(cbList2.getValue().toString().equals(type2e)){
                            cbList3.setValue(null);
                            cbList2.setVisible(true);
                            cbList3.setCaption("Rodzaj");
                            for(ZamkiRodzaj_IIIc item : ZamkiRodzaj_IIIc.values()){
                                cbList3.addItem(item.displayName());
                            }
                            cbList3.setVisible(true);
                            tfThickness.setVisible(true);
                            tfColorNr.setVisible(true);
                            cbColor.setVisible(true);
                            tfLength.setVisible(true);
                        }
                    });
                    tfThickness.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    tfLength.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
                    break;
                }
                case "AKCESORIA KRAWIECKIE": {
                    cbList1 = new ComboBox("Rodzaj");
                    for(AkcesoriaKrawieckieRodzaj item : AkcesoriaKrawieckieRodzaj.values()){
                        cbList1.addItem(item.displayName());
                    }
                    specificFilteringLayout.addComponents(cbList1, butFilter);
                    break;
                }
                case "GUMY": {
                    cbList1 = new ComboBox("Rodzaj");
                    for(GumyRodzaj item : GumyRodzaj.values()){
                        cbList1.addItem(item.displayName());
                    }
                    cbColor = new ComboBox("Kolor");
                    for(Colors item : Colors.values()){
                        cbColor.addItem(item.displayName());
                    }
                    tfColorNr = new TextField("Kod koloru");
                    tfWidth = new TextField("Szerokość [mm]");
                    specificFilteringLayout.addComponents(cbList1, tfWidth, cbColor, tfColorNr, butFilter);
                    tfWidth.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    break;
                }
                case "RĘCZNIKI": {
                    //tfSize = new TextField("Rozmiar");
                    tfLengthInt = new TextField("Długość [cm]");
                    tfWidth = new TextField("Szerokość [cm]");
                    cbList1 = new ComboBox("Rodzaj");
                    cbColor = new ComboBox("Kolor");
                    for(Colors item : Colors.values()) {
                        cbColor.addItem(item.displayName());
                    }
                    tfColorNr = new TextField("Kod koloru");
                    for(RecznikiRodzaj item : RecznikiRodzaj.values()) {
                        cbList1.addItem(item.displayName());
                    }
                    specificFilteringLayout.addComponents(cbList1, cbColor, tfColorNr, tfWidth, tfLengthInt, butFilter);
                    tfWidth.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    tfLengthInt.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    break;
                }
                case "WSTĄŻKI": {
                    cbList1 = new ComboBox("Rodzaj");
                    for(WstazkiRodzaj item : WstazkiRodzaj.values()){
                        cbList1.addItem(item.displayName());
                    }
                    cbColor = new ComboBox("Kolor");
                    for(Colors item : Colors.values()){
                        cbColor.addItem(item.displayName());
                    }
                    tfColorNr = new TextField("Kod koloru");
                    tfWidth = new TextField("Szerokość");
                    specificFilteringLayout.addComponents(cbList1, tfWidth, cbColor, tfColorNr, butFilter);
                    tfWidth.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    break;
                }
                case "GUZIKI": {
                    tfSize = new TextField("Rozmiar");
                    specificFilteringLayout.addComponents(tfSize, butFilter);
                    tfSize.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
                    break;
                }
                case "AKCESORIA DO DEKORACJI": {
                    cbList1 = new ComboBox("Rodzaj");
                    for(AkcesoriaDoDekoracjiRodzaj item : AkcesoriaDoDekoracjiRodzaj.values()){
                        cbList1.addItem(item.displayName());
                    }
                    specificFilteringLayout.addComponents(cbList1, butFilter);
                    break;
                }
                case "AKCESORIA DO FIRAN": {
                    cbList1 = new ComboBox("Rodzaj");
                    for(AkcesoriaDoFiranRodzaj item : AkcesoriaDoFiranRodzaj.values()){
                        cbList1.addItem(item.displayName());
                    }
                    specificFilteringLayout.addComponents(cbList1, butFilter);
                    break;

                }
                case "AKCESORIA OBUWNICZE": {
                    cbList1 = new ComboBox("Rodzaj");
                    for(AkcesoriaObuwniczeRodzaj item : AkcesoriaObuwniczeRodzaj.values()){
                        cbList1.addItem(item.displayName());
                    }
                    specificFilteringLayout.addComponents(cbList1, butFilter);
                    break;
                }
                case "GALANTERIA": {
                    cbList1 = new ComboBox("Rodzaj");
                    for(GalanteriaRodzaj item : GalanteriaRodzaj.values()){
                        cbList1.addItem(item.displayName());
                    }
                    specificFilteringLayout.addComponents(cbList1, butFilter);
                    break;
                }
                case "AKCESORIA DO BIELIZNY": {
                    break;
                }
                case "DODATKI PLASTIKOWE": {
                    break;
                }
                case "POZOSTAŁE": {
                    break;
                }
                case "SZNURKI": {
                    tfLength = new TextField("Długość [m]");
                    specificFilteringLayout.addComponents(tfLength, butFilter);
                    tfLength.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
                    break;
                }
                case "ŁATY I ŁATKI": {
                    break;
                }
                default:
                    System.out.println("Nie ma takiej kategorii");
            }
        } else {
            specificFilteringLayout.removeAllComponents();
        }
    }

    private Object[][] processObjectToArray(List<Object[]> result) {
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


    private void addNewItemToContainer(Articles row) {
        Item newItem = container.getItem(container.addItem());
        newItem.getItemProperty("KodKreskowy").setValue(row.getBarcode());
        newItem.getItemProperty("NumerKatalogowy").setValue(row.getCatalogNumber());
        newItem.getItemProperty("NazwaArtykułu").setValue(row.getArticleName());
        newItem.getItemProperty("Ilość").setValue(row.getQuantity());
        newItem.getItemProperty("JM").setValue(row.getUnitOfMeasurement());
        newItem.getItemProperty("Cena").setValue(row.getPriceMargin());
    }
}



