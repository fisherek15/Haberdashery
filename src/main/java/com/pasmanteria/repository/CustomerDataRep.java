package com.pasmanteria.repository;

import com.pasmanteria.model.CustomerData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by adrian on 12.07.2017.
 */
public interface CustomerDataRep extends JpaRepository<CustomerData, Long> {
}
