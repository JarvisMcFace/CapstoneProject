package com.gobluegreen.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.viewholder.RoomEstimateViewHolder;
import com.gobluegreen.app.databinding.ItemRoomEstimateBinding;
import com.gobluegreen.app.to.EstimateItemTO;
import com.gobluegreen.app.to.RoomTO;
import com.gobluegreen.app.util.StringUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by David on 7/15/17.
 */

public class EstimateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_ROOM = 2;
    public static final int VIEW_TYPE_STAIRWAY = 3;
    public static final int VIEW_TYPE_UPHOLSTERY = 4;


    private List<EstimateItemTO> estimateItemTOs;
    private WeakReference<CarpetRoomServiceCallBack> weakReferenceCarpetRoomServiceCallBack;

    public EstimateAdapter(List<EstimateItemTO> estimateItemTOs, WeakReference<CarpetRoomServiceCallBack> weakReferenceCarpetRoomServiceCallBack) {
        this.estimateItemTOs = estimateItemTOs;
        this.weakReferenceCarpetRoomServiceCallBack = weakReferenceCarpetRoomServiceCallBack;
    }

    @Override
    public int getItemViewType(int position) {

        EstimateItemTO estimateItemTO = estimateItemTOs.get(position);

        switch (estimateItemTO.getItemType()) {
            case HEADER:
                return VIEW_TYPE_HEADER;
            case ROOM:
                return VIEW_TYPE_ROOM;
            case STAIRWAY:
                return VIEW_TYPE_STAIRWAY;
            case UPHOLSTERY:
                return VIEW_TYPE_UPHOLSTERY;
            default:
                return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_HEADER:
            case VIEW_TYPE_ROOM:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_estimate, parent, false);
                return new RoomEstimateViewHolder(view);
            case VIEW_TYPE_STAIRWAY:
            case VIEW_TYPE_UPHOLSTERY:
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        EstimateItemTO estimateItemTO = estimateItemTOs.get(position);

        if (estimateItemTO.getItemType() == EstimateItemTO.ItemType.ROOM) {
            bindCarpetCleaningService((RoomEstimateViewHolder) holder, estimateItemTO);
        }

    }

    @Override
    public int getItemCount() {
        return estimateItemTOs.size();
    }

    private void bindCarpetCleaningService(RoomEstimateViewHolder roomEstimateViewHolder, EstimateItemTO estimateItemTO) {

        ItemRoomEstimateBinding binding = roomEstimateViewHolder.getBinding();

        final RoomTO roomTO = (RoomTO) estimateItemTO.getItemObject();

        binding.carpetServiceLabel.setText(roomTO.getRoomType().getDescription());


        int roomLength = roomTO.getLength();
        if (roomLength > 0) {
            binding.estimateRoomLength.setText(String.valueOf(roomLength));
        }
        binding.estimateRoomLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int length = 0;

                if (StringUtils.isNotEmpty(s.toString())) {
                    length = Integer.parseInt(s.toString());
                }

                roomTO.setLength(length);
                CarpetRoomServiceCallBack carpetRoomServiceCallBack = weakReferenceCarpetRoomServiceCallBack.get();
                carpetRoomServiceCallBack.updateRoomLength(roomTO);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //intentionally left blank
            }
        });

        int roomWidth = roomTO.getWidth();
        if (roomWidth > 0) {
            binding.estimateRoomWidth.setText(String.valueOf(roomWidth));
        }
        binding.estimateRoomWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int width = 0;

                if (StringUtils.isNotEmpty(s.toString())) {
                    width = Integer.parseInt(s.toString());
                }

                roomTO.setWidth(width);
                CarpetRoomServiceCallBack carpetRoomServiceCallBack = weakReferenceCarpetRoomServiceCallBack.get();
                carpetRoomServiceCallBack.updateRoomWidth(roomTO);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //intentionally left blank
            }
        });

        double estimatePrice = roomTO.getPriceEstimate();
        if (!Double.isNaN(estimatePrice) && estimatePrice > 0){
            binding.estimatedPrice.setText(String.valueOf(estimatePrice));
        }
    }

}
