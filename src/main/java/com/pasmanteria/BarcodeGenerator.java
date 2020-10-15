package com.pasmanteria;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Adrian on 28/11/2017.
 *
 *  EAN13 Barcode
 *  All generated barcodes start from 200 - 299 numbers. It's designed only to inside using.
 */
public class BarcodeGenerator {

    public static String generateBarcode() {
        Random rand = new Random();
        int min = 0;
        int max = 9;

        String EAN13 = new String();
        int rawNumber[] = new int[13];

        // pierwszy znak zawsze taki sam - stosowany dla wew. numeracji
        rawNumber[0] = 2;

        // losuje 11 liczb
        for(int i = 1; i < 12; i++){
            int x = rand.nextInt((max - min) + 1) + min;
            rawNumber[i] = x;
        }

        // dopisuje znak sumy kontrolnej
        rawNumber[12] = countCheckSum(rawNumber);

        // zamienia ciag liczb na barcode w postaci stringa
        for(int number : rawNumber){
            EAN13 = EAN13.concat(Integer.toString(number));
        }

        return EAN13;
    }

    public static int countCheckSum(int[] rawNumber){
        int sum = 1 * rawNumber[0] +
                3 * rawNumber[1] +
                1 * rawNumber[2] +
                3 * rawNumber[3] +
                1 * rawNumber[4] +
                3 * rawNumber[5] +
                1 * rawNumber[6] +
                3 * rawNumber[7] +
                1 * rawNumber[8] +
                3 * rawNumber[9] +
                1 * rawNumber[10] +
                3 * rawNumber[11];
        sum %= 10;
        sum = 10 - sum;
        sum %= 10;

        return sum;
    }

    public static boolean barcodeCheckSum(String EAN13){
        if(EAN13.length() != 13) {
            return false;
        } else {
            String stringArray[];
            int intArray[];

            stringArray = EAN13.split("");
            intArray = Arrays.stream(stringArray).mapToInt(Integer::parseInt).toArray();

            int sum = countCheckSum(intArray);

            if (sum == intArray[12]) {
                return true;
            } else {
                return false;
            }
        }
    }

}
