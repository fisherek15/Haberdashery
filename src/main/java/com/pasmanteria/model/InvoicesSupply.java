package com.pasmanteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by adrian on 08.12.2016.
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class InvoicesSupply {

    @Id
    @GeneratedValue
    private Long invSupId;

    @Column(nullable = false) private String invoiceNr;
    @Column(nullable = false) private String wholesale;
    @Column(nullable = false) private Date dateAdded;
    @Column(nullable = false) private Date dateOfIssue;

    @OneToMany(mappedBy="invoicesSupply", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ArticlesAdditionalInfo> articlesAdditionalInfoList;
}
