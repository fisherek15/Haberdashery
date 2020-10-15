package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Adrian on 4/11/2017.
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class InvoiceFieldsTmp {

    @Id
    @GeneratedValue
    private Long invFieTmpId;

    @Column(nullable = false) private String fieldName;
    @Column(nullable = false) private Integer topY;
    @Column(nullable = false) private Integer leftX;
    @Column(nullable = false) private Integer heightY;
    @Column(nullable = false) private Integer widthX;
}
