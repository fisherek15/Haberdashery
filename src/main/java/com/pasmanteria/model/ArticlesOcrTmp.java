package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by adrian on 08.05.2017.
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class ArticlesOcrTmp {

    @Id
    @GeneratedValue
    private Long artOcrTmpId;

    @Column(nullable = false) private String barcode;
    @Column(nullable = false) private String catalogNumber;
    @Column(nullable = false) private String articleName;
    @Column(nullable = false) private Integer discountPercent;
    @Column(nullable = false) private Integer taxPercent;
    @Column(nullable = false) private BigDecimal priceGrossTotality;
}
