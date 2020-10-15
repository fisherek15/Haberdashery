package com.pasmanteria;

import java.util.List;

/**
 * Created by adrian on 07.08.2017.
 */
public class QueryMapper {

    public static Object[][] readDataFromQuery(List<Object[]> result) {
        //System.out.println(result);
        if (result.size() != 0) {
            Object[][] tabArt = new Object[result.size()][result.get(0).length];
            int i = 0;
            for (Object[] objects : result) {
                int j = 0;
                for (Object object : objects) {
                    tabArt[i][j] = object;
                    //System.out.println(tabArt[i][j]);
                    j++;
                }
                i++;
            }
            return tabArt;
        } else {
            return null;
        }
    }

}
