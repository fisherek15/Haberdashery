package com.pasmanteria;

import com.pasmanteria.model.*;
import com.pasmanteria.repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by adrian on 17.12.2016.
 */

@Order
@Component
public class RunAtStart{

    private ArticlesRep articlesRep;
    private ArticlesAdditionalInfoRep articlesAdditionalInfoRep;
    private InvoicesSupplyRep invoicesSupplyRep;
    private ArticlesToOrderRep articlesToOrderRep;
    private SoldArticlesRep soldArticlesRep;
    private ArticlesTmpRep articlesTmpRep;
    private InvoicesSupplyTmpRep invoicesSupplyTmpRep;
    private CategoryTmpRep categoryTmpRep;
    private CategoryRep categoryRep;
    private StaticVariablesRep staticVariablesRep;

    private final Logger logger = Logger.getLogger(RunAtStart.class);
    private HelpfulMethods hm;

    @Autowired
    public RunAtStart(ArticlesRep articlesRep,
                      ArticlesAdditionalInfoRep articlesAdditionalInfoRep,
                      InvoicesSupplyRep invoicesSupplyRep,
                      ArticlesToOrderRep articlesToOrderRep,
                      SoldArticlesRep soldArticlesRep,
                      ArticlesTmpRep articlesTmpRep,
                      InvoicesSupplyTmpRep invoicesSupplyTmpRep,
                      CategoryTmpRep categoryTmpRep,
                      CategoryRep categoryRep,
                      StaticVariablesRep staticVariablesRep) {

        this.articlesRep = articlesRep;
        this.articlesAdditionalInfoRep = articlesAdditionalInfoRep;
        this.invoicesSupplyRep = invoicesSupplyRep;
        this.articlesToOrderRep = articlesToOrderRep;
        this.soldArticlesRep = soldArticlesRep;
        this.articlesTmpRep = articlesTmpRep;
        this.categoryRep = categoryRep;
        this.invoicesSupplyTmpRep = invoicesSupplyTmpRep;
        this.categoryTmpRep = categoryTmpRep;
        this.staticVariablesRep = staticVariablesRep;

        hm = new HelpfulMethods();
    }

    @PostConstruct
    public void runAtStart() {

        //tymczasowe dodawanie artykułów do bazy na sztywno - w celu przeprowadzania testów na czas braku layoutu

/*
        StaticVariables sv = new StaticVariables(null, "saveofrosa", 0, null);
        staticVariablesRep.save(sv);

        StaticVariables sv1 = new StaticVariables(null, "saveofrosm", 0, null);
        staticVariablesRep.save(sv1);

        StaticVariables sv2 = new StaticVariables(null, "rosaissaved", 0, null);
        staticVariablesRep.save(sv2);

        StaticVariables sv3 = new StaticVariables(null, "rosmissaved", 0, null);
        staticVariablesRep.save(sv3);
        */
/*
        InvoicesSupply is1 = new InvoicesSupply(
                null, "14456/01/2016", "BafPol", hm.convertStringToDate("data"),
                hm.convertStringToDate("19-12-2016"), null
        );
        invoicesSupplyRep.save(is1);

        InvoicesSupply is2 = new InvoicesSupply(
                null, "08901/Z/2016", "Jola", hm.convertStringToDate("data"),
                hm.convertStringToDate("15-12-2016"), null
        );
        invoicesSupplyRep.save(is2);

        InvoicesSupply is3 = new InvoicesSupply(
                null, "14421/01/2016", "BafPol", hm.convertStringToDate("data"),
                hm.convertStringToDate("10-12-2016"), null
        );
        invoicesSupplyRep.save(is3);
*/
/*
        articlesRep.deleteAll();
        Articles a1 = new Articles(
                null, "45343434", "K286", "Gumka z falbanką 40 B,CZ216-132-42",
                new BigDecimal(60.0), "m", new BigDecimal(1.14), 5,
                new BigDecimal(1.09), new BigDecimal(1.34), new BigDecimal(65.4), 23, new BigDecimal(15.04),
                new BigDecimal(80.44), hm.convertStringToDate("30-12-2016"), 100, new BigDecimal(2.18), "Dodatki krawieckie",
                null, null, null, null, null);
        articlesRep.save(a1);

        Articles a2 = new Articles(
                null, "45374136", "09747", "Arena 120 nici",
                new BigDecimal(30.0), "szt.", new BigDecimal(1.64), 7,
                new BigDecimal(1.53), new BigDecimal(1.88), new BigDecimal(45.9), 23, new BigDecimal(10.56),
                        new BigDecimal(56.46), hm.convertStringToDate("30-12-2016"), 100, new BigDecimal(3.06), "Nici",
                null, null, null, null, null);
        articlesRep.save(a2);

        Articles a3 = new Articles(
                null, "98541512", "K286", "Ręcznik Klasik 70/140",
                new BigDecimal(6.0), "szt.", new BigDecimal(18.74), 7,
                new BigDecimal(17.43), new BigDecimal(21.44), new BigDecimal(104.58), 23, new BigDecimal(24.05),
                new BigDecimal(128.63), hm.convertStringToDate("30-12-2016"), 100, new BigDecimal(34.86), "Ręczniki",
                null, null, null, null, null);
        articlesRep.save(a3);

        Articles a4 = new Articles(
                null, "45123456", "K286", "testowy art",
                new BigDecimal(50.0), "m", new BigDecimal(1.14), 5,
                new BigDecimal(1.09), new BigDecimal(1.34), new BigDecimal(65.4), 23, new BigDecimal(15.04),
                new BigDecimal(80.44), hm.convertStringToDate("30-12-2016"), 100, new BigDecimal(2.18), "Dodatki krawieckie",
                null, null, null, null, null);
        articlesRep.save(a4);
        */
/*
        articlesAdditionalInfoRep.save(
                new ArticlesAdditionalInfo(
                        null, new BigDecimal(60.0), hm.convertStringToDate("30-12-2016"), a1, is1
                ));

        articlesAdditionalInfoRep.save(
                new ArticlesAdditionalInfo(
                        null, new BigDecimal(30.0), hm.convertStringToDate("30-12-2016"), a2, is2
                ));

        articlesAdditionalInfoRep.save(
                new ArticlesAdditionalInfo(
                        null, new BigDecimal(6.0), hm.convertStringToDate("30-12-2016"), a3, is1
                ));

        articlesAdditionalInfoRep.save(
                new ArticlesAdditionalInfo(
                        null, new BigDecimal(5.0), hm.convertStringToDate("20-12-2016"), a3, is3
                ));


        soldArticlesRep.save(
                new SoldArticles(null, 1, new BigDecimal(5.0), 0,
                        new BigDecimal(3.06), hm.convertStringToDate("data"), a2
                ));

        soldArticlesRep.save(
                new SoldArticles(null, 2, new BigDecimal(10.0), 0,
                        new BigDecimal(3.06), hm.convertStringToDate("data"), a2
                ));
        soldArticlesRep.save(
                new SoldArticles(null, 3, new BigDecimal(2.0), 0,
                new BigDecimal(34.86), hm.convertStringToDate("15-12-2016"), a3
                ));
        soldArticlesRep.save(
                new SoldArticles(null, 4, new BigDecimal(4.0), 0,
                new BigDecimal(34.86), hm.convertStringToDate("20-12-2016"), a3
                ));

        articlesToOrderRep.save(
                new ArticlesToOrder(null, "45374136", new BigDecimal(6.0), hm.convertStringToDate("data"), a2));

        articlesToOrderRep.save(
                new ArticlesToOrder(null, "98541512", new BigDecimal(15.0), hm.convertStringToDate("data"), a3));
*/
/*
        CategoryTemp ct1 = new CategoryTemp(null, "Artykuły krawieckie",
                "null", "null", "null", "null", null);
        categoryTmpRep.save(ct1);

        CategoryTemp ct2 = new CategoryTemp(null, "Nici, włóczki, sznurki",
                "null", "null", "null", "null", null);
        categoryTmpRep.save(ct2);
*/
/*
        Category ct = new Category(null,"98541512","RĘCZNIKI",null,null,null,null,null,null,null,"70x140",null,null,null, null, null, a3);
        categoryRep.save(ct);

        InvoicesSupplyTmp it1 = new InvoicesSupplyTmp(null, "14456/01/2016", "BafPol",
                hm.convertStringToDate("05-12-2016"));
        invoicesSupplyTmpRep.save(it1);
        */
/*
        articlesTmpRep.save(
                new NewSupplyTemp(null, "68685859", "04593",
                        "Mydełko krawieckie", new BigDecimal(10.0), "szt.",
                        new BigDecimal(7.25), 7, new BigDecimal(0.68), new BigDecimal(0.83),
                                new BigDecimal(6.75), 23, new BigDecimal(1.55), new BigDecimal(8.3), 100,
                        ct1));

        articlesTmpRep.save(
                new NewSupplyTemp(null, "25478965", "00985",
                        "Mulina", new BigDecimal(10.0), "szt.",
                new BigDecimal(1.1), 7, new BigDecimal(0.94), new BigDecimal(1.15),
                new BigDecimal(9.37), 23, new BigDecimal(2.16), new BigDecimal(11.53), 100,
                        ct2));
*/

        //  List<Articles> listFull =articlesRep.findFullInfo();
        //  List<Articles> listaArt = articlesRep.findByQuantityFromTo(new Integer("10"), new Integer("50"));
        //   printAll(listaArt);
    }

    private void printAll(List<Articles> allUnsorted) {
        allUnsorted.forEach(logger::info);
    }


}
