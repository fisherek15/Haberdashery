package com.pasmanteria.repository;

import com.pasmanteria.model.Receipts;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by adrian on 12.07.2017.
 */
public interface ReceiptsRep extends JpaRepository<Receipts, Long> {
}
