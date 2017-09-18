package com.gobluegreen.app.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gobluegreen.app.databinding.ItemReviewEstimateCardBinding;

/**
 * Created by David on 7/6/17.
 */

public class ReviewEstimateItemHolder extends RecyclerView.ViewHolder  {

    private final ItemReviewEstimateCardBinding binding;

    public ReviewEstimateItemHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);

    }

    public ItemReviewEstimateCardBinding getBinding() {
        return binding;
    }


}
