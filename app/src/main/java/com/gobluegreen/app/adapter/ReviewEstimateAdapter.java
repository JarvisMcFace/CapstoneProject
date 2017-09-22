package com.gobluegreen.app.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.viewholder.ReviewEstimateItemHolder;
import com.gobluegreen.app.data.estimate.ReviewEstimateCursorHelper;
import com.gobluegreen.app.databinding.ItemReviewEstimateCardBinding;
import com.gobluegreen.app.to.ReviewEstimateTO;
import com.gobluegreen.app.util.StringUtils;

/**
 * Created by David on 9/17/17.
 */

public class ReviewEstimateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ESTIMATE_ID_COLUMN = 1;
    private final static String TAG = ReviewEstimateAdapter.class.getSimpleName();

    private Cursor cursor;

    public ReviewEstimateAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_estimate_card, parent, false);
        return new ReviewEstimateItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        cursor.moveToPosition(position);
        ReviewEstimateTO reviewEstimateTO = ReviewEstimateCursorHelper.retrieveReviewEstimateTO(cursor);

        ReviewEstimateItemHolder reviewEstimateItemHolder = (ReviewEstimateItemHolder) holder;
        ItemReviewEstimateCardBinding binding = reviewEstimateItemHolder.getBinding();
        binding.setReview(reviewEstimateTO);

        String priceRange = reviewEstimateTO.getPriceEstimatesRange();
        String sqFeet =reviewEstimateTO.getEstimatedSqFt();

        if (StringUtils.isNotEmpty(priceRange)) {
            binding.reviewPriceEstimateRange.setText(priceRange);
        }

        if (StringUtils.isNotEmpty(sqFeet)) {
            binding.reviewPriceEstimateSquareFeet.setText(sqFeet);
        }

        String numberRooms = reviewEstimateTO.getNumberOfRooms();
        if (StringUtils.isNotEmpty(numberRooms)) {
            binding.reviewPriceEstimateNumberRooms.setText(numberRooms);
        }

    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(ESTIMATE_ID_COLUMN);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
