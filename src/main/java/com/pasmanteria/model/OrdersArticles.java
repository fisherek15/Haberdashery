package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by adrian on 06.07.2017.
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class OrdersArticles {

    @Id
    @GeneratedValue
    private Long ordArtId;

    @Column(nullable = false) private String orderNumber;
    @Column(nullable = false) private BigDecimal quantity;
    @Column(nullable = false) private Date dateAdded;

    @ManyToOne(optional = false)
    @JoinColumn(name = "artId")
    private Articles articles;


}
