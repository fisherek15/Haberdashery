package com.pasmanteria.repository;

import com.pasmanteria.model.InvoicesCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by adrian on 12.07.2017.
 */
public interface InvoicesCustomerRep extends JpaRepository<InvoicesCustomer, Long> {
}
