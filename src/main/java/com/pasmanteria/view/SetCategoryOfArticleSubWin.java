package com.pasmanteria.view;

import com.pasmanteria.Enum.*;
import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.model.Category;
import com.pasmanteria.model.CategoryTmp;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.Getter;
import lombok.Setter;
import sun.applet.Main;

/**
 * Created by Mike on 07/02/2017.
 */
public class SetCategoryOfArticleSubWin extends Window {

    private VerticalLayout mainLayout;
    private HorizontalLayout listsLayout;
    private HorizontalLayout buttonsLayout;
    private HorizontalLayout categoryLayout;

    private Label labHeader;
    private Button butCancel;
    private Button butSelect;
    private Button butKoronki;
    private Button butNici;
    private Button butKordonkiMulina;
    private Button butWloczka;
    private Button butRobotkiDziewiarskie;
    private Button butSznurki;
    private Button butTasmy;
    private Button butZamki;
    private Button butLatyLatki;
    private Button butAkcesoriaKrawieckie;
    private Button butGumy;
    private Button butReczniki;
    private Button butWstazki;
    private Button butGuziki;
    private Button butPozostale;
    private Button butAkcesoriaDoBielizny;
    private Button butAkcesoriaDoDekoracji;
    private Button butAkcesoriaDoFiran;
    private Button butAkcesoriaObuwnicze;
    private Button butDodatkiPlastikowe;
    private Button butGalanteria;

    @Getter @Setter private Boolean isAccept;
    @Getter @Setter private CategoryTmp ct;
    @Getter @Setter private String categoryTxt;

    public SetCategoryOfArticleSubWin() {
        super("Przypisywanie kategorii"); // Set window caption
        setWidth("1000");
        center();
        //init();
    }

    public void init() {
        // Create
        isAccept = false;
        mainLayout = new VerticalLayout();
        categoryLayout = new HorizontalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label("Kategoria");
        butKoronki = new Button(MainCategories.KORONKI.displayName());
        butNici = new Button(MainCategories.NICI.displayName());
        butKordonkiMulina = new Button(MainCategories.KORDONKI_I_MULINA.displayName());
        butWloczka = new Button(MainCategories.WLOCZKA.displayName());
        butRobotkiDziewiarskie = new Button(MainCategories.ROBOTKI_DZIEWIARSKIE.displayName());
        butSznurki = new Button(MainCategories.SZNURKI.displayName());
        butTasmy = new Button(MainCategories.TASMY.displayName());
        butZamki = new Button(MainCategories.ZAMKI_I_AKCESORIA.displayName());
        butLatyLatki = new Button(MainCategories.LATY_I_LATKI.displayName());
        butAkcesoriaKrawieckie = new Button(MainCategories.AKCESORIA_KRAWIECKIE.displayName());
        butGumy = new Button(MainCategories.GUMY.displayName());
        butReczniki = new Button(MainCategories.RECZNIKI.displayName());
        butWstazki = new Button(MainCategories.WSTAZKI.displayName());
        butGuziki = new Button(MainCategories.GUZIKI.displayName());
        butPozostale = new Button(MainCategories.POZOSTALE.displayName());
        butAkcesoriaDoBielizny = new Button(MainCategories.AKCESORIA_DO_BIELIZNY.displayName());
        butAkcesoriaDoDekoracji = new Button(MainCategories.AKCESORIA_DO_DEKORACJI.displayName());
        butAkcesoriaDoFiran = new Button(MainCategories.AKCESORIA_DO_FIRAN.displayName());
        butAkcesoriaObuwnicze = new Button(MainCategories.AKCESORIA_OBUWNICZE.displayName());
        butDodatkiPlastikowe = new Button(MainCategories.DODATKI_PLASTIKOWE.displayName());
        butGalanteria = new Button(MainCategories.GALANTERIA.displayName());
        butCancel = new Button("Anuluj");

        // Add
        categoryLayout.addComponents(butNici, butKoronki, butKordonkiMulina, butWloczka,
                butRobotkiDziewiarskie, butSznurki, butTasmy, butZamki, butLatyLatki, butAkcesoriaKrawieckie,
                butGumy, butReczniki, butWstazki, butGuziki, butPozostale, butAkcesoriaDoBielizny,
                butAkcesoriaDoDekoracji, butAkcesoriaDoFiran, butAkcesoriaObuwnicze, butDodatkiPlastikowe,
                butGalanteria);
        buttonsLayout.addComponents(butCancel);
        mainLayout.addComponents(labHeader, categoryLayout, buttonsLayout);

        // Set
        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        listsLayout.setSpacing(true);
        categoryLayout.setSpacing(true);
        categoryLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);

        butCancel.addClickListener(event -> close());
        butKoronki.addClickListener(event ->  koronki());
        butNici.addClickListener(event ->  nici());
        butKordonkiMulina.addClickListener(event -> kordonkiIMulina());
        butWloczka.addClickListener(event -> wloczka());
        butRobotkiDziewiarskie.addClickListener(event -> robotkiDziewiarskie());
        butSznurki.addClickListener(event -> sznurki());
        butTasmy.addClickListener(event -> tasmy());
        butZamki.addClickListener(event -> zamki());
        butLatyLatki.addClickListener(event -> latyLatki());
        butAkcesoriaKrawieckie.addClickListener(event -> akcesoriaKrawieckie());
        butGumy.addClickListener(event -> gumy());
        butReczniki.addClickListener(event -> reczniki());
        butWstazki.addClickListener(event -> wstazki());
        butGuziki.addClickListener(event -> guziki());
        butPozostale.addClickListener(event -> pozostale());
        butAkcesoriaDoBielizny.addClickListener(event -> akcesoriaDoBielizny());
        butAkcesoriaDoDekoracji.addClickListener(event -> akcesoriaDoDekoracji());
        butAkcesoriaDoFiran.addClickListener(event -> akcesoriaDoFiran());
        butAkcesoriaObuwnicze.addClickListener(event -> akcesoriaObuwnicze());
        butDodatkiPlastikowe.addClickListener(event -> dodatkiPlastikowe());
        butGalanteria.addClickListener(event -> galanteria());

    } //end init

    public void koronki(){
        // Create
        String mainCategory = MainCategories.KORONKI.displayName();
        TextField tfWidth = new TextField("Szerokość [mm]");
        ListSelect lisSel1 = new ListSelect("Rodzaj");

        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label("Koronki");
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");

        // Add
        for(KoronkiRodzaj item : KoronkiRodzaj.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1,tfWidth);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);
        setContent(mainLayout);

        // Set
        tfWidth.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfWidth.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));

        lisSel1.setNullSelectionAllowed(true);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        listsLayout.setSpacing(true);
        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> {
            isAccept = false;
            close();
        });

        butSelect.addClickListener(event -> {
            if(lisSel1.isValid() && tfWidth.isValid()) {
                String type1 = lisSel1.getValue().toString();
                Integer width = Integer.parseInt(tfWidth.getValue());
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, width.toString());

                ct = new CategoryTmp(null, null, mainCategory,
                type1, null, null, null, null, null,
                null, null, null, null, width, null, null,
                null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void nici(){
        // Create
        String mainCategory = MainCategories.NICI.displayName();
        isAccept = false;
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        ListSelect lisSel1 = new ListSelect("Rodzaj");
        ListSelect lisSel2 = new ListSelect("Rodzaj");
        ListSelect lisSelColor = new ListSelect("Kolor");
        TextField tfColorNr = new TextField("Kod koloru");
        TextField tfThickness = new TextField("Grubość");
        TextField tfDesignation = new TextField("Firma(Nazwa)");
        TextField tfLength = new TextField("Długość [m]");

        // Add
        for(NiciRodzaj_I item : NiciRodzaj_I.values()){
            lisSel1.addItem(item.displayName());
        }
        for(NiciRodzaj_II item : NiciRodzaj_II.values()){
            lisSel2.addItem(item.displayName());
        }
        for(Colors item : Colors.values()){
            lisSelColor.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1, lisSel2, lisSelColor, tfColorNr, tfLength, tfThickness, tfDesignation);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSel2.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSelColor.addValidator(new NullValidator("Pole nie może być puste.", false));
        tfColorNr.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfThickness.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfThickness.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfLength.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfLength.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfDesignation.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        listsLayout.setSpacing(true);
        lisSel1.setNullSelectionAllowed(true);
        lisSel2.setVisible(false);
        lisSelColor.setVisible(false);
        tfColorNr.setVisible(false);
        tfLength.setVisible(false);
        tfThickness.setVisible(false);
        tfDesignation.setVisible(false);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        lisSel1.addValueChangeListener(event -> {
            if(lisSel1.getValue().toString().equals(NiciRodzaj_I.SPECJALNE.displayName())){
                lisSel2.setVisible(true);
                lisSelColor.setVisible(true);
                tfColorNr.setVisible(true);
                tfLength.setVisible(true);
                tfThickness.setVisible(true);
                tfDesignation.setVisible(true);
            } else {
                lisSel2.setValue(null);
                lisSel2.setVisible(false);
                lisSelColor.setVisible(true);
                tfColorNr.setVisible(true);
                tfLength.setVisible(true);
                tfThickness.setVisible(true);
                tfDesignation.setVisible(true);
            }
        });

        butCancel.addClickListener(event -> {
            isAccept = false;
            close();
        });

        butSelect.addClickListener(event -> {
            String type1;
            String type2;
            String color;
            String nrColor;
            Float length;
            Integer thickness;
            String designation;

            if(lisSel1.isValid()) {
                if (lisSel1.getValue().toString().equals(NiciRodzaj_I.SPECJALNE.displayName())) {
                    if (lisSel1.isValid() &&
                            lisSel2.isValid() &&
                            lisSelColor.isValid() &&
                            tfColorNr.isValid() &&
                            tfLength.isValid() &&
                            tfThickness.isValid() &&
                            tfDesignation.isValid()
                            ) {

                        type1 = lisSel1.getValue().toString();
                        type2 = lisSel2.getValue().toString();
                        color = lisSelColor.getValue().toString();
                        nrColor = tfColorNr.getValue().toString();
                        length = Float.parseFloat(tfLength.getValue().toString());
                        thickness = Integer.parseInt(tfThickness.getValue().toString());
                        designation = tfDesignation.getValue().toString();

                        categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, type2, color, nrColor, length.toString(), thickness.toString(), designation);

                        ct = new CategoryTmp(null, null, mainCategory,
                                type1, type2, null, color, nrColor, null,
                                null, null, thickness, designation, null, length, null,
                                null, null);
                        isAccept = true;
                        close();
                    } else {
                        Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
                    }
                } else {
                    if (lisSel1.isValid() &&
                            lisSelColor.isValid() &&
                            tfColorNr.isValid() &&
                            tfLength.isValid() &&
                            tfThickness.isValid() &&
                            tfDesignation.isValid()
                            ) {
                        type1 = lisSel1.getValue().toString();
                        color = lisSelColor.getValue().toString();
                        nrColor = tfColorNr.getValue().toString();
                        length = Float.parseFloat(tfLength.getValue().toString());
                        thickness = Integer.parseInt(tfThickness.getValue().toString());
                        designation = tfDesignation.getValue().toString();

                        categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, color, nrColor, length.toString(), thickness.toString(), designation);

                        ct = new CategoryTmp(null, null, mainCategory,
                                type1, null, null, color, nrColor, null,
                                null, null, thickness, designation, null, length, null,
                                null, null);
                        isAccept = true;
                        close();
                    } else {
                        Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
                    }
                }
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void kordonkiIMulina(){
        // Create
        String mainCategory = MainCategories.KORDONKI_I_MULINA.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        ListSelect lisSel1 = new ListSelect("Rodzaj");
        ListSelect lisSelColor = new ListSelect("Kolor");
        TextField tfColor = new TextField("Kod koloru");
        TextField tfLength = new TextField("Długość [m]");
        TextField tfWeight = new TextField("Waga [g]");
        TextField tfThickness = new TextField("Grubość");
        TextField tfDesignation = new TextField("Firma(Nazwa)");
        String type1a = KordonkiMulinaRodzaj_I.KORDONKI.displayName();
        String type1b = KordonkiMulinaRodzaj_I.MULINA.displayName();


        // Add
        lisSel1.addItems(type1a, type1b);
        for(Colors item : Colors.values()){
            lisSelColor.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1, lisSelColor, tfColor, tfLength, tfWeight, tfThickness, tfDesignation);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSelColor.addValidator(new NullValidator("Pole nie może być puste.", false));
        tfColor.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfLength.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?", "Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfLength.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfWeight.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfWeight.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfThickness.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfThickness.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfDesignation.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        lisSel1.setNullSelectionAllowed(true);

        lisSelColor.setVisible(false);
        tfColor.setVisible(false);
        tfLength.setVisible(false);
        tfWeight.setVisible(false);
        tfThickness.setVisible(false);
        tfDesignation.setVisible(false);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        lisSel1.addValueChangeListener(event -> {
            if(lisSel1.getValue().toString().equals(type1a)){
                lisSelColor.setVisible(true);
                tfColor.setVisible(true);
                tfLength.setVisible(true);
                tfWeight.setVisible(true);
                tfThickness.setVisible(true);
                tfDesignation.setVisible(true);
            } else if(lisSel1.getValue().toString().equals(type1b)) {
                tfLength.setVisible(false);
                tfLength.setValue(null);
                tfWeight.setVisible(false);
                tfWeight.setValue(null);
                tfThickness.setVisible(false);
                tfThickness.setValue(null);
                lisSelColor.setVisible(true);
                tfColor.setVisible(true);
                tfDesignation.setVisible(true);
            }
        });

        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
            String type1;
            String color;
            String nrColor;
            String designation;
            Float length;
            Integer weight;
            Integer thickness;

            if(lisSel1.isValid()) {
                if (lisSel1.getValue().toString().equals(type1a)) {
                    if (lisSel1.isValid() &&
                            lisSelColor.isValid() &&
                            tfColor.isValid() &&
                            tfLength.isValid() &&
                            tfWeight.isValid() &&
                            tfThickness.isValid() &&
                            tfDesignation.isValid()
                            ) {
                        type1 = lisSel1.getValue().toString();
                        color = lisSelColor.getValue().toString();
                        nrColor = tfColor.getValue().toString();
                        designation = tfDesignation.getValue().toString();
                        thickness = Integer.parseInt(tfThickness.getValue().toString());
                        length = Float.parseFloat(tfLength.getValue().toString());
                        weight = Integer.parseInt(tfWeight.getValue().toString());

                        categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, color, nrColor, length.toString(), weight.toString(), thickness.toString(), designation);
                        ct = new CategoryTmp(null, null, mainCategory,
                                type1, null, null, color, nrColor, null,
                                null, null, thickness, designation, null, length, weight,
                                null, null);
                        isAccept = true;
                        close();
                    } else {
                        Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
                    }
                } else if (lisSel1.getValue().toString().equals(type1b)) {
                    if (lisSel1.isValid() &&
                            lisSelColor.isValid()&&
                            tfColor.isValid() &&
                            tfDesignation.isValid()
                            ) {
                        type1 = lisSel1.getValue().toString();
                        color = lisSelColor.getValue().toString();
                        nrColor = tfColor.getValue().toString();
                        designation = tfDesignation.getValue().toString();

                        categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, color, designation);
                        ct = new CategoryTmp(null, null, mainCategory,
                                type1, null, null, color, nrColor, null,
                                null, null, null, designation, null, null, null, null, null);
                        isAccept = true;
                        close();
                    } else {
                        Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
                    }
                }
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void wloczka(){
        // Create
        String mainCategory = MainCategories.WLOCZKA.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        ListSelect lisSelColor = new ListSelect("Kolor");
        TextField tfColor = new TextField("Kod koloru");
        TextField tfLength = new TextField("Długość [m]");
        TextField tfWeight = new TextField("Waga [g]");
        TextField tfDesignation = new TextField("Firma(Nazwa)");
        
        // Add
        for(Colors item : Colors.values()){
            lisSelColor.addItem(item.displayName());
        }
        listsLayout.addComponents(tfDesignation, lisSelColor, tfColor, tfLength, tfWeight);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        lisSelColor.addValidator(new NullValidator("Pole nie może być puste.", false));
        tfColor.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfDesignation.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfLength.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfLength.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfWeight.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));        tfDesignation.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfWeight.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
            if(lisSelColor.isValid() &&
                    tfDesignation.isValid() &&
                    tfColor.isValid() &&
                    tfLength.isValid() &&
                    tfWeight.isValid()) {

                String color = lisSelColor.getValue().toString();
                String nrColor = tfColor.getValue();
                String designation = tfDesignation.getValue();
                Float length = Float.parseFloat(tfLength.getValue());
                Integer weight = Integer.parseInt(tfWeight.getValue());
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, color, nrColor, designation, length.toString(), weight.toString());

                ct = new CategoryTmp(null, null, mainCategory,
                        null, null, null, color, nrColor, null,
                        null, null, null, designation, null, length, weight,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void robotkiDziewiarskie(){
        // Create
        String mainCategory = MainCategories.ROBOTKI_DZIEWIARSKIE.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        ListSelect lisSel1 = new ListSelect("Rodzaj");

        // Add
        for(RobotkiDziewiarskieRodzaj_I item : RobotkiDziewiarskieRodzaj_I.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
            if(lisSel1.isValid()) {
                String type = lisSel1.getValue().toString();
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type);
                ct = new CategoryTmp(null, null, mainCategory,
                        type, null, null, null, null, null,
                        null, null, null, null, null, null, null,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void sznurki(){
        // Create
        String mainCategory = MainCategories.SZNURKI.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        TextField tfLength = new TextField("Długość [m]");

        // Add
        listsLayout.addComponents(tfLength);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        tfLength.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfLength.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
            if(tfLength.isValid()) {
                Float length = Float.parseFloat(tfLength.getValue());
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, length.toString());
                ct = new CategoryTmp(null, null, mainCategory,
                        null, null, null, null, null, null,
                        null, null, null, null, null, length, null,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    //tu skonczyłem

    public void tasmy(){
        // Create
        String mainCategory = MainCategories.TASMY.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        TextField tfWidth = new TextField("Szerokość [mm]");
        ListSelect lisSel1 = new ListSelect("Rodzaj");
        ListSelect lisSelColor = new ListSelect("Kolor");
        TextField tfColor = new TextField("Kod koloru");

        // Add
        for(Colors item : Colors.values()){
            lisSelColor.addItem(item.displayName());
        }
        for(TasmyRodzaj_I item : TasmyRodzaj_I.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1, lisSelColor, tfColor, tfWidth);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSelColor.addValidator(new NullValidator("Pole nie może być puste.", false));
        tfColor.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfWidth.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfWidth.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        lisSel1.setNullSelectionAllowed(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
                if(lisSel1.isValid() &&
                        lisSelColor.isValid() &&
                        tfColor.isValid() &&
                        tfWidth.isValid()) {
                    String type = lisSel1.getValue().toString();
                    String color = lisSelColor.getValue().toString();
                    String nrColor = tfColor.getValue().toString();
                    Integer width = Integer.parseInt(tfWidth.getValue().toString());
                    categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type, color, nrColor, width.toString());

                    ct = new CategoryTmp(null, null, mainCategory,
                            type, null, null, color, nrColor, null,
                            null, null, null, null, width, null, null,
                            null, null);
                    isAccept = true;
                    close();
                } else {
                    Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
                }
        });
    }


    public void zamki(){
        // Create
        String mainCategory = MainCategories.ZAMKI_I_AKCESORIA.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        TextField tfLength = new TextField("Długość [cm]");
        ListSelect lisSelColor = new ListSelect("Kolor");
        TextField tfColor = new TextField("Kod koloru");
        ListSelect lisSel1 = new ListSelect("Rodzaj");
        ListSelect lisSel2 = new ListSelect("Zamek");
        ListSelect lisSel3 = new ListSelect("");
        ListSelect lisSel4 = new ListSelect("");
        ListSelect lisSel5 = new ListSelect("");
        //ListSelect lisSel6 = new ListSelect("Grubość");
        TextField tfThickness = new TextField("Grubość [mm]");
        ListSelect lisSel7 = new ListSelect("Tworzywo");
        String type1a = ZamkiRodzaj_Ia.SUWAKI_KONIKI.displayName();
        String type1b = ZamkiRodzaj_Ia.TASMY_SUWAKOWE.displayName();
        String type1c = ZamkiRodzaj_Ia.ZAMKI_BLYSKAWICZNE.displayName();
        String type2a = ZamkiRodzaj_IIa.KOSTKOWY.displayName();
        String type2b = ZamkiRodzaj_IIa.KRYTY.displayName();
        String type2c = ZamkiRodzaj_IIa.METALOWY.displayName();
        String type2d = ZamkiRodzaj_IIa.POSCIELOWY.displayName();
        String type2e = ZamkiRodzaj_IIa.ZYLKOWY.displayName();

        // Add
        for(ZamkiRodzaj_Ia item : ZamkiRodzaj_Ia.values()){
            lisSel1.addItem(item.displayName());
        }
        for(ZamkiRodzaj_IIa item : ZamkiRodzaj_IIa.values()){
            lisSel2.addItem(item.displayName());
        }
        for(ZamkiRodzaj_IIIa item : ZamkiRodzaj_IIIa.values()){
            lisSel3.addItem(item.displayName());
        }
        for(ZamkiRodzaj_IIIb item : ZamkiRodzaj_IIIb.values()){
            lisSel4.addItem(item.displayName());
        }
        for(ZamkiRodzaj_IIIc item : ZamkiRodzaj_IIIc.values()){
            lisSel5.addItem(item.displayName());
        }
        /*
        for(ZamkiGrubosc item : ZamkiGrubosc.values()){
            lisSel6.addItem(item.displayName());
        }
        */
        for(ZamkiTworzywo item : ZamkiTworzywo.values()){
            lisSel7.addItem(item.displayName());
        }
        for(Colors item : Colors.values()){
            lisSelColor.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1, lisSel7, lisSel2, lisSel3, lisSel4, lisSel5, tfThickness, lisSelColor, tfColor, tfLength);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader, listsLayout, buttonsLayout);

        // Set
        tfColor.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfLength.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfLength.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfThickness.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfThickness.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        lisSelColor.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSel2.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSel3.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSel4.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSel5.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSel7.addValidator(new NullValidator("Pole nie może być puste.", false));


        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        listsLayout.setSpacing(true);
        lisSel1.setNullSelectionAllowed(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        lisSel2.setVisible(false);
        lisSel3.setVisible(false);
        lisSel4.setVisible(false);
        lisSel5.setVisible(false);
        //lisSel6.setVisible(false);
        tfThickness.setVisible(false);
        lisSel7.setVisible(false);
        lisSelColor.setVisible(false);
        tfColor.setVisible(false);
        tfLength.setVisible(false);

        // Listeners
        lisSel1.addValueChangeListener(event -> {
            if(lisSel1.getValue().toString().equals(type1a)) {
                lisSel2.setVisible(false);
                lisSel3.setVisible(false);
                lisSel4.setVisible(false);
                lisSel5.setVisible(false);
                //lisSel6.setVisible(true);
                tfThickness.setVisible(true);
                lisSel7.setVisible(true);
                lisSelColor.setVisible(true);
                tfColor.setVisible(true);
                tfLength.setVisible(false);
            }
            if(lisSel1.getValue().toString().equals(type1b)) {
                lisSel2.setVisible(false);
                lisSel3.setVisible(false);
                lisSel4.setVisible(false);
                lisSel5.setVisible(false);
                //lisSel6.setVisible(true);
                tfThickness.setVisible(true);
                lisSel7.setVisible(false);
                lisSelColor.setVisible(true);
                tfColor.setVisible(true);
                tfLength.setVisible(false);
            }
            if(lisSel1.getValue().toString().equals(type1c)) {
                lisSel2.setVisible(true);
                lisSel3.setVisible(false);
                lisSel4.setVisible(false);
                lisSel5.setVisible(false);
                //lisSel6.setVisible(false);
                tfThickness.setVisible(false);
                lisSel7.setVisible(false);
                lisSelColor.setVisible(false);
                tfColor.setVisible(false);
                tfLength.setVisible(false);
            }
        });

        lisSel2.addValueChangeListener(event -> {
            if(lisSel2.getValue().toString().equals(type2a)){
                lisSel2.setVisible(true);
                lisSel3.setVisible(true);
                lisSel4.setVisible(false);
                lisSel5.setVisible(false);
                //lisSel6.setVisible(true);
                tfThickness.setVisible(true);
                lisSel7.setVisible(false);
                lisSelColor.setVisible(true);
                tfColor.setVisible(true);
                tfLength.setVisible(true);
            }
            if(lisSel2.getValue().toString().equals(type2b)){
                lisSel2.setVisible(true);
                lisSel3.setVisible(false);
                lisSel4.setVisible(false);
                lisSel5.setVisible(false);
                //lisSel6.setVisible(false);
                tfThickness.setVisible(false);
                lisSel7.setVisible(false);
                lisSelColor.setVisible(true);
                tfColor.setVisible(true);
                tfLength.setVisible(true);
            }
            if(lisSel2.getValue().toString().equals(type2c)){
                lisSel2.setVisible(true);
                lisSel3.setVisible(false);
                lisSel4.setVisible(true);
                lisSel5.setVisible(false);
                //lisSel6.setVisible(true);
                tfThickness.setVisible(true);
                lisSel7.setVisible(false);
                lisSelColor.setVisible(true);
                tfColor.setVisible(true);
                tfLength.setVisible(true);
            }
            if(lisSel2.getValue().toString().equals(type2d)){
                lisSel2.setVisible(true);
                lisSel3.setVisible(false);
                lisSel4.setVisible(false);
                lisSel5.setVisible(false);
                //lisSel6.setVisible(false);
                tfThickness.setVisible(false);
                lisSel7.setVisible(false);
                lisSelColor.setVisible(true);
                tfColor.setVisible(true);
                tfLength.setVisible(true);
            }
            if(lisSel2.getValue().toString().equals(type2e)){
                lisSel2.setVisible(true);
                lisSel3.setVisible(false);
                lisSel4.setVisible(false);
                lisSel5.setVisible(true);
                //lisSel6.setVisible(true);
                tfThickness.setVisible(true);
                lisSel7.setVisible(false);
                lisSelColor.setVisible(true);
                tfColor.setVisible(true);
                tfLength.setVisible(true);
            }
        });

        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
                String type1;
                String type2;
                String type3;
                String type4;
                String type5;
                //String type6;
                Integer thickness;
                String type7;
                String color;
                String nrColor;
                Float length;

                if(lisSel1.isValid() &&
                        //&& lisSel6.getValue() != null
                        tfThickness.isValid() &&
                        lisSel7.isValid() &&
                        lisSelColor.isValid() &&
                        tfColor.isValid()
                        ) {
                    if(lisSel1.getValue().toString().equals(type1a)){
                        type1 = lisSel1.getValue().toString();
                        //type6 = lisSel6.getValue().toString();
                        thickness = Integer.parseInt(tfThickness.getValue().toString());
                        type7 = lisSel7.getValue().toString();
                        color = lisSelColor.getValue().toString();
                        nrColor = tfColor.getValue().toString();
                        categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, type7, thickness.toString(), color, nrColor);
                        System.out.println(categoryTxt);
                        ct = new CategoryTmp(null, null, mainCategory,
                                type1, type7, null, color, nrColor, null,
                                null, null, thickness, null, null, null, null,
                                null, null);
                        isAccept = true;
                        close();
                    }
                } else if(lisSel1.isValid() &&
                        //&& lisSel6.getValue() != null
                        tfThickness.isValid() &&
                        lisSelColor.isValid() &&
                        tfColor.isValid()
                        ) {
                    if(lisSel1.getValue().toString().equals(type1b)) {
                        type1 = lisSel1.getValue().toString();
                        //type6 = lisSel6.getValue().toString();
                        thickness = Integer.parseInt(tfThickness.getValue().toString());
                        color = lisSelColor.getValue().toString();
                        nrColor = tfColor.getValue().toString();
                        categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, thickness.toString(), color, nrColor);
                        System.out.println(categoryTxt);
                        ct = new CategoryTmp(null, null, mainCategory,
                                type1, null, null, color, nrColor, null,
                                null, null, thickness, null, null, null, null, null, null);
                        isAccept = true;
                        close();
                    }
                } else if(lisSel1.isValid()) {
                    if (lisSel1.getValue().toString().equals(type1c)) {
                        if (lisSel2.isValid() &&
                                lisSel3.isValid() &&
                                //&& lisSel6.getValue() != null
                                tfThickness.isValid() &&
                                lisSelColor.isValid() &&
                                tfColor.isValid() &&
                                tfLength.isValid()
                                ) {
                            if (lisSel2.getValue().toString().equals(type2a)) {
                                //kostkowy
                                type1 = lisSel1.getValue().toString();
                                type2 = lisSel2.getValue().toString();
                                type3 = lisSel3.getValue().toString();
                                //type6 = lisSel6.getValue().toString();
                                thickness = Integer.parseInt(tfThickness.getValue().toString());
                                color = lisSelColor.getValue().toString();
                                nrColor = tfColor.getValue().toString();
                                length = Float.parseFloat(tfLength.getValue().toString());
                                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, type2, type3, thickness.toString(), length.toString(), color, nrColor);
                                System.out.println(categoryTxt);
                                ct = new CategoryTmp(null, null, mainCategory,
                                        type1, type2, type3, color, nrColor, null,
                                        null, null, thickness, null, null, length, null, null, null);
                                isAccept = true;
                                close();
                            }
                        } else if (lisSel2.isValid() &&
                                lisSelColor.isValid() &&
                                tfColor.isValid() &&
                                tfLength.isValid()
                                ) {
                            if (lisSel2.getValue().toString().equals(type2b)) {
                                //kryty
                                type1 = lisSel1.getValue().toString();
                                type2 = lisSel2.getValue().toString();
                                color = lisSelColor.getValue().toString();
                                nrColor = tfColor.getValue().toString();
                                length = Float.parseFloat(tfLength.getValue().toString());
                                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, type2, length.toString(),color, nrColor);
                                System.out.println(categoryTxt);
                                ct = new CategoryTmp(null, null, mainCategory,
                                        type1, type2, null, color, nrColor, null,
                                        null, null, null, null, null, length, null, null, null);
                                isAccept = true;
                                close();
                            }
                        } else if (lisSel2.isValid() &&
                                lisSel4.isValid() &&
                                //&& lisSel6.getValue() != null
                                tfThickness.isValid() &&
                                lisSelColor.isValid() &&
                                tfColor.isValid() &&
                                tfLength.isValid()
                                ) {
                            if (lisSel2.getValue().toString().equals(type2c)) {
                                //metalowy
                                type1 = lisSel1.getValue().toString();
                                type2 = lisSel2.getValue().toString();
                                type4 = lisSel4.getValue().toString();
                                //type6 = lisSel6.getValue().toString();
                                thickness = Integer.parseInt(tfThickness.getValue().toString());
                                color = lisSelColor.getValue().toString();
                                nrColor = tfColor.getValue().toString();
                                length = Float.parseFloat(tfLength.getValue().toString());
                                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, type2, type4, thickness.toString(), length.toString(), color, nrColor);
                                System.out.println(categoryTxt);
                                ct = new CategoryTmp(null, null, mainCategory,
                                        type1, type2, type4, color, nrColor, null,
                                        null, null, thickness, null, null, length, null, null, null);
                                isAccept = true;
                                close();
                            }
                        } else if (lisSel2.isValid() &&
                                lisSelColor.isValid() &&
                                tfColor.isValid() &&
                                tfLength.isValid()
                                ) {
                            if (lisSel2.getValue().toString().equals(type2d)) {
                                //poscielowy
                                type1 = lisSel1.getValue().toString();
                                type2 = lisSel2.getValue().toString();
                                color = lisSelColor.getValue().toString();
                                nrColor = tfColor.getValue().toString();
                                length = Float.parseFloat(tfLength.getValue().toString());
                                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, type2, length.toString(), color, nrColor);
                                System.out.println(categoryTxt);
                                ct = new CategoryTmp(null, null, mainCategory,
                                        type1, type2, null, color, nrColor, null,
                                        null, null, null, null, null, length, null, null, null);
                                isAccept = true;
                                close();
                            }
                        } else if (lisSel2.isValid() &&
                                lisSel5.isValid() &&
                                lisSelColor.isValid() &&
                                tfColor.isValid() &&
                                tfLength.isValid()
                                ) {
                            if (lisSel2.getValue().toString().equals(type2e)) {
                                //zylkowy
                                type1 = lisSel1.getValue().toString();
                                type2 = lisSel2.getValue().toString();
                                type5 = lisSel5.getValue().toString();
                                //type6 = lisSel6.getValue().toString();
                                thickness = Integer.parseInt(tfThickness.getValue().toString());
                                color = lisSelColor.getValue().toString();
                                nrColor = tfColor.getValue().toString();
                                length = Float.parseFloat(tfLength.getValue().toString());
                                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type1, type2, type5, thickness.toString(), length.toString(), color, nrColor);
                                System.out.println(categoryTxt);
                                ct = new CategoryTmp(null, null, mainCategory,
                                        type1, type2, type5, color, nrColor, null,
                                        null, null, thickness, null, null, length, null, null, null);
                                isAccept = true;
                                close();
                            }
                        }
                    }
                } else {
                    Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
                }
        });
    }

    public void latyLatki(){
        String mainCategory = MainCategories.LATY_I_LATKI.displayName();
        categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory);

        ct = new CategoryTmp(null, null, mainCategory,
                null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null);
        isAccept = true;
        close();
    }

    public void akcesoriaKrawieckie(){
        // Create
        String mainCategory = MainCategories.AKCESORIA_KRAWIECKIE.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        ListSelect lisSel1 = new ListSelect("Rodzaj");

        // Add
        for(AkcesoriaKrawieckieRodzaj item : AkcesoriaKrawieckieRodzaj.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        lisSel1.setNullSelectionAllowed(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
            if(lisSel1.isValid()) {
                String type = lisSel1.getValue().toString();
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type);
                ct = new CategoryTmp(null, null, mainCategory,
                        type, null, null, null, null, null,
                        null, null, null, null, null, null, null,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void gumy(){
        // Create
        String mainCategory = MainCategories.GUMY.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        TextField tfWidth = new TextField("Szerokość [mm]");
        ListSelect lisSel1 = new ListSelect("Rodzaj");
        ListSelect lisSelColor = new ListSelect("Kolor");
        TextField tfColor = new TextField("Kod koloru");

        // Add
        for(Colors item : Colors.values()){
            lisSelColor.addItem(item.displayName());
        }
        for(GumyRodzaj item : GumyRodzaj.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1, lisSelColor, tfColor, tfWidth);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        tfColor.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfWidth.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfWidth.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSelColor.addValidator(new NullValidator("Pole nie może być puste.", false));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        lisSel1.setNullSelectionAllowed(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {

            if(lisSel1.isValid() &&
                    lisSelColor.isValid() &&
                    tfColor.isValid() &&
                    tfWidth.isValid()) {

                String type = lisSel1.getValue().toString();
                String color = lisSelColor.getValue().toString();
                String nrColor = tfColor.getValue().toString();
                Integer width = Integer.parseInt(tfWidth.getValue().toString());
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type, color, width.toString());

                ct = new CategoryTmp(null, null, mainCategory,
                        type, null, null, color, nrColor, null,
                        null, null, null, null, width, null, null,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void reczniki(){
        // Create
        String mainCategory = MainCategories.RECZNIKI.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        TextField tfWidth = new TextField("Szerokość [cm]");
        TextField tfLength = new TextField("Długość [cm]");
        ListSelect lisSel1 = new ListSelect("Rodzaj");
        ListSelect lisSelColor = new ListSelect("Kolor");
        TextField tfColor = new TextField("Kod koloru");

        // Add
        for(Colors item : Colors.values()){
            lisSelColor.addItem(item.displayName());
        }
        for(RecznikiRodzaj item : RecznikiRodzaj.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1, lisSelColor, tfColor, tfWidth, tfLength);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        tfColor.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfWidth.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfWidth.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfLength.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));
        tfLength.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSelColor.addValidator(new NullValidator("Pole nie może być puste.", false));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        lisSel1.setNullSelectionAllowed(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
           if(lisSel1.isValid() &&
                    lisSelColor.isValid() &&
                    tfColor.isValid() &&
                    tfWidth.isValid() &&
                    tfLength.isValid()) {

               String type = lisSel1.getValue().toString();
               String color = lisSelColor.getValue().toString();
               String nrColor = tfColor.getValue().toString();
               Integer width = Integer.parseInt(tfWidth.getValue().toString());
               Integer length = Integer.parseInt(tfLength.getValue().toString());
               categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type, color, nrColor, width.toString(), length.toString());

               ct = new CategoryTmp(null, null, mainCategory,
                        type, null, null, color, nrColor, null,
                        null, null, null, null, width, null, null, length,
                        null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void wstazki(){
        // Create
        String mainCategory = MainCategories.WSTAZKI.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        ListSelect lisSel1 = new ListSelect("Rodzaj");
        ListSelect lisSelColor = new ListSelect("Kolor");
        TextField tfColor = new TextField("Kod koloru");
        TextField tfWidth = new TextField("Szerokość [mm]");

        // Add
        for(Colors item : Colors.values()){
            lisSelColor.addItem(item.displayName());
        }
        for(WstazkiRodzaj item : WstazkiRodzaj.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1, lisSelColor, tfColor, tfWidth);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        tfColor.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfWidth.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą rzeczywistą."));
        tfWidth.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));
        lisSelColor.addValidator(new NullValidator("Pole nie może być puste.", false));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        lisSel1.setNullSelectionAllowed(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
            if (lisSel1.isValid() &&
                    lisSelColor.isValid() &&
                    tfColor.isValid() &&
                    tfWidth.isValid()) {

                String type = lisSel1.getValue().toString();
                String color = lisSelColor.getValue().toString();
                String nrColor = tfColor.getValue().toString();
                Integer width = Integer.parseInt(tfWidth.getValue().toString());
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type, color, nrColor, width.toString());

                ct = new CategoryTmp(null, null, mainCategory,
                        type, null, null, color, nrColor, null,
                        null, null, null, null, width, null, null,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void guziki(){
        // Create
        String mainCategory = MainCategories.GUZIKI.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        TextField tfSize = new TextField("Rozmiar");

        // Add
        listsLayout.addComponents(tfSize);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        tfSize.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfSize.addValidator(new RegexpValidator("[0]{1}|[1-9]{1}[0-9]*", "Wprowadzona wartość musi być liczbą całkowitą."));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
            if(tfSize.isValid()) {

                Integer size = Integer.parseInt(tfSize.getValue().toString());
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, size.toString());

                ct = new CategoryTmp(null, null, mainCategory,
                        null, null, null, null, null, null,
                        null, size, null, null, null, null, null,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void pozostale(){
        String mainCategory = MainCategories.POZOSTALE.displayName();
        categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory);

        ct = new CategoryTmp(null, null, mainCategory,
                null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null);
        isAccept = true;
        close();
    }

    public void akcesoriaDoBielizny(){
        String mainCategory = MainCategories.AKCESORIA_DO_BIELIZNY.displayName();
        categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory);

        ct = new CategoryTmp(null, null, mainCategory,
                null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null);
        isAccept = true;
        close();
    }

    public void akcesoriaDoDekoracji(){
        // Create
        String mainCategory = MainCategories.AKCESORIA_DO_DEKORACJI.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        ListSelect lisSel1 = new ListSelect("Rodzaj");

        // Add
        for(AkcesoriaDoDekoracjiRodzaj item : AkcesoriaDoDekoracjiRodzaj.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        lisSel1.setNullSelectionAllowed(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
            if(lisSel1.isValid()) {

                String type = lisSel1.getValue().toString();
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type);

                ct = new CategoryTmp(null, null, mainCategory,
                        type, null, null, null, null, null,
                        null, null, null, null, null, null, null,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void akcesoriaDoFiran(){
        // Create
        String mainCategory = MainCategories.AKCESORIA_DO_FIRAN.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        ListSelect lisSel1 = new ListSelect("Rodzaj");

        // Add
        for(AkcesoriaDoFiranRodzaj item : AkcesoriaDoFiranRodzaj.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        lisSel1.setNullSelectionAllowed(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {
            if(lisSel1.isValid()) {
                String type = lisSel1.getValue().toString();
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type);

                ct = new CategoryTmp(null, null, mainCategory,
                        type, null, null, null, null, null,
                        null, null, null, null, null, null, null,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void akcesoriaObuwnicze(){
        // Create
        String mainCategory = MainCategories.AKCESORIA_OBUWNICZE.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        ListSelect lisSel1 = new ListSelect("Rodzaj");

        // Add
        for(AkcesoriaObuwniczeRodzaj item  :AkcesoriaObuwniczeRodzaj.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        lisSel1.setNullSelectionAllowed(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {

            if(lisSel1.isValid()) {

                String type = lisSel1.getValue().toString();
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type);

                ct = new CategoryTmp(null, null, mainCategory,
                        type, null, null, null, null, null,
                        null, null, null, null, null, null, null,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void dodatkiPlastikowe(){
        String mainCategory = MainCategories.DODATKI_PLASTIKOWE.displayName();
        categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory);

        ct = new CategoryTmp(null, null, mainCategory,
                null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null);
        isAccept = true;
        close();
    }

    public void galanteria(){
        // Create
        String mainCategory = MainCategories.GALANTERIA.displayName();
        mainLayout = new VerticalLayout();
        listsLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();
        labHeader = new Label(mainCategory);
        butCancel = new Button("Anuluj");
        butSelect = new Button("Wybierz");
        ListSelect lisSel1 = new ListSelect("Rodzaj");

        // Add
        for(GalanteriaRodzaj item : GalanteriaRodzaj.values()){
            lisSel1.addItem(item.displayName());
        }
        listsLayout.addComponents(lisSel1);
        buttonsLayout.addComponents(butCancel, butSelect);
        mainLayout.addComponents(labHeader,listsLayout, buttonsLayout);

        // Set
        lisSel1.addValidator(new NullValidator("Pole nie może być puste.", false));

        setContent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        listsLayout.setSpacing(true);

        lisSel1.setNullSelectionAllowed(true);

        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);
        buttonsLayout.setComponentAlignment(butCancel, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(butSelect, Alignment.MIDDLE_CENTER);

        // Listeners
        butCancel.addClickListener(event -> close());

        butSelect.addClickListener(event -> {

            if(lisSel1.isValid()) {
                String type = lisSel1.getValue().toString();
                categoryTxt = HelpfulMethods.makeCategoryPath(mainCategory, type);

                ct = new CategoryTmp(null, null, mainCategory,
                        type, null, null, null, null, null,
                        null, null, null, null, null, null, null,
                        null, null);
                isAccept = true;
                close();
            } else {
                Notification.show("Uwaga!", "Pola nie zostały poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

}


