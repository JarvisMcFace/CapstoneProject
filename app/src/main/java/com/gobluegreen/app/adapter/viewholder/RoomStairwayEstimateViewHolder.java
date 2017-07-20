package com.gobluegreen.app.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gobluegreen.app.databinding.ItemRoomStairwayEstimateBinding;

/**
 * Created by David on 7/6/17.
 */

public class RoomStairwayEstimateViewHolder extends RecyclerView.ViewHolder {

    private ItemRoomStairwayEstimateBinding binding;

    public RoomStairwayEstimateViewHolder(View rowView) {
        super(rowView);
        binding = DataBindingUtil.bind(rowView);
    }

    public ItemRoomStairwayEstimateBinding getBinding() {
        return binding;
    }
}
