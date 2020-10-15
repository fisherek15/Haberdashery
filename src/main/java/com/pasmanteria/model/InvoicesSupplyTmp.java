package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by adrian on 30.12.2016.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class InvoicesSupplyTmp {

    @Id
    private Long invSupTmpId;

    @Column(nullable = false) private String invoiceNr;
    @Column(nullable = false) private String wholesale;
    @Column(nullable = false) private Date dateOfIssue;

}
