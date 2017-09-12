package com.gobluegreen.app.util;

import android.content.res.Resources;

import com.gobluegreen.app.R;
import com.gobluegreen.app.to.CustomerTO;

/**
 * Created by David on 9/11/17.
 */
public class CreateCityStateZipDisplayLine {

    public static String execute(Resources resources, CustomerTO customerTO) {

        StringBuilder stringBuilder = new StringBuilder();
        String city = customerTO.getCity();
        if (StringUtils.isNotEmpty(city)) {
            stringBuilder.append(city);
        }

        String state = customerTO.getState();
        String selectedState = resources.getString(R.string.select_state);
        if (StringUtils.isNotEmpty(state) && !selectedState.equalsIgnoreCase(state)) {
            stringBuilder.append(" ").append(state);
        }

        String zip = customerTO.getZipCode();
        if (StringUtils.isNotEmpty(zip)) {
            stringBuilder.append(", ").append(zip);
        }

        return stringBuilder.toString();
    }
}
