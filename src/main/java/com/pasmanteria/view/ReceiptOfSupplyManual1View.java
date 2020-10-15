package com.pasmanteria.view;

import com.pasmanteria.Filess;
import com.pasmanteria.model.InvoicesSupplyTmp;
import com.pasmanteria.repository.InvoicesSupplyTmpRep;
import com.pasmanteria.repository.StaticVariablesRep;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Date;
import java.util.Locale;


/**
 * Created by adrian on 30.12.2016.
 */
//@SpringUI(path = "/receiptOfSupplyManual2")
@SpringView(name= ReceiptOfSupplyManual1View.VIEW_NAME)
@Theme("valo")
public class ReceiptOfSupplyManual1View extends VerticalLayout implements View {//UI {
    public static final String VIEW_NAME = "ROSM1";

    VerticalLayout vlFields;
    HorizontalLayout hlBanner;
    HorizontalLayout hlConfirmButtons;
    Image imgHeader;
    private TextField tfInvoiceNumber;
    private Button butCancelNewInvoice;
    private Button butAddNewInvoice;
    private PopupDateField dateOfIssue;
    private ComboBox cbSelectWholesale;
    private String captions[] = {"Brak", "Jola", "BafPol", "Inna"};
    private Filess filess;
    private InvoicesSupplyTmpRep invoicesSupplyTmpRep;
    private StaticVariablesRep staticVariablesRep;
    private Selection2OptionsSubWin selection2OptionsSubWin;
    private InformationSubWin informationSubWin;

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    @Autowired
    public ReceiptOfSupplyManual1View(InvoicesSupplyTmpRep invoicesSupplyTmpRep,
                                      StaticVariablesRep staticVariablesRep) {
        this.invoicesSupplyTmpRep = invoicesSupplyTmpRep;
        this.staticVariablesRep = staticVariablesRep;
        selection2OptionsSubWin = new Selection2OptionsSubWin();
        informationSubWin = new InformationSubWin();
        filess = new Filess();
    }

    @PostConstruct
    void init() {
        // Create
        vlFields = new VerticalLayout();
        hlConfirmButtons = new HorizontalLayout();
        hlBanner = new HorizontalLayout();
        cbSelectWholesale = new ComboBox("Hurtownia");
        tfInvoiceNumber = new TextField("Numer faktury");
        butCancelNewInvoice = new Button("Anuluj");
        butAddNewInvoice = new Button("Dalej");
        dateOfIssue = new PopupDateField("Data wystawienia") {
            private static final long serialVersionUID = 4001006351711723688L;
            @Override
            protected Date handleUnparsableDateString(String dateString)
                    throws ConversionException {
                // Have a notification for the error
                Notification.show(
                        "Niepoprawny format daty",
                        Notification.Type.WARNING_MESSAGE);

                // A failure must always also throw an exception
                throw new ConversionException("Niepoprawna data");
            }
        };

        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/banners/reczne_dodawanie_towaru_d.png"));
        imgHeader = new Image("",resource);

        tfInvoiceNumber.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
        dateOfIssue.addValidator(new NullValidator("Pole nie może być puste.", false));
        cbSelectWholesale.addValidator(new NullValidator("Pole nie może być puste.", false));

        // Add
        for (String caption: captions)
            cbSelectWholesale.addItem(caption);

        hlBanner.addComponent(imgHeader);
        vlFields.addComponents(dateOfIssue, cbSelectWholesale, tfInvoiceNumber);
        hlConfirmButtons.addComponents(butCancelNewInvoice, butAddNewInvoice);
        addComponents(hlBanner, vlFields, hlConfirmButtons);

        // Set
        this.setMargin(true);
        this.setSpacing(true);

        hlBanner.setComponentAlignment(imgHeader, Alignment.MIDDLE_CENTER);
        hlBanner.setWidth(100, Unit.PERCENTAGE);
        vlFields.setMargin(true);
        vlFields.setSpacing(true);
        vlFields.setWidth(100, Unit.PERCENTAGE);
        vlFields.setComponentAlignment(cbSelectWholesale, Alignment.MIDDLE_CENTER);
        vlFields.setComponentAlignment(tfInvoiceNumber, Alignment.MIDDLE_CENTER);
        vlFields.setComponentAlignment(dateOfIssue, Alignment.MIDDLE_CENTER);
        hlConfirmButtons.setMargin(true);
        hlConfirmButtons.setSpacing(true);
        hlConfirmButtons.setWidth(100, Unit.PERCENTAGE);
        hlConfirmButtons.setComponentAlignment(butCancelNewInvoice, Alignment.MIDDLE_RIGHT);
        hlConfirmButtons.setComponentAlignment(butAddNewInvoice, Alignment.MIDDLE_LEFT);

        tfInvoiceNumber.setWidth("300");
        dateOfIssue.setWidth("300");
        cbSelectWholesale.setWidth("300");
        butCancelNewInvoice.setWidth("185");
        butAddNewInvoice.setWidth("185");
        butCancelNewInvoice.addStyleName("danger");
        butAddNewInvoice.addStyleName("friendly");

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

        //sprawdzam czy istnieje rozpoczeta sesja automatycznego dodawania towaru
        int rosaIsSaved = staticVariablesRep.findByName("rosaissaved").getValue();
        if (rosaIsSaved == 1) {
                // komunikat informacyjny aby najpierw zakonczyc automatyczne dodawanie towaru
                String info = "Aby móc skorzystać z tej opcji najpierw zakończ lub anuluj rozpoczete automatyczne dodawanie towaru.";
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

        selection2OptionsSubWin.addCloseListener(e -> {
            if (selection2OptionsSubWin.getSelectedOption() == 1) {
                //nic nie zostanie zapisane
                invoicesSupplyTmpRep.deleteAll();
                staticVariablesRep.editOneByName("saveofrosm",0);
                staticVariablesRep.editOneByName("rosmissaved",0);
                JavaScript.eval("close()");
                getUI().close();
            }
        });

        butAddNewInvoice.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (isValiated()) {
                    invoicesSupplyTmpRep.save(
                            new InvoicesSupplyTmp(1L, tfInvoiceNumber.getValue(), cbSelectWholesale.getValue().toString(), dateOfIssue.getValue()));

                    //zapisanie strony postepu
                    staticVariablesRep.editOneByName("saveofrosm",2);

                    //przejscie do strony recznego wprowadzania towaru
                    getUI().getNavigator().navigateTo(ReceiptOfSupplyManual2View.VIEW_NAME);
                } else {
                    Notification.show("Uwaga!", "Obowiązkowe pola nie zostały wypełnione.", Notification.Type.WARNING_MESSAGE);
                }
            }
        });

    }

    private boolean isValiated() {
        if(tfInvoiceNumber.isValid()
                && dateOfIssue.isValid()

                //&& !dateOfIssue.isEmpty()
                && cbSelectWholesale.isValid()
                //&& !cbSelectWholesale.isEmpty()
                ) {
            return true;
        } else return false;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){
        // the view is constructed in the init() methof()
    }
}