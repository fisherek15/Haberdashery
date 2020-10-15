package com.pasmanteria.repository;

import com.pasmanteria.model.ArticlesToOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by adrian on 16.12.2016.
 */
public interface ArticlesToOrderRep extends JpaRepository<ArticlesToOrder, Long> {
/*
    @Query(
            name = "ArticlesToOrder.findAllToGrid",
            value = "SELECT p.barcode, p.catalogNumber, p.articleName, sum(t.quantity), " +
                    "p.unitOfMeasurement, i.wholesale " +
                    "FROM ArticlesToOrder t LEFT JOIN t.articles p JOIN p.articlesAdditionalInfoList a " +
                    "JOIN a.invoicesSupply i " +
                    "group by p.barcode")
    List<Object[]> findAllToGrid();
*/
    @Query(
            name = "ArticlesToOrder.findAllToGrid",
            value = "SELECT a.art_id, a.barcode, a.catalog_number, a.article_name, sum(ato.quantity) AS quantity, " +
                    "a.unit_of_measurement, aai_is.wholesale " +
                    "FROM articles_to_order as ato JOIN articles as a ON ato.art_id=a.art_id JOIN " +
                    "(SELECT DISTINCT aai.art_id, isu.wholesale FROM articles_additional_info as aai " +
                    "JOIN invoices_supply as isu ON aai.inv_sup_id=isu.inv_sup_id) AS aai_is ON a.art_id=aai_is.art_id " +
                    "group by a.barcode", nativeQuery = true)
    List<Object[]> findAllToGrid();
/*
    @Query(
            name = "ArticlesToOrder.findAllId",
            value = "SELECT p.artToOrdId FROM ArticlesToOrder p")
    List<Long> findAllId();
*/
    @Query(
            name = "ArticlesToOrder.findSelectedCol",
            value = "SELECT t.artId, p.quantity FROM ArticlesToOrder p JOIN p.articles t")
    List<Object[]> findSelectedCol();

    ArticlesToOrder findOneByBarcode(String barcode);

    @Transactional
    @Modifying//(clearAutomatically = true)
    @Query(
            name = "ArticlesToOrder.editOneByBarcode",
            value = "UPDATE ArticlesToOrder e set e.quantity = ?2 " +
                    "where barcode = ?1"
    )
    void editOneByBarcode(String barcode, BigDecimal quantity);

}
