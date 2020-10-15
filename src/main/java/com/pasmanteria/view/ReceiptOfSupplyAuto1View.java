package com.pasmanteria.view;

import com.pasmanteria.Filess;
import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.model.InvoiceFieldsTmp;
import com.pasmanteria.model.InvoicesSupplyTmp;
import com.pasmanteria.model.StaticVariables;
import com.pasmanteria.ocr.InvoiceOcr;
import com.pasmanteria.repository.InvoiceFieldsTmpRep;
import com.pasmanteria.repository.InvoicesSupplyTmpRep;
import com.pasmanteria.repository.StaticVariablesRep;
import com.vaadin.annotations.*;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

/**
 * Created by Mike on 08/01/2017.
 */

//@SpringUI(path = "/newInvoiceAuto")
@SpringView(name = ReceiptOfSupplyAuto1View.VIEW_NAME)
@Theme("valo")
public class ReceiptOfSupplyAuto1View extends VerticalLayout implements View { //extends UI {
    public static final String VIEW_NAME = "ROSA1";

    private boolean isLoaded;

    //VerticalLayout mainLayout;
    private GridLayout gridLayout;
    private HorizontalLayout hlConfirmButtons;
    private HorizontalLayout hlBanner;
    private Image imgHeader;
    private TextField tfInvoiceSearch;
    private TextField tfInvoiceNumber;
    private Button butLoad;
    private Button butSelectFile;
    private Button butCancelNewInvoice;
    private Button butAddNewInvoice;
    private PopupDateField dateOfIssue;
    private ComboBox cbSelectWholesale;
    private String captions[] = {"Brak", "Jola", "BafPol", "Inna"};
    private InvoicesSupplyTmpRep invoicesSupplyTmpRep;
    private InvoiceFieldsTmpRep invoiceFieldsTmpRep;
    private StaticVariablesRep staticVariablesRep;
    private InvoiceOcr invoiceOcr;
    private HelpfulMethods hm;
    private Filess filess;
    private Selection2OptionsSubWin selection2OptionsSubWin;
    private InformationSubWin informationSubWin;
    private String imageName = "";

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    @Autowired
    public ReceiptOfSupplyAuto1View(InvoicesSupplyTmpRep invoicesSupplyTmpRep,
                                    InvoiceFieldsTmpRep invoiceFieldsTmpRep,
                                    StaticVariablesRep staticVariablesRep) {
        this.invoicesSupplyTmpRep = invoicesSupplyTmpRep;
        this.invoiceFieldsTmpRep = invoiceFieldsTmpRep;
        this.staticVariablesRep = staticVariablesRep;
        hm = new HelpfulMethods();
        invoiceOcr = new InvoiceOcr();
        filess = new Filess();
        selection2OptionsSubWin = new Selection2OptionsSubWin();
        informationSubWin = new InformationSubWin();
        isLoaded = false;
    }

    @PostConstruct
    void init() {
        // Create
        //mainLayout = new VerticalLayout();
        hlConfirmButtons = new HorizontalLayout();
        gridLayout = new GridLayout(4,4);
        hlBanner = new HorizontalLayout();
        //labPrompt1 = new Label("Upewnij się że wczytany nr faktury jest poprawny.");
        //labPrompt2 = new Label("Jeśli jest błędny dokonaj zmiany manualnie.");
        cbSelectWholesale = new ComboBox("Hurtownia");
        tfInvoiceSearch = new TextField("Nazwa pliku");
        tfInvoiceNumber = new TextField("Numer faktury");
        butLoad = new Button("Wczytaj");
        butSelectFile = new Button("Wybierz plik");
        butCancelNewInvoice = new Button("Anuluj");
        butAddNewInvoice = new Button("Dalej");
        dateOfIssue = new PopupDateField("Data wystawienia") {
            private static final long serialVersionUID = 4001006351711723688L;

            @Override
            protected Date handleUnparsableDateString(String dateString)
                    throws Converter.ConversionException {
                // Have a notification for the error
                Notification.show("Niepoprawny format daty", Notification.Type.WARNING_MESSAGE);
                // A failure must always also throw an exception
                throw new Converter.ConversionException("Niepoprawna data");
            }
        };

        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/banners/automatyczne_dodawanie_towaru_d.png"));
        imgHeader = new Image("",resource);

        tfInvoiceNumber.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        tfInvoiceSearch.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        dateOfIssue.addValidator(new NullValidator("Pole nie może być puste.", false));
        cbSelectWholesale.addValidator(new NullValidator("Pole nie może być puste", false));
        // Add
        for (String caption: captions)
            cbSelectWholesale.addItem(caption);
        //cbSelectWholesale.select("BafPol");

        gridLayout.addComponent(cbSelectWholesale, 0, 0, 1,0);
        gridLayout.addComponent(dateOfIssue, 0, 1, 1, 1);
        gridLayout.addComponent(tfInvoiceSearch, 0, 2, 1, 2);
        gridLayout.addComponent(butSelectFile, 2, 2);
        gridLayout.addComponent(tfInvoiceNumber, 0, 3, 1, 3);
        gridLayout.addComponent(butLoad, 2, 3);
        hlBanner.addComponent(imgHeader);
        hlConfirmButtons.addComponents(butCancelNewInvoice, butAddNewInvoice);
        addComponents(hlBanner, gridLayout, hlConfirmButtons);

        //gridLayout.addComponent(labPrompt1, 1, 4, 2, 4);
        //gridLayout.addComponent(labPrompt2, 1, 5, 2, 5);
        //gridLayout.addComponent(butCancelNewInvoice, 1, 6);
        //gridLayout.addComponent(butAddNewInvoice, 2, 6);

        hlBanner.setComponentAlignment(imgHeader, Alignment.MIDDLE_CENTER);
        hlBanner.setWidth(100, Unit.PERCENTAGE);

        hlConfirmButtons.setMargin(true);
        hlConfirmButtons.setSpacing(true);
        hlConfirmButtons.setWidth(100, Unit.PERCENTAGE);

        hlConfirmButtons.setComponentAlignment(butCancelNewInvoice, Alignment.MIDDLE_RIGHT);
        hlConfirmButtons.setComponentAlignment(butAddNewInvoice, Alignment.MIDDLE_LEFT);

        this.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
        this.setWidth(100, Unit.PERCENTAGE);

        // Set
        //setContent(mainLayout);
        this.setMargin(true);
        this.setSpacing(true);

        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);

        cbSelectWholesale.setWidth("240");
        dateOfIssue.setWidth("240");
        tfInvoiceSearch.setWidth("240");
        tfInvoiceNumber.setWidth("240");
        butCancelNewInvoice.setWidth("185");
        butAddNewInvoice.setWidth("185");
        butCancelNewInvoice.addStyleName("danger");
        butAddNewInvoice.addStyleName("friendly");

        gridLayout.setComponentAlignment(butSelectFile, Alignment.BOTTOM_LEFT);
        gridLayout.setComponentAlignment(butLoad, Alignment.BOTTOM_LEFT);

        // to zakomentowane na razie zostaw, może się gdzieś przyda.
        // działa to tak, że po wybraniu hurtowni z listy, na stronie pojawia się tekst
        // "Selected <nazwa_hurtowni>. Warto mieć na uwadze ten Listener.

//        selectWholesale.addValueChangeListener(event ->
//                layout.addComponent(
//                        new Label("Selected " + event.getProperty().getValue())
//                )
//        );

        cbSelectWholesale.setNullSelectionAllowed(false);

        dateOfIssue.setImmediate(true);
        // Display only year, month, and day in dash-delimited format
        dateOfIssue.setDateFormat("dd-MM-yyyy");
        // Don't be too tight about the validity of dates
        // on the client-side
        dateOfIssue.setLenient(true);
        // END-EXAMPLE: component.datefield.popupdatefield.customerror
        dateOfIssue.setInputPrompt("Wybierz datę");
        // Always use Polish locale in the example
        dateOfIssue.setLocale(new Locale("pl", "PL"));
        //dateOfIssue.addValidator(new NullValidator("Pole nie może być puste.", false));
        //dateOfIssue.setRequired(true);
        //tfInvoiceSearch.setRequired(true);
        //tfInvoiceNumber.setRequired(true);

        //sprawdzam czy istnieje rozpoczeta sesja dodawania recznego towaru
            int rosmIsSaved = staticVariablesRep.findByName("rosmissaved").getValue();
            if (rosmIsSaved == 1) {
                // komunikat informacyjny aby najpierw zakonczyc manualne dodawanie towaru
                String info = "Aby móc skorzystać z tej opcji najpierw zakończ lub anuluj rozpoczete reczne dodawanie towaru.";
                String caption = "Informacja";
                informationSubWin.init(caption, info);
                UI.getCurrent().addWindow(informationSubWin);
                informationSubWin.setModal(true);
            }

        informationSubWin.addCloseListener(e -> {
            JavaScript.eval("close()");
            getUI().close();
        });


        // Listeners
        butCancelNewInvoice.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                //subokno z opcja wyboru wyjscia z zapisaniem lub bez
                String info = "Przerwanie czynności dodawania towaru spowoduje niezapisanie wprowadzonych zmian.";
                String caption = "Informacja";
                selection2OptionsSubWin.init(caption, info);
                UI.getCurrent().addWindow(selection2OptionsSubWin);
                selection2OptionsSubWin.setModal(true);
            }
        });

        selection2OptionsSubWin.addCloseListener(event -> {
            if (selection2OptionsSubWin.getSelectedOption() == 1) {
                //nic nie zostanie zapisane
                invoicesSupplyTmpRep.deleteAll();
                filess.deleteFile(".\\invoice_data\\faktura_ocr.csv");
                staticVariablesRep.editOneByName("saveofrosa",0);
                staticVariablesRep.editOneByName("rosaissaved",0);
                JavaScript.eval("close()");
                getUI().close();
            }
        });

        butAddNewInvoice.addClickListener(event -> {
            if (isValidated()) {
                invoicesSupplyTmpRep.save(
                        new InvoicesSupplyTmp(1L, tfInvoiceNumber.getValue(), cbSelectWholesale.getValue().toString(), dateOfIssue.getValue()));

                //zapisanie strony postepu
                staticVariablesRep.editOneByName("saveofrosa",2);

                //przejscie do strony poprawiania danych odczytanych przez OCR
                getUI().getNavigator().navigateTo(ReceiptOfSupplyAuto2View.VIEW_NAME);
            } else {
                Notification.show("Uwaga!", "Faktura nie została wczytana lub wszystkie pola nie zostały wypełnione.", Notification.Type.WARNING_MESSAGE);
            }
        });


        butLoad.addClickListener(event -> {
            ArrayList<String> catalogNumberList = new ArrayList<>();
            ArrayList<String> articleNameList = new ArrayList<>();
            ArrayList<String> discountPercentList = new ArrayList<>();
            ArrayList<String> taxPercentList = new ArrayList<>();
            ArrayList<String> priceGrossTotalityList = new ArrayList<>();
            String ocrText = new String();
            List<InvoiceFieldsTmp> queryContent = invoiceFieldsTmpRep.findAll();
            StaticVariables st = staticVariablesRep.findByName("imageFileName");
            imageName = st.getStringValue();
            //odczytywanie nazwy faktury i uzupelnij pole z nazwa faktury w widoku i zapisz dane faktury do pliku
            //odczytywanie w petli kolumn z faktury i zapisanie ich do pliku

            for (InvoiceFieldsTmp row : queryContent) {
                String fieldName = row.getFieldName();
                Integer leftX = row.getLeftX();
                Integer topY = row.getTopY();
                Integer heightY = row.getHeightY();
                Integer widthX = row.getWidthX();

                ocrText = invoiceOcr.scanInvoice(imageName, leftX, topY, widthX, heightY);

                if (fieldName.equals("invoice_nr")) {
                    tfInvoiceNumber.setValue(ocrText);
                } else {
                    switch (fieldName) {
                        case "catalog_number":
                            catalogNumberList.addAll(Arrays.asList(ocrText.split("\\r\\n|\\n|\\r")));
                            break;
                        case "article_name":
                            articleNameList.addAll(Arrays.asList(ocrText.split("\\r\\n|\\n|\\r")));
                            break;
                        case "discount_percent":
                            discountPercentList.addAll(Arrays.asList(ocrText.split("\\r\\n|\\n|\\r")));
                            break;
                        case "tax_percent":
                            taxPercentList.addAll(Arrays.asList(ocrText.split("\\r\\n|\\n|\\r")));
                            break;
                        case "price_gross_totality":
                            priceGrossTotalityList.addAll(Arrays.asList(ocrText.split("\\r\\n|\\n|\\r")));
                            break;
                        default:
                            // okno z bledem
                            break;
                    }
                }
            }

            String fileName = new String(".\\invoice_data\\faktura_ocr.csv");
            boolean isWorked = filess.writeToCsv(fileName,
                    filess.makeArray(catalogNumberList, articleNameList,
                            discountPercentList, taxPercentList, priceGrossTotalityList));

            if(isWorked) {
                InformationSubWin informationSubWin = new InformationSubWin();
                String info = "Dane zostały wczytane. Upewnij się czy wczytany numer faktury jest poprawny. Jeśli nie, dokonaj zmiany ręcznie. ";
                String caption = "Informacja";
                informationSubWin.init(caption, info);
                UI.getCurrent().addWindow(informationSubWin);
                informationSubWin.setModal(true);
                isLoaded = true;
            } else {
                InformationSubWin informationSubWin = new InformationSubWin();
                String info = "Dane nie zostały wczytane! Wystąpił problem z zapisem danych do pliku.";
                String caption = "Informacja";
                informationSubWin.init(caption, info);
                UI.getCurrent().addWindow(informationSubWin);
                informationSubWin.setModal(true);
                isLoaded = false;
            }
        });

        butSelectFile.addClickListener(event -> {
            getUI().getPage().open("http://localhost/faktura/readFiles.php", "Faktura", true);
        });
    }

    private boolean isValidated() {
        if(tfInvoiceNumber.isValid()
                && dateOfIssue.isValid()
                //&& !dateOfIssue.isEmpty()
                && tfInvoiceSearch.isValid()
                && cbSelectWholesale.isValid()
                //&& !cbSelectWholesale.isEmpty()
                && isLoaded
                ) {
            return true;
        } else return false;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
