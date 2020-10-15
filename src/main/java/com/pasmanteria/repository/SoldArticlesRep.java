package com.pasmanteria.repository;

import com.pasmanteria.model.SoldArticles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by adrian on 16.12.2016.
 */
public interface SoldArticlesRep extends JpaRepository<SoldArticles, Long> {

    @Query(
            name = "SoldArticles.standardFindAll",
            value = "SELECT t.transactionId, p.barcode, p.catalogNumber, p.articleName, t.quantity, " +
                    "p.unitOfMeasurement, t.discountForCustomerPercent, t.endPrice, t.dateOfSale " +
                    "FROM SoldArticles t LEFT JOIN t.articles p ")
    List<Object[]> standardFindAll();

    @Query(
            name = "SoldArticles.findAllFromDateToDate",
            value = "SELECT t.transactionId, p.barcode, p.catalogNumber, p.articleName, t.quantity, " +
                    "p.unitOfMeasurement, t.discountForCustomerPercent, t.endPrice, t.dateOfSale " +
                    "FROM SoldArticles t LEFT JOIN t.articles p " +
                    "WHERE t.dateOfSale BETWEEN ?1 AND ?2")
    List<Object[]> findAllFromDateToDate(Date dateFrom, Date dateTo);

    @Query(
            name = "SoldArticles.findLeastOftenSold",
            value = "SELECT p.barcode, p.articleName, SUM(t.quantity), " +
                    "p.unitOfMeasurement, COUNT(*) as amount " +
                    "FROM SoldArticles t LEFT JOIN t.articles p " +
                    "WHERE t.dateOfSale BETWEEN ?1 AND ?2 " +
                    "GROUP BY p.barcode " +
                    "ORDER BY amount ASC")
    List<Object[]> findLeastOftenSold(Date dateFrom, Date dateTo);

    @Query(
            name = "SoldArticles.findMostOftenSold",
            value = "SELECT p.barcode, p.articleName, SUM(t.quantity), " +
                    "p.unitOfMeasurement, COUNT(*) as amount " +
                    "FROM SoldArticles t LEFT JOIN t.articles p " +
                    "WHERE t.dateOfSale BETWEEN ?1 AND ?2 " +
                    "GROUP BY p.barcode " +
                    "ORDER BY amount DESC")
    List<Object[]> findMostOftenSold(Date dateFrom, Date dateTo);

    @Query(
            name = "SoldArticles.findLeastSold",
            value = "SELECT p.barcode, p.articleName, SUM(t.quantity), " +
                    "p.unitOfMeasurement, COUNT(*) " +
                    "FROM SoldArticles t LEFT JOIN t.articles p " +
                    "WHERE t.dateOfSale BETWEEN ?1 AND ?2 " +
                    "GROUP BY p.barcode " +
                    "ORDER BY SUM(t.quantity) ASC")
    List<Object[]> findLeastSold(Date dateFrom, Date dateTo);

    @Query(
            name = "SoldArticles.findMostSold",
            value = "SELECT p.barcode, p.articleName, SUM(t.quantity), " +
                    "p.unitOfMeasurement, COUNT(*) " +
                    "FROM SoldArticles t LEFT JOIN t.articles p " +
                    "WHERE t.dateOfSale BETWEEN ?1 AND ?2 " +
                    "GROUP BY p.barcode " +
                    "ORDER BY SUM(t.quantity) DESC")
    List<Object[]> findMostSold(Date dateFrom, Date dateTo);

    List<SoldArticles> findByTransactionId(Integer transactionId);

}


