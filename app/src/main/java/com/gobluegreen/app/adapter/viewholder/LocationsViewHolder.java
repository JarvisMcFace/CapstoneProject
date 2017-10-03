package com.gobluegreen.app.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gobluegreen.app.databinding.ItemLocationBinding;

/**
 * Created by David on 7/6/17.
 */

public class LocationsViewHolder extends RecyclerView.ViewHolder {

    private final ItemLocationBinding binding;

    public LocationsViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);

    }

    public ItemLocationBinding getBinding() {
        return binding;
    }


}
