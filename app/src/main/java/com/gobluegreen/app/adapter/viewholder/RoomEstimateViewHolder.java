package com.gobluegreen.app.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gobluegreen.app.databinding.ItemRoomEstimateBinding;

/**
 * Created by David on 7/6/17.
 */

public class RoomEstimateViewHolder extends RecyclerView.ViewHolder {

    private ItemRoomEstimateBinding binding;



    public RoomEstimateViewHolder(View rowView) {
        super(rowView);
        binding = DataBindingUtil.bind(rowView);
    }

    public ItemRoomEstimateBinding getBinding() {
        return binding;
    }
}
