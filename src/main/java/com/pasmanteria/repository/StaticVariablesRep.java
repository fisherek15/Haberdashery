package com.pasmanteria.repository;

import com.pasmanteria.model.StaticVariables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by adrian on 21.08.2017.
 */
public interface StaticVariablesRep extends JpaRepository<StaticVariables, Long> {

    StaticVariables findByName(String name);

    @Transactional
    @Modifying// (clearAutomatically = true)
    @Query(
            name = "StaticVariables.editOneByName",
            value = "UPDATE StaticVariables e set e.value = ?2 " +
                    "where e.name = ?1"
    )
    void editOneByName(String name, Integer value);
}
