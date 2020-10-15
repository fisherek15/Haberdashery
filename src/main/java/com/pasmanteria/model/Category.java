package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by adrian on 08.12.2016.
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Category {

    @Id
    @GeneratedValue
    private Long catId;

    @Column(nullable = false) private String barcode;
    @Column(nullable = false) private String mainCategory;
    private String type1;
    private String type2;
    private String type3;
    private String color;
    private String colorNr;
    private String empty1;
    private String empty2;
    private Integer size;
    private Integer thickness;
    private String designation;
    private Integer width;
    private Float length;
    private Integer weight;
    private Integer lengthInt;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "artId", nullable = false)
    private Articles articles;


}
