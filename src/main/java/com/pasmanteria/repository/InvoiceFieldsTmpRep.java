package com.pasmanteria.repository;

import com.pasmanteria.model.InvoiceFieldsTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by adrian on 05.05.2017.
 */
public interface InvoiceFieldsTmpRep extends JpaRepository<InvoiceFieldsTmp, Long> {
/*
    @Query(
            name = "InvoiceFieldsTmp.findAll",
            value = "SELECT p.fieldName, p.leftX, p.topY, p.heightY, p.widthX FROM InvoiceFieldsTmp p")
*/
    List<InvoiceFieldsTmp> findAll();
}
