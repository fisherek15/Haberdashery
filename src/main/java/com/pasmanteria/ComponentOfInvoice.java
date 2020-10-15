package com.pasmanteria;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Created by adrian on 08.01.2017.
 */

@Getter
@ToString
public class ComponentOfInvoice {

    private BigDecimal priceNetWithoutDiscount;
    private BigDecimal priceNetPcs;
    private BigDecimal priceGrossPcs;
    private BigDecimal valueNet;
    private BigDecimal valueVat;
    private BigDecimal priceMargin;

    public ComponentOfInvoice(BigDecimal priceGrossTotality, BigDecimal quantity,
                              BigDecimal discountPercent, BigDecimal taxPercent, BigDecimal marginPercent ) {

        BigDecimal tax = new BigDecimal(1.00).add(taxPercent.divide(new BigDecimal(100.00)));
        System.out.println(tax);
        BigDecimal discount = new BigDecimal(1.00).subtract(discountPercent.divide(new BigDecimal(100.00)));
        System.out.println(discount);
        BigDecimal margin = new BigDecimal(1.00).add(marginPercent.divide(new BigDecimal(100.00)));
        System.out.println(margin);

        valueNet = priceGrossTotality.divide(tax,BigDecimal.ROUND_HALF_EVEN);
        System.out.println(valueNet);
        valueVat = priceGrossTotality.subtract(valueNet);
        System.out.println(valueVat);
        priceGrossPcs = priceGrossTotality.divide(quantity, BigDecimal.ROUND_HALF_EVEN);
        System.out.println(priceGrossPcs);
        priceNetPcs = valueNet.divide(quantity, BigDecimal.ROUND_HALF_EVEN);
        System.out.println(priceNetPcs);
        priceMargin = priceNetPcs.multiply(margin);
        System.out.println(priceMargin);
        priceNetWithoutDiscount = priceNetPcs.divide(discount, BigDecimal.ROUND_HALF_EVEN);
        System.out.println(priceNetWithoutDiscount);
    }
}
