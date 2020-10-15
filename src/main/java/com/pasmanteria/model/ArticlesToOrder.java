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

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class ArticlesToOrder {

    @Id
    @GeneratedValue
    private Long artToOrdId;

    @Column(nullable = false) private String barcode;
    @Column(nullable = false) private BigDecimal quantity;
    @Column(nullable = false) private Date dateAdded;

    @ManyToOne
    @JoinColumn(name = "artId")
    private Articles articles;
}
