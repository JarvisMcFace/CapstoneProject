package com.gobluegreen.app.util;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.EstimateInProgressTO;

/**
 * Created by David on 9/10/17.
 */

public class ReviewSubmitBindingAdapter {

    @BindingAdapter("estimate_client_type")
    public static void setClientType(TextView textView, EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();


        textView.setText(customerTO.getCustomerType().toString());
    }


    @BindingAdapter("estimate_customer_name")
    public static void setClientName(TextView textView, EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();

        String firstName = customerTO.getFirstName();
        String lastName = customerTO.getLastName();

        textView.setText(firstName + " " + lastName);
    }
}
