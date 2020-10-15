package com.pasmanteria.repository;

import com.pasmanteria.model.ArticlesAdditionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by adrian on 17.12.2016.
 */
public interface ArticlesAdditionalInfoRep extends JpaRepository<ArticlesAdditionalInfo, Long> {

    //tu na razie bez zapytan
}
