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
public class SoldArticles {

    @Id
    @GeneratedValue
    private Long solArtId;

    @Column(nullable = false) private Integer transactionId;
    @Column(nullable = false) private BigDecimal quantity;
    @Column(nullable = false) private Integer discountForCustomerPercent; // zniżka dla klienta (podana w %)
    @Column(nullable = false) private BigDecimal endPrice; // cena sztuki po uwzględnieniu zniżki klienta
    @Column(nullable = false) private Date dateOfSale;

    @ManyToOne//(optional = false)
    @JoinColumn(name = "artId")
    private Articles articles;
}
