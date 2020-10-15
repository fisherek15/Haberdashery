package com.pasmanteria.repository;

import com.pasmanteria.model.InvoicesSupply;
import com.pasmanteria.model.InvoicesSupplyTmp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by adrian on 30.12.2016.
 */
public interface InvoicesSupplyRep extends JpaRepository<InvoicesSupply, Long> {

    List<InvoicesSupply> findAll();

    //zapytanie wyswietlajace fakture zawierajaca spis zamowionych artykulow
}
