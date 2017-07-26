package com.gobluegreen.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.viewholder.RoomEstimateViewHolder;
import com.gobluegreen.app.adapter.viewholder.RoomStairwayEstimateViewHolder;
import com.gobluegreen.app.databinding.ItemRoomEstimateBinding;
import com.gobluegreen.app.databinding.ItemRoomStairwayEstimateBinding;
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
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
            case VIEW_TYPE_ROOM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_estimate, parent, false);
                return new RoomEstimateViewHolder(view);
            case VIEW_TYPE_STAIRWAY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_stairway_estimate, parent, false);
                return new RoomStairwayEstimateViewHolder(view);
            case VIEW_TYPE_UPHOLSTERY:
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        EstimateItemTO estimateItemTO = estimateItemTOs.get(position);

        if (estimateItemTO.getItemType() == EstimateItemTO.ItemType.ROOM) {
            bindCarpetCleaningService((RoomEstimateViewHolder) holder, estimateItemTO, position);
            return;
        }

        if (estimateItemTO.getItemType() == EstimateItemTO.ItemType.STAIRWAY) {
            bindStairwayCleaningService((RoomStairwayEstimateViewHolder) holder, estimateItemTO, position);
            return;
        }

    }

    @Override
    public int getItemCount() {
        return estimateItemTOs.size();
    }

    private void bindCarpetCleaningService(RoomEstimateViewHolder roomEstimateViewHolder, EstimateItemTO estimateItemTO, final int position) {

        final ItemRoomEstimateBinding binding = roomEstimateViewHolder.getBinding();

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
                updateEstimatedPrice(roomTO, binding.estimatedPrice);
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

                updateEstimatedPrice(roomTO, binding.estimatedPrice);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //intentionally left blank
            }
        });


        String estimatePrice = roomTO.getPriceEstimate();
        if (StringUtils.isNotEmpty(estimatePrice)) {
            binding.estimatedPrice.setText(String.valueOf(estimatePrice));
        }

        if (roomTO.isCarpetProtector()) {
            binding.estimateCarpetProtectorCheckbox.setChecked(true);
        }
        binding.estimateCarpetProtectorCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = binding.estimateCarpetProtectorCheckbox.isChecked();
                if (isChecked) {
                    roomTO.setCarpetProtector(true);
                } else {
                    roomTO.setCarpetProtector(false);
                }
            }
        });

        if (roomTO.isMoveFurniture()) {
            binding.estimateMoveFurnatureCheckbox.setChecked(true);
        }
        binding.estimateMoveFurnatureCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = binding.estimateMoveFurnatureCheckbox.isChecked();
                if (isChecked) {
                    roomTO.setMoveFurniture(true);

                } else {
                    roomTO.setMoveFurniture(false);
                }
            }
        });
    }


    private void bindStairwayCleaningService(RoomStairwayEstimateViewHolder roomStairwayEstimateViewHolder, EstimateItemTO estimateItemTO, final int position) {

        final ItemRoomStairwayEstimateBinding binding = roomStairwayEstimateViewHolder.getBinding();

        final RoomTO roomTO = (RoomTO) estimateItemTO.getItemObject();

        binding.carpetStairwayServiceLabel.setText(roomTO.getRoomType().getDescription());

        int numberSteps = roomTO.getNumberSteps();

        if (numberSteps > 0) {
            binding.estimateNumberOfSteps.setText(String.valueOf(numberSteps));
        }
        binding.estimateNumberOfSteps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int numberSteps = 0;

                if (StringUtils.isNotEmpty(s.toString())) {
                    numberSteps = Integer.parseInt(s.toString());
                }

                roomTO.setNumberSteps(numberSteps);
                updateNumberOfStepEstimatedPrice(roomTO, binding.estimatedPrice);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //intentionally left blank
            }
        });


        String estimatePrice = roomTO.getPriceEstimate();
        if (StringUtils.isNotEmpty(estimatePrice)) {
            binding.estimatedPrice.setText(String.valueOf(estimatePrice));
        }

        if (roomTO.isCarpetProtector()) {
            binding.estimateCarpetProtectorCheckbox.setChecked(true);
        }
        binding.estimateCarpetProtectorCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = binding.estimateCarpetProtectorCheckbox.isChecked();
                if (isChecked) {
                    roomTO.setCarpetProtector(true);
                } else {
                    roomTO.setCarpetProtector(false);
                }

                updateEstimatedPrice(roomTO, binding.estimatedPrice);
            }
        });


    }

    private void updateNumberOfStepEstimatedPrice(RoomTO roomTO, TextView estimatedPrice) {

        int numberOfSteps = roomTO.getNumberSteps();

        if (numberOfSteps > 0) {

            roomTO.setPriceEstimate("step price");
            estimatedPrice.setText("step price");
        } else {
            estimatedPrice.setText("");
        }
    }

    private void updateEstimatedPrice(RoomTO roomTO, TextView estimatedPrice) {

        int roomLength = roomTO.getLength();
        int roomWidth = roomTO.getWidth();

        if (roomLength > 0 && roomWidth > 0) {

            CarpetRoomServiceCallBack carpetRoomServiceCallBack = weakReferenceCarpetRoomServiceCallBack.get();
            String roomEstimatedSquareFeet = carpetRoomServiceCallBack.showEstimatedPrice(roomTO);
            estimatedPrice.setText(roomEstimatedSquareFeet);

        } else {
            estimatedPrice.setText("");
        }

    }

}
