package com.pasmanteria.repository;

import com.pasmanteria.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Adrian on 10/29/2017.
 */
public interface CategoryRep extends JpaRepository<Category, Long>, JpaSpecificationExecutor {
}
