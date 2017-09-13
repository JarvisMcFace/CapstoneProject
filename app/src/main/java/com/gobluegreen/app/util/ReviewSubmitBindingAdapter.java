package com.gobluegreen.app.util;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.gobluegreen.app.R;
import com.gobluegreen.app.to.CustomerTO;
import com.gobluegreen.app.to.CustomerType;
import com.gobluegreen.app.to.EstimateInProgressTO;
import com.gobluegreen.app.to.RoomType;
import com.gobluegreen.app.to.UpholsteryType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @BindingAdapter("estimate_room_to_clean_title")
    public static void setRoomToCLeanTitle(TextView textView, EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        CustomerType customerType = customerTO.getCustomerType();

        if (CustomerType.COMMERCIAL == customerType) {
            textView.setVisibility(View.GONE);
            return;
        }

        textView.setVisibility(View.VISIBLE);
    }

    @BindingAdapter("estimate_room_to_clean_gride_view")
    public static void showRoomToClean(GridView gridView, EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        CustomerType customerType = customerTO.getCustomerType();

        if (CustomerType.COMMERCIAL == customerType) {
            gridView.setVisibility(View.GONE);
            return;
        }

        List<RoomType> roomTypes = estimateInProgressTO.getRoomTypes();
        List<String> roomServiceList = new ArrayList<>();
        final String DOT = "\u00b7";
        for (RoomType roomType : roomTypes) {
            roomServiceList.add(DOT + " " + roomType.getDescription());
        }

        final ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>(gridView.getContext(), R.layout.layout_selected_carpet_servcies_item, roomServiceList);
        gridView.setAdapter(gridViewArrayAdapter);
        gridViewArrayAdapter.notifyDataSetChanged();

    }


    @BindingAdapter("estimate_upholstery_to_clean_title")
    public static void setUpholsteryToCleanTitle(TextView textView, EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        CustomerType customerType = customerTO.getCustomerType();

        if (CustomerType.COMMERCIAL == customerType) {
            textView.setVisibility(View.GONE);
            return;
        }

        textView.setVisibility(View.VISIBLE);
    }


    @BindingAdapter("estimate_upholstery_gride_view")
    public static void setUpholsteryToClean(GridView gridView, EstimateInProgressTO estimateInProgressTO) {

        CustomerTO customerTO = estimateInProgressTO.getCustomerTO();
        CustomerType customerType = customerTO.getCustomerType();

        if (CustomerType.COMMERCIAL == customerType) {
            gridView.setVisibility(View.GONE);
            return;
        }

        gridView.setVisibility(View.VISIBLE);

        final String DOT = "\u00b7";
        Set<UpholsteryType> upholsteryTypeSet = estimateInProgressTO.getUpholsterySet();
        Resources resources = gridView.getResources();
        List<String> upholsteryList = new ArrayList<>();
        for (UpholsteryType upholsteryType : upholsteryTypeSet) {
            switch (upholsteryType) {

                case SOFA:
                    upholsteryList.add(DOT + " " + resources.getString(R.string.sofa));
                    break;
                case CHAIR:
                    upholsteryList.add(DOT + " " + resources.getString(R.string.chair));
                    break;
            }
        }

        final ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>(gridView.getContext(), R.layout.layout_selected_carpet_servcies_item, upholsteryList);
        gridView.setAdapter(gridViewArrayAdapter);
        gridViewArrayAdapter.notifyDataSetChanged();
    }
}
