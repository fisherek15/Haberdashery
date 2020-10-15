package com.pasmanteria;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.IndexedContainer;

import java.math.BigDecimal;
import java.text.CharacterIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by adrian on 08.01.2017.
 */
public class HelpfulMethods {

    private static Random rand = new Random();

    public static Date convertStringToDate(String stringDate) {
        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
        Date t;
        try {
            t = ft.parse(stringDate);
            return t;
        }catch (ParseException e) {
            System.out.println("Unparseable using " + ft);
            return new Date();
        }
    }

    public static String convertDateToString(Date date) {
        Date currentDate = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormat.format(currentDate);

        return dateString;
    }

    public static String convertDateToNameOfDocument(Date date) {
        Date currentDate = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String dateString = dateFormat.format(currentDate);

        return dateString;
    }

    public static String convertDateToHeaderOfDocument(Date date) {
        Date currentDate = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String dateString = dateFormat.format(currentDate);

        return dateString;
    }


    public static Integer convertStringToInteger(String str) {
        str = str.replaceAll("\\s", "");
        try
        {
            int value = Integer.parseInt(str);
            return value;
        }
        catch (NumberFormatException nfe)
        {
            nfe.printStackTrace();
            return null;
        }
    }

    public static BigDecimal convertStringAmountToBigDecimal(String str) {
        str = str.replace(",", ".");
        str = str.replaceAll("\\s","");
        return new BigDecimal(str);
    }

    public static String generateOrderNumber() {

        Calendar calendar = Calendar.getInstance();
        String orderNumber = rand.nextInt(100) + "/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH);
        return orderNumber;
    }

    public static String makeCategoryPath(String... args) {
        String path = new String();
        char arrow = '\u2192';
        for(int i = 0; i < args.length; i++) {
            path += args[i];
            if(i < args.length - 1) path += " " + arrow + " ";
        }
        return path;
    }

}
