package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by adrian on 12.07.2017.
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Receipts {

    @Id
    @GeneratedValue
    private Long recId;
}
