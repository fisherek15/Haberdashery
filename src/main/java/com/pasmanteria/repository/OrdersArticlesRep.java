package com.pasmanteria.repository;

import com.pasmanteria.model.OrdersArticles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by adrian on 06.07.2017.
 */
public interface OrdersArticlesRep extends JpaRepository<OrdersArticles, Long> {

    @Query(
            name = "OrdersArticles.findByOrderNumber",
            value = "SELECT t.orderNumber, p.barcode, p.catalogNumber, p.articleName, t.quantity, " +
                    "p.unitOfMeasurement " +
                    "FROM OrdersArticles t JOIN t.articles p " +
                    "where t.orderNumber = ?1")
    List<Object[]> findByOrderNumber(String orderNumber);

    @Query(
            name = "OrdersArticles.findOrders",
            value = "SELECT t.orderNumber, t.dateAdded " +
                    "FROM OrdersArticles t " +
                    "GROUP BY t.orderNumber " +
                    "ORDER BY t.dateAdded DESC ")
    List<Object[]> findOrders();

    @Query(
            name = "OrdersArticles.findArticlesToOrder",
            value = "SELECT p.barcode, p.catalogNumber, p.articleName, t.quantity, " +
                    "p.unitOfMeasurement, i.wholesale " +
                    "FROM OrdersArticles t JOIN t.articles p JOIN p.articlesAdditionalInfoList a " +
                    "JOIN a.invoicesSupply i " +
                    "WHERE t.orderNumber = ?1")
    List<Object[]> findArticlesToOrder(String orderNumber);

}

