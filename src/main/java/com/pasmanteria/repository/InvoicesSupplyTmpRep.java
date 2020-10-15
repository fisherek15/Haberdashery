package com.pasmanteria.repository;

import com.pasmanteria.model.InvoicesSupply;
import com.pasmanteria.model.InvoicesSupplyTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by adrian on 05.01.2017.
 */
public interface InvoicesSupplyTmpRep extends JpaRepository<InvoicesSupplyTmp, Long> {

}
