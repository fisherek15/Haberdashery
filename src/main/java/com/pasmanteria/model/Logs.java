package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by adrian on 08.12.2016.
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Logs {

    @Id
    @GeneratedValue
    private Long logId;

    @Column(nullable = false) private Date dateAdded;
    @Column(nullable = false) private String tableName;
    @Column(nullable = false) private Integer idFromTable;
    @Column(nullable = false) private String operation;
}
