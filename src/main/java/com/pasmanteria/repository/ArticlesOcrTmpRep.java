package com.pasmanteria.repository;

import com.pasmanteria.model.ArticlesOcrTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by adrian on 08.05.2017.
 */
public interface ArticlesOcrTmpRep extends JpaRepository<ArticlesOcrTmp, Long> {

 //   @Query(
 //           name = "ArticlesOcrTmp.findAll",
 //           value = "SELECT ALL FROM ArticlesOcrTmp")
    List<ArticlesOcrTmp> findAll();

    @Query(
            name = "ArticlesOcrTmp.existByBarcode",
            value = "SELECT p.artOcrTmpId FROM ArticlesOcrTmp p where barcode = ?1"
    )
    Long existByBarcode(String barcode);

}
