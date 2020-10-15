package com.pasmanteria.repository;

import com.pasmanteria.model.Articles;
import com.pasmanteria.model.ArticlesTmp;
import com.pasmanteria.model.ArticlesTmp;
import com.pasmanteria.model.CategoryTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by adrian on 16.12.2016.
 */

public interface ArticlesTmpRep extends JpaRepository<ArticlesTmp, Long> {
/*
    @Query(
            name = "ArticlesTmp.standardFindAll",
            value = "SELECT p.barcode, p.catalogNumber, p.articleName, p.quantity, " +
                    "p.unitOfMeasurement, p.discountPercent, p.taxPercent, p.priceGrossTotality, " +
                    "p.marginPercent " +
                    "FROM ArticlesTmp p")
*/

    List<ArticlesTmp> findAll();

    ArticlesTmp findOneByBarcode(String barcode);

/*
    @Query(
            name = "ArticlesTmp.findByCatalogNumber",
            value = "select e.barcode, e.catalogNumber, e.articleName, e.quantity, " +
                    "e.unitOfMeasurement, e.discountPercent, e.taxPercent, e.priceGrossTotality, " +
                    "e.marginPercent " +
                    "from ArticlesTmp e where catalogNumber = ?1"
    )
    List<Object[]> findByCatalogNumber(String catalogNumber);
*/

// to zapytanie mi nie pasuje. Przyjzec sie lepiej temu!
    @Transactional
    @Modifying//(clearAutomatically = true)
    @Query(
            name = "ArticlesTmp.editOneById",
            value = "UPDATE ArticlesTmp e set e.barcode = ?2, e.articleName = ?3, e.quantity = ?4, e.unitOfMeasurement = ?5, " +
                    "e.priceNetWithoutDiscount = ?6, e.discountPercent = ?7, e.priceNetPcs = ?8, e.priceGrossPcs = ?9, " +
                    "e. valueNet = ?10, e.taxPercent = ?11, e.valueVat = ?12, e.priceGrossTotality = ?13, e.marginPercent = ?14, e.priceMargin = ?15, e.categoryTxt = ?16 " +
                    "where artTmpId = ?1"
    )
    void editOneById(Long newSuppId, String barcode, String articleName, BigDecimal quantity, String unitOfMeasurement,
                     BigDecimal priceNetWithoutDiscount, Integer discountPercent, BigDecimal priceNetPcs,
                     BigDecimal priceGrossPcs, BigDecimal valueNet, Integer taxPercent, BigDecimal valueVat,
                     BigDecimal priceGrossTotality, Integer marginPercent, BigDecimal priceMargin, String categoryTxt);

    @Query(
            name = "ArticlesTmp.existByBarcode",
            value = "select e.artTmpId from ArticlesTmp e where barcode = ?1"
    )
    Long existByBarcode(String barcode);

    @Query(
            name = "ArticlesTmp.getCategoryTxtByBarcode",
            value = "select e.categoryTxt from ArticlesTmp e where barcode = ?1"
    )
    String getCategoryTxtByBarcode(String barcode);
/*
    @Query(
            name = "ArticlesTmp.getCategoryIdByBarcode",
            value = "select e.catTmpId from ArticlesTmp e where barcode = ?1"
    )
    CategoryTmp getCategoryIdByBarcode(String barcode);
*/
}

