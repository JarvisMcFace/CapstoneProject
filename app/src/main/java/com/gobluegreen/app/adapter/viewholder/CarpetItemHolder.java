package com.gobluegreen.app.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gobluegreen.app.databinding.ItemCarpetCleaningServiceBinding;

/**
 * Created by David on 7/6/17.
 */

public class CarpetItemHolder extends RecyclerView.ViewHolder {

    private ItemCarpetCleaningServiceBinding binding;

    public CarpetItemHolder(View rowView) {
        super(rowView);
        binding = DataBindingUtil.bind(rowView);
    }

    public ItemCarpetCleaningServiceBinding getBinding() {
        return binding;
    }
}
