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
public class ArticlesAdditionalInfo {

    @Id
    @GeneratedValue
    private Long artAdInfId;

    @Column(nullable = false) private BigDecimal quantity;
    @Column(nullable = false) private Date dateAdded;

    @ManyToOne(optional = false)
    @JoinColumn(name = "artId")
    private Articles articles;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invSupId")
    private InvoicesSupply invoicesSupply;
}