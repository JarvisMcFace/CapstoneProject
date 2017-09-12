package com.gobluegreen.app.util;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

import com.gobluegreen.app.R;
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.CustomerType;
import com.gobluegreen.app.to.EstimateInProgressTO;

/**
 * Created by David on 9/10/17.
 */

public class ReviewSubmitBindingAdapter {

    @BindingAdapter("estimate_client_type")
    public static void setClientType(TextView textView, EstimateInProgressTO estimateInProgressTO) {

        Resources resources = textView.getResources();
        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        CustomerType customerType = customerTO.getCustomerType();

        switch (customerType) {

            case COMMERCIAL:
                textView.setText(resources.getText(R.string.commercial));
                return;

            case RESIDENTIAL:
                textView.setText(resources.getText(R.string.residential));
                return;
        }
    }

    @BindingAdapter("estimate_customer_name")
    public static void setClientName(TextView textView, EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();

        String firstName = customerTO.getFirstName();
        String lastName = customerTO.getLastName();

        textView.setText(firstName + " " + lastName);
    }

    @BindingAdapter("estimate_customer_address_line1")
    public static void setAddressLine1(TextView textView, EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        String addessLine1 = customerTO.getAddress1();
        textView.setText(addessLine1);
    }

    @BindingAdapter("estimate_customer_address_line2")
    public static void setAddressLine2(TextView textView, EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        String addessLine2 = customerTO.getAddress2();
        textView.setText(addessLine2);
    }

    @BindingAdapter("estimate_city_state_postal_code")
    public static void setCityStateZip(TextView textView, EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();

        Resources resources = textView.getResources();
        String cityStateZip = CreateCityStateZipDisplayLine.execute(resources, customerTO);

        if (StringUtils.isEmpty(cityStateZip)) {
            textView.setVisibility(View.GONE);
            return;
        }

        textView.setText(cityStateZip);
    }


}
