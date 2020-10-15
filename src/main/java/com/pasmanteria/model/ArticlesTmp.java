package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by adrian on 08.12.2016.
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class ArticlesTmp {

    @Id
    @GeneratedValue
    private Long artTmpId;

    @Column(nullable = false) private String barcode;
    @Column(nullable = false)private String catalogNumber;
    @Column(nullable = false) private String articleName;
    @Column(nullable = false) private BigDecimal quantity;
    @Column(nullable = false) private String unitOfMeasurement;
    @Column(nullable = false) private BigDecimal priceNetWithoutDiscount;
    @Column(nullable = false) private Integer discountPercent;
    @Column(nullable = false) private BigDecimal priceNetPcs;
    @Column(nullable = false) private BigDecimal priceGrossPcs;
    @Column(nullable = false) private BigDecimal valueNet;
    @Column(nullable = false) private Integer taxPercent;
    @Column(nullable = false) private BigDecimal valueVat;
    @Column(nullable = false) private BigDecimal priceGrossTotality;
    @Column(nullable = false) private Integer marginPercent;
    @Column(nullable = false) private BigDecimal priceMargin;
    @Column(nullable = false) private String categoryTxt;

    @OneToOne
    @JoinColumn(name = "catTmpId")
    private CategoryTmp categoryTmp;

}
