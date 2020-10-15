package com.pasmanteria.view;

import com.pasmanteria.HelpfulMethods;
import com.pasmanteria.QueryMapper;
import com.pasmanteria.model.*;
import com.pasmanteria.repository.*;
import com.pasmanteria.repository.ArticlesOcrTmpRep;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by adrian on 29.06.2017.
 */
//@SpringView(name = com.pasmanteria.view.NewSaleView.VIEW_NAME)
@Theme("valo")
@SpringUI(path = "/newSale")
public class NewSaleView extends UI{ // VerticalLayout implements View {

    public static final String VIEW_NAME = "NewSaleView";
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

        private HorizontalLayout labelLayout;
        private HorizontalLayout searchLayout;
        private HorizontalLayout gridLayout;
        private HorizontalLayout buttonsLayout;
        private VerticalLayout mainLayout;
        private HorizontalLayout optionsLayout;

        private HorizontalLayout hlBanner;
        private Image imgHeader;

        private Button butCancelNewItems;
        private Button butAddNewItems;
        private Button butDeleteSelected;
        private Button butSearch;
        private TextField tfSearch;
        private TextField tfQuantity;
        private TextField tfEndPrice;

        private Grid gridTable;
        private Grid.MultiSelectionModel selection;
        private IndexedContainer container;

        private ArticlesTmpRep newSupplyTempRepository;
        private ArticlesRep articlesRep;
        private SoldArticlesRep soldArticlesRepository;
        private CategoryTmpRep categoryTmpRep;
        private InvoicesSupplyTmpRep newInvoiceTempRepository;
        private ArticlesOcrTmpRep newInvoiceOcrTempRepository;
        private ArticlesToOrderRep articlesToOrderRep;

        private Integer quantityRowsFromQuery;
        private HelpfulMethods hm;

        private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

        @Autowired
        public NewSaleView(ArticlesTmpRep newSupplyTempRepository,
                           ArticlesRep articlesRep,
                           CategoryTmpRep categoryTmpRep,
                           SoldArticlesRep soldArticlesRepository,
                           InvoicesSupplyTmpRep newInvoiceTempRepository,
                           ArticlesOcrTmpRep newInvoiceOcrTempRepository,
                           ArticlesToOrderRep articlesToOrderRep) {
            this.newSupplyTempRepository = newSupplyTempRepository;
            this.articlesRep = articlesRep;
            this.categoryTmpRep = categoryTmpRep;
            this.newInvoiceTempRepository = newInvoiceTempRepository;
            this.newInvoiceOcrTempRepository = newInvoiceOcrTempRepository;
            this.soldArticlesRepository = soldArticlesRepository;
            this.articlesToOrderRep = articlesToOrderRep;
            gridTable = new Grid();
            container = new IndexedContainer();
            hm = new HelpfulMethods();
        }

        @Override
        protected void init(VaadinRequest request) {
            // Create
            mainLayout = new VerticalLayout();
            labelLayout = new HorizontalLayout();
            searchLayout = new HorizontalLayout();
            //gridLayout = new HorizontalLayout();
            buttonsLayout = new HorizontalLayout();
            optionsLayout = new HorizontalLayout();
            hlBanner = new HorizontalLayout();

            butCancelNewItems = new Button("Anuluj sprzedaż");
            butAddNewItems = new Button("Zakończ sprzedaż");
            butDeleteSelected = new Button("Usuń zaznaczone");
            butSearch = new Button("Dodaj");
            tfSearch = new TextField("Kod kreskowy");
            tfQuantity = new TextField("Ilość");
            tfEndPrice = new TextField("Należność");

            FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/banners/sprzedaz_m.png"));
            imgHeader = new Image("",resource);

            // Add
            container.addContainerProperty("KodKreskowy", String.class, ""); //barcode
            container.addContainerProperty("NrKatalogowy", String.class, ""); //catalogNr
            container.addContainerProperty("NazwaArtykułu", String.class, ""); //articleName
            container.addContainerProperty("Ilość", BigDecimal.class, 0.0); //quantity
            container.addContainerProperty("JM", String.class, ""); //unitOfMeasurement
            container.addContainerProperty("Cena", BigDecimal.class, 0.0f); //marginPercent

            butCancelNewItems.addStyleName("danger");
            butAddNewItems.addStyleName("friendly");
            //

            hlBanner.addComponent(imgHeader);
            searchLayout.addComponents(tfSearch, tfQuantity, butSearch);
            optionsLayout.addComponents(butDeleteSelected, tfEndPrice);
            buttonsLayout.addComponents(butCancelNewItems, butAddNewItems);
            mainLayout.addComponents(hlBanner, searchLayout, gridTable , optionsLayout, buttonsLayout);

            // Set
            this.setContent(mainLayout);
            mainLayout.setMargin(true);
            mainLayout.setSpacing(true);

            hlBanner.setComponentAlignment(imgHeader, Alignment.MIDDLE_CENTER);
            hlBanner.setWidth(100, Unit.PERCENTAGE);

            optionsLayout.setComponentAlignment(tfEndPrice, Alignment.TOP_RIGHT);
            optionsLayout.setWidth(100,Unit.PERCENTAGE);


            gridTable.setHeight(100, Unit.PERCENTAGE);
            gridTable.setWidth(100, Unit.PERCENTAGE);
            gridTable.setSelectionMode(Grid.SelectionMode.MULTI);

            selection = (Grid.MultiSelectionModel) gridTable.getSelectionModel();

            tfSearch.addValidator(new RegexpValidator("[0-9]+(\\.)?[0-9]+","Wprowadzona wartość musi być liczbą."));
            tfQuantity.addValidator(new StringLengthValidator("Pole nie może być puste.", 1, 100, true));
            tfQuantity.addValidator(new RegexpValidator("[0-9]+((\\.|,)?[0-9]+)?","Wprowadzona wartość musi być liczbą."));

            buttonsLayout.setMargin(true);
            buttonsLayout.setSpacing(true);
            buttonsLayout.setWidth(100, Unit.PERCENTAGE);
            buttonsLayout.setComponentAlignment(butCancelNewItems, Alignment.MIDDLE_CENTER);
            buttonsLayout.setComponentAlignment(butAddNewItems, Alignment.MIDDLE_CENTER);
            searchLayout.setComponentAlignment(butSearch, Alignment.BOTTOM_CENTER);
            searchLayout.setSpacing(true);

            butDeleteSelected.setEnabled(gridTable.getSelectedRows().size() > 0);
/*
            Articles queryContent = articlesRep.findOneByBarcode(tfSearch.getValue().toString());
            if(queryContent != null) {
                addNewItemToContainer(queryContent);
            }
            listArticles();
*/

            // end Set

            // Listeners
            butDeleteSelected.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    for (Object itemId : selection.getSelectedRows())
                        gridTable.getContainerDataSource().removeItem(itemId);
                    gridTable.getSelectionModel().reset();
                    butDeleteSelected.setEnabled(false);
                    tfEndPrice.setValue(countEndPrice().toString());
                }
            });

            butSearch.addClickListener(event -> {
                if(isValidated()) {
                    Articles queryContent2 = articlesRep.findOneByBarcode(tfSearch.getValue().toString());
                    if (queryContent2 != null) {
                        BigDecimal tfQuanity = hm.convertStringAmountToBigDecimal(tfQuantity.getValue().toString());
                        //sprawdzenie czy zadeklarowana ilość kupna nie jest większa od ilości artykułu w bazie
                        //sprawdzanie nie działą w przypadku kilkukrotnego dodania tego samego produktu na liste sprzedazową
                        //ponieważ kupowana wartość jest porównywana tylko do tej w bazie, a ta już na liście nie jest brana pod uwagę
                        if(queryContent2.getQuantity().compareTo(tfQuanity) == 0 || queryContent2.getQuantity().compareTo(tfQuanity) == 1) {
                            Articles row = queryContent2;
                            Item newItem = container.getItem(container.addItem());
                            newItem.getItemProperty("KodKreskowy").setValue(row.getBarcode());
                            newItem.getItemProperty("NrKatalogowy").setValue(row.getCatalogNumber());
                            newItem.getItemProperty("NazwaArtykułu").setValue(row.getArticleName());
                            newItem.getItemProperty("Ilość").setValue(hm.convertStringAmountToBigDecimal(tfQuantity.getValue().toString()));
                            newItem.getItemProperty("JM").setValue(row.getUnitOfMeasurement());
                            newItem.getItemProperty("Cena").setValue(row.getPriceMargin().multiply(hm.convertStringAmountToBigDecimal(tfQuantity.getValue().toString())));
                            tfEndPrice.setValue(countEndPrice().toString());
                        } else {
                            Notification.show("Nie ma takiej ilości artykułu!", Notification.Type.ERROR_MESSAGE);
                        }
                    } else {
                        Notification.show("Podany artykuł nie został odnaleziony w bazie!", Notification.Type.ERROR_MESSAGE);
                    }
                    listArticles();
                } else {
                    Notification.show("Uwaga!", "Pole ilość nie zostało poprawnie wypełnione.", Notification.Type.WARNING_MESSAGE);

                }
            });

            gridTable.addSelectionListener(select -> {
                butDeleteSelected.setEnabled(gridTable.getSelectedRows().size() > 0);
            });

            butAddNewItems.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    Collection<?> itemIds = container.getItemIds();
                    int transactionId;
                    List<SoldArticles> sa;
                    //ta petla jest do poprawienia - warunek jest bez sensu, można po prostu poruwnać do nulla to zwracane
                    do{
                        //zmienic transactionId na zapis w postaci daty i liczby losowanej np 01/12/17/5432 typu string
                       transactionId = ThreadLocalRandom.current().nextInt(100);
                        sa = soldArticlesRepository.findByTransactionId(transactionId);
                    } while(sa.size() != 0);
                    for (Object itemID : itemIds) {
                        Item newItem = container.getItem(itemID);
                        String barcode = newItem.getItemProperty("KodKreskowy").getValue().toString();
                        BigDecimal quantity = hm.convertStringAmountToBigDecimal(newItem.getItemProperty("Ilość").getValue().toString());
                        BigDecimal price = hm.convertStringAmountToBigDecimal(newItem.getItemProperty("Cena").getValue().toString());
                        BigDecimal oldQuantity = articlesRep.findOneByBarcode(barcode).getQuantity();
                        //BigDecimal oldQuantity = articlesRep.findQuantityByBarcode(barcode);
                        BigDecimal newQuantity = oldQuantity.subtract(quantity);
                        articlesRep.editOneByBarcode(barcode, newQuantity);

                        Articles article = articlesRep.findOneByBarcode(barcode);
                        soldArticlesRepository.save(
                                new SoldArticles(null, transactionId, quantity, 0,
                                        price, hm.convertStringToDate("data"), article));

                        ArticlesToOrder articlesToOrder = articlesToOrderRep.findOneByBarcode(barcode);
                        if(articlesToOrder != null) {
                            BigDecimal newQuantityToOrder = articlesToOrder.getQuantity().add(quantity);
                            articlesToOrderRep.editOneByBarcode(barcode, newQuantityToOrder );
                        } else {
                            articlesToOrderRep.save(
                                    new ArticlesToOrder(null, barcode, quantity,
                                            hm.convertStringToDate("data"), article));
                        }
                    }
                    container.removeAllItems();
                    listArticles();
                    Notification.show("Sprzedaż została zakończona!", Notification.Type.TRAY_NOTIFICATION);
                }
            });

            butCancelNewItems.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    getUI().getNavigator().navigateTo(MainView.VIEW_NAME);

                }
            });
        }

    private void addNewItemToContainer(Articles row) {
        Item newItem = container.getItem(container.addItem());
        newItem.getItemProperty("KodKreskowy").setValue(row.getBarcode());
        newItem.getItemProperty("NrKatalogowy").setValue(row.getCatalogNumber());
        newItem.getItemProperty("NazwaArtykułu").setValue(row.getArticleName());
        newItem.getItemProperty("Ilość").setValue(hm.convertStringAmountToBigDecimal(tfQuantity.getValue().toString()));
        newItem.getItemProperty("JM").setValue(row.getUnitOfMeasurement());
        newItem.getItemProperty("Cena").setValue(row.getPriceGrossPcs());
    }

    private BigDecimal countEndPrice() {
        Collection<?> itemIds = container.getItemIds();
        BigDecimal endPrice = new BigDecimal(0);
        for (Object itemID : itemIds) {
            Item newItem = container.getItem(itemID);
            BigDecimal price = hm.convertStringAmountToBigDecimal(newItem.getItemProperty("Cena").getValue().toString());
            endPrice = endPrice.add(price);
        }
        return  endPrice;
    }

    private void listArticles() {
            gridTable.setContainerDataSource(container);
        }

    private boolean isValidated() {
        if(tfQuantity.isValid()) {
            return true;
        } else return false;
    }


    }

