package com.pasmanteria.repository;

import com.pasmanteria.model.Articles;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by adrian on 08.12.2016.
 */
public interface ArticlesRep extends JpaRepository<Articles, Long> {

    List<Articles> findByCatalogNumberStartsWithIgnoreCase(String catalogNumber);
/*
    @Query(
            name = "Articles.findByQuantityFromTo",
            value = "select e.barcode, e.articleName, e.quantity from Articles e where quantity between ?1 and ?2"
    )
    List<Articles> findByQuantityFromTo(Integer from, Integer to);
*/
/*
    @Query(
            name = "Articles.standardFindAll",
            value = "select e.barcode, e.catalogNumber, e.articleName, e.quantity, e.unitOfMeasurement, " +
                    "e.priceMargin, e.priceGrossPcs from Articles e"
    )
*/
    //List<Articles> findAll();
/*
    @Query(
            name = "Articles.findAllBarcode",
            value = "select e.barcode from Articles e"
    )
    List<Articles>  findAllBarcode();
*/
    @Query(
            name = "Articles.findFullInfo",
            value = "SELECT t.artId, t.barcode, t.articleName, p.artAdInfId " +
                    "FROM Articles t JOIN t.articlesAdditionalInfoList p")
    List<Object[]> findFullInfo();
/*
    @Query(
            name = "Articles.findOneByBarcode",
            value = "select e.barcode, e.catalogNumber, e.articleName, e.unitOfMeasurement, " +
                    "e.discountPercent, e.taxPercent, e.marginPercent, e.priceMargin " +
                    "from Articles e where barcode = ?1"
    )
*/

    Articles findOneByBarcode(String barcode);
/*
    @Query(
            name = "Articles.findByCatalogNumber",
            value = "select e.barcode, e.catalogNumber, e.articleName, e.quantity, e.unitOfMeasurement, " +
                    "e.discountPercent, e.taxPercent, e.marginPercent, e.priceMargin " +
                    "from Articles e where catalogNumber = ?1"
    )
*/
    List<Articles> findByCatalogNumber(String catalogNumber);
/*
    @Query(
            name = "Articles.findByBarcode",
            value = "select e.barcode, e.catalogNumber, e.articleName, e.quantity, e.unitOfMeasurement, " +
                    "e.discountPercent, e.taxPercent, e.marginPercent, e.priceMargin " +
                    "from Articles e where barcode = ?1"
    )
    List<Object[]> findByBarcode(String barcode);
*/
    @Transactional
    @Modifying//(clearAutomatically = true)
    @Query(
            name = "Articles.editOneByBarcode",
            value = "UPDATE Articles e set e.quantity = ?2 " +
                    "where barcode = ?1"
    )
    void editOneByBarcode(String barcode, BigDecimal quantity);
/*
    @Query(
            name = "Articles.findIdByBarcode",
            value = "select e.artId " +
                    "from Articles e where barcode = ?1"
    )
    Long findIdByBarcode(String barcode);
*/
/*
    @Query(
            name = "Articles.findQuantityByBarcode",
            value = "select e.quantity " +
                    "from Articles e where barcode = ?1"
    )
    BigDecimal findQuantityByBarcode(String barcode);
*/

    @Query(
            name = "Articles.findAllFromSpecificCategory",
            value = "SELECT a.barcode, a.catalogNumber, a.articleName, " +
                    "a.quantity, a.unitOfMeasurement, a.priceMargin " +
                    "FROM Articles a JOIN a.category c " +
                    "WHERE c.mainCategory = ?1"
    )
    List<Object[]> findAllFromSpecificCategory(String mainCategory);

    @Query(
            name = "Articles.findAllFromAllCategories",
            value = "SELECT a.barcode, a.catalogNumber, a.articleName, " +
                    "a.quantity, a.unitOfMeasurement, a.priceMargin " +
                    "FROM Articles a"
    )
    List<Object[]> findAllFromAllCategories();

    @Query(
            name = "Articles.findSpecificFromKoronki",
            value = "SELECT a.barcode, a.catalogNumber, a.articleName, " +
                    "a.quantity, a.unitOfMeasurement, a.priceMargin " +
                    "FROM Articles a JOIN a.category c " +
                    "WHERE c.type1 = ?1 AND c.width = ?2"
    )
    List<Object[]> findSpecificFromKoronki(String type1, Integer width);

}

