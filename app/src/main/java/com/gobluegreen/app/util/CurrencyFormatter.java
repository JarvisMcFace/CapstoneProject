package com.gobluegreen.app.util;

import java.text.DecimalFormat;

/**
 * Created by David on 7/25/17.
 */

public class CurrencyFormatter {

    public static String execute(double estimatedPrice) {

        DecimalFormat decimalFormat = new DecimalFormat(("$###,###.00"));
        String formatedCurrency = decimalFormat.format(estimatedPrice);
        if (formatedCurrency.contains("$.")) {
            formatedCurrency = formatedCurrency.replace("$.", "$0.");
        }

        return formatedCurrency;
    }
}
