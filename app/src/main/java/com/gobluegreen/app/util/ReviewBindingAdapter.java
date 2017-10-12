package com.gobluegreen.app.util;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gobluegreen.app.R;
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.CustomerType;
import com.gobluegreen.app.to.ReviewEstimateTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by David on 9/10/17.
 */

public class ReviewBindingAdapter {

    @BindingAdapter("review_client_type")
    public static void setClientType(TextView textView, ReviewEstimateTO reviewEstimateTO) {

        Resources resources = textView.getResources();
        CustomerTO customerTO = reviewEstimateTO.getCustomerTO();
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

    @BindingAdapter("review_customer_name")
    public static void setClientName(TextView textView, ReviewEstimateTO reviewEstimateTO) {

        CustomerTO customerTO = reviewEstimateTO.getCustomerTO();

        String firstName = customerTO.getFirstName();
        String lastName = customerTO.getLastName();

        textView.setText(firstName + " " + lastName);
    }

    @BindingAdapter("review_customer_address_line1")
    public static void setAddressLine1(TextView textView, ReviewEstimateTO reviewEstimateTO) {

        CustomerTO customerTO = reviewEstimateTO.getCustomerTO();
        String addessLine1 = customerTO.getAddress1();
        textView.setText(addessLine1);
    }

    @BindingAdapter("review_customer_address_line2")
    public static void setAddressLine2(TextView textView, ReviewEstimateTO reviewEstimateTO) {

        CustomerTO customerTO = reviewEstimateTO.getCustomerTO();
        String addessLine2 = customerTO.getAddress2();
        textView.setText(addessLine2);
    }

    @BindingAdapter("review_city_state_postal_code")
    public static void setCityStateZip(TextView textView, ReviewEstimateTO reviewEstimateTO) {

        CustomerTO customerTO = reviewEstimateTO.getCustomerTO();

        Resources resources = textView.getResources();
        String cityStateZip = CreateCityStateZipDisplayLine.execute(resources, customerTO);

        if (StringUtils.isEmpty(cityStateZip)) {
            textView.setVisibility(View.GONE);
            return;
        }

        textView.setText(cityStateZip);
    }

    @BindingAdapter("review_phone_number")
    public static void setPhoneNumber(TextView textView, ReviewEstimateTO reviewEstimateTO) {

        CustomerTO customerTO = reviewEstimateTO.getCustomerTO();

        String phoneNumber = customerTO.getPhoneNumber();

        if (StringUtils.isEmpty(phoneNumber)) {
            textView.setVisibility(View.GONE);
            return;
        }

        textView.setText(phoneNumber);
    }

    @BindingAdapter("review_estimate_date")
    public static void setEstimateDate(TextView textView, ReviewEstimateTO reviewEstimateTO) {


        Calendar calendar = reviewEstimateTO.getEstimateDate();

        String pattern = "MMM dd, yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date = new Date(calendar.getTimeInMillis());

        String formattedDate = simpleDateFormat.format(date);
        textView.setText(formattedDate);
    }

    @BindingAdapter("review_commercial_estimate")
    public static void hideCommercialEstimates(LinearLayout linearLayout, ReviewEstimateTO reviewEstimateTO) {

        CustomerTO customerTO = reviewEstimateTO.getCustomerTO();
        CustomerType customerType = customerTO.getCustomerType();

        if (CustomerType.COMMERCIAL == customerType) {
            linearLayout.setVisibility(View.GONE);
            return;
        }
    }


}
