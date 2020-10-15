package com.pasmanteria.repository;

import com.pasmanteria.model.CategoryTmp;
import com.pasmanteria.model.CategoryTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by adrian on 05.01.2017.
 */
public interface CategoryTmpRep extends JpaRepository<CategoryTmp, Long> {

    @Transactional
    @Modifying
    @Query(
            name = "CategoryTmp.editOneByBarcode",
            value = "UPDATE CategoryTmp e set e.barcode = ?2, e.mainCategory = ?3, e.type1 = ?4, e.type2 = ?5, " +
                    "e.type3 = ?6, e.color = ?7, e.colorNr= ?8, e.empty1 = ?9, " +
                    "e.empty2 = ?10, e.size = ?11, e.thickness = ?12, e.designation = ?13, e.width = ?14, " +
                    "e.length = ?15, e.weight = ?16, e.lengthInt = ?17 " +
                    "where catTmpId = ?1"
    )
    void editOneByBarcode(Long catTmpId, String newBarcode, String mainCategory, String type1, String type2,
            String type3, String color, String colorNr, String empty1, String empty2, Integer size,
            Integer thickness, String designation, Integer width, Float length, Integer weight, Integer lenghtInt
    );

    @Query(
            name = "CategoryTmp.getIdByBarcode",
            value = "select e.catTmpId from CategoryTmp e where barcode = ?1"
    )
    Long getIdByBarcode(String barcode);

    @Query(
            name = "CategoryTmp.getOneByBarcode",
            value = "select e from CategoryTmp e where barcode = ?1"
    )
    CategoryTmp getOneByBarcode(String barcode);
}