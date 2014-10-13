package br.com.epicdroid.travel.utils;

import java.math.BigDecimal;

/**
 * Created by call on 10/13/14.
 */
public class CurrencyUtils {
    public static boolean isHigher(BigDecimal first, BigDecimal second){
        if (first.compareTo(second) == 1){
           return true;
        }
        return false;
    }

    public static boolean isHigherThanZero(BigDecimal first){
        if (first.compareTo(BigDecimal.ZERO) == 1){
            return true;
        }
        return false;
    }
}
