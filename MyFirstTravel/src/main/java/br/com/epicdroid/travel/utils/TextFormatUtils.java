package br.com.epicdroid.travel.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TextFormatUtils {

    public static String formatDateToField(long timeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeMillis);
        return (new SimpleDateFormat("dd MMM yyyy").format(c.getTime()).toUpperCase());
    }

    public static String showAsMoney(BigDecimal money){
        NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);
        usdCostFormat.setMinimumFractionDigits( 2 );
        usdCostFormat.setMaximumFractionDigits( 2 );
        return usdCostFormat.format(money.doubleValue());
    }

    public static String calculateRemainingDays(long date1, long date2) {
        long timeDifInMilliSec =  date1 - date2;
        long timeDifDays = timeDifInMilliSec / (24 * 60 * 60 * 1000);

        return String.valueOf(timeDifDays);
    }
}
