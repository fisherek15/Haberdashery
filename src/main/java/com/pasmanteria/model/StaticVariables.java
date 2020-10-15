package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by adrian on 21.08.2017.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class StaticVariables {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long staVarId;

    @Column(nullable = false) private String name;
    private int value;
    private String stringValue;
}
