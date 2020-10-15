package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by adrian on 08.12.2016.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
//@Table(name = "articles")
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long artId;

    @Column(nullable = false) private String barcode;
    private String catalogNumber;
    @Column(nullable = false) private String articleName;
    @Column(nullable = false) private BigDecimal quantity;
    @Column(nullable = false) private String unitOfMeasurement;
    @Column(nullable = false) private BigDecimal priceNetWithoutDiscount; //cena całkowita netto bez rabatu
    @Column(nullable = false) private Integer discountPercent; // wysokość rabatu w procentach
    @Column(nullable = false) private BigDecimal priceNetPcs; // cena sztuki netto z rabatem
    @Column(nullable = false) private BigDecimal priceGrossPcs; // cena sztuki brutto z rabatem
    @Column(nullable = false) private BigDecimal valueNet; // wartość netto z rabatem
    @Column(nullable = false) private Integer taxPercent; // wysokość podatku VAT w procentach
    @Column(nullable = false) private BigDecimal valueVat; //wartość VAT
    @Column(nullable = false) private BigDecimal priceGrossTotality; // cena brutto z rabatem
    @Column(nullable = false) private Date dateAdded;
    @Column(nullable = false) private Integer marginPercent; // procent marży
    @Column(nullable = false) private BigDecimal priceMargin; // cena sztuki z marża
    @Column(nullable = false) private String categoryTxt;

    @OneToMany(mappedBy="articles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ArticlesAdditionalInfo> articlesAdditionalInfoList;

    @OneToMany(mappedBy="articles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdersArticles> ordersArticlesList;

    @OneToMany(mappedBy="articles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SoldArticles> soldArticlesList;

    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ArticlesToOrder> articlesToOrder;

    @OneToOne(mappedBy = "articles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Category category;

}


