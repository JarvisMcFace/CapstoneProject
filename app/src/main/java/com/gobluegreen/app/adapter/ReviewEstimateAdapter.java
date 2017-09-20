package com.gobluegreen.app.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.viewholder.ReviewEstimateItemHolder;
import com.gobluegreen.app.data.estimate.ReviewEstimateCursorHelper;
import com.gobluegreen.app.databinding.ItemReviewEstimateCardBinding;
import com.gobluegreen.app.to.ReviewEstimateTO;

/**
 * Created by David on 9/17/17.
 */

public class ReviewEstimateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
        ReviewEstimateTO reviewEstimateTO = ReviewEstimateCursorHelper.retrieveReviewEstimateTO(cursor, position);

        ReviewEstimateItemHolder reviewEstimateItemHolder = (ReviewEstimateItemHolder) holder;
        ItemReviewEstimateCardBinding binding = reviewEstimateItemHolder.getBinding();
        binding.setReview(reviewEstimateTO);

        Log.d(TAG, "David: " + "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(1); //TODO David
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
