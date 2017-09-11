package com.gobluegreen.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.viewholder.RoomEstimateViewHolder;
import com.gobluegreen.app.adapter.viewholder.RoomStairwayEstimateViewHolder;
import com.gobluegreen.app.databinding.ItemRoomEstimateBinding;
import com.gobluegreen.app.databinding.ItemRoomStairwayEstimateBinding;
import com.gobluegreen.app.to.EstimateItemTO;
import com.gobluegreen.app.to.RoomTO;
import com.gobluegreen.app.util.CurrencyFormatter;
import com.gobluegreen.app.util.StringUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by David on 7/15/17.
 */

public class EstimateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private enum MeasureBy {SQUARE_FOOT, DEMENSION}

    ;

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_ROOM = 2;
    public static final int VIEW_TYPE_STAIRWAY = 3;
    public static final int VIEW_TYPE_UPHOLSTERY = 4;


    private List<EstimateItemTO> estimateItemTOs;
    private WeakReference<CarpetRoomServiceCallBack> weakReferenceCarpetRoomServiceCallBack;
    private MeasureBy measureBy = MeasureBy.DEMENSION;

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

        if (roomTO.isDimensionByLengthWidth()) {
            measureBy = MeasureBy.DEMENSION;
            binding.buttonBySquareFeet.setVisibility(View.VISIBLE);
            binding.containerSquareFeet.setVisibility(View.GONE);
            binding.buttonByLengthWidth.setVisibility(View.GONE);
            binding.containerLengthWidth.setVisibility(View.VISIBLE);
        } else {
            measureBy = MeasureBy.SQUARE_FOOT;
            binding.buttonByLengthWidth.setVisibility(View.VISIBLE);
            binding.containerLengthWidth.setVisibility(View.GONE);
            binding.buttonBySquareFeet.setVisibility(View.GONE);
            binding.containerSquareFeet.setVisibility(View.VISIBLE);

        }

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
                updateHeaderEstimate(roomTO);
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
                roomTO.setDimensionByLengthWidth(true);
                updateHeaderEstimate(roomTO);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //intentionally left blank
            }
        });

        int roomSquareFeet = roomTO.getSquareFeet();
        if (roomSquareFeet > 0) {
            binding.estimateBySquareFeet.setText(String.valueOf(roomSquareFeet));

        }
        binding.estimateBySquareFeet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int squareFeet = 0;

                if (StringUtils.isNotEmpty(s.toString())) {
                    squareFeet = Integer.parseInt(s.toString());
                }

                roomTO.setSquareFeet(squareFeet);
                updateHeaderEstimate(roomTO);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //intentionally left blank
            }
        });

        if (roomTO.isCarpetProtector()) {
            binding.estimateCarpetProtectorCheckbox.setChecked(true);
        }
        binding.checkboxContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = binding.estimateCarpetProtectorCheckbox.isChecked();
                if (isChecked) {
                    roomTO.setCarpetProtector(false);
                    binding.estimateCarpetProtectorCheckbox.setChecked(false);
                } else {
                    roomTO.setCarpetProtector(true);
                    binding.estimateCarpetProtectorCheckbox.setChecked(true);
                }
                updateHeaderEstimate(roomTO);
            }
        });


        binding.byLenghtWidth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                roomTO.setSquareFeet(0);
                measureBy = MeasureBy.DEMENSION;
                flipMeasureBy(binding);
                binding.estimateBySquareFeet.setText(null);
                animateView(binding.containerSquareFeet, binding.containerLengthWidth);
                updateHeaderEstimate(roomTO);
            }
        });

        binding.bySquareFeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                measureBy = MeasureBy.SQUARE_FOOT;
                int squareFeet = roomTO.getSquareFeet();
                flipMeasureBy(binding);
                animateView(binding.containerLengthWidth, binding.containerSquareFeet);

                roomTO.setLength(0);
                roomTO.setWidth(0);
                binding.estimateRoomLength.setText(null);
                binding.estimateRoomWidth.setText(null);
                roomTO.setDimensionByLengthWidth(false);

                if (squareFeet > 0) {
                    binding.estimateBySquareFeet.setText(String.valueOf(squareFeet));
                }

                updateHeaderEstimate(roomTO);
            }
        });


        updateHeaderEstimate(roomTO);
    }

    private void flipMeasureBy(ItemRoomEstimateBinding binding) {

        if (measureBy == MeasureBy.DEMENSION) {
            binding.buttonByLengthWidth.setVisibility(View.GONE);
            binding.buttonBySquareFeet.setVisibility(View.VISIBLE);
        } else {
            binding.buttonByLengthWidth.setVisibility(View.VISIBLE);
            binding.buttonBySquareFeet.setVisibility(View.GONE);
        }
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


        Double estimatePrice = roomTO.getPriceEstimate();
        if (!Double.isNaN(estimatePrice) && estimatePrice > 0) {
            String formattedPrice = CurrencyFormatter.execute(estimatePrice);
            binding.estimatedPrice.setText(formattedPrice);
        }

    }

`    private void updateNumberOfStepEstimatedPrice(RoomTO roomTO, TextView estimatedPrice) {

        int numberOfSteps = roomTO.getNumberSteps();

        if (numberOfSteps > 0) {
            // TODO: 7/25/17 price per stemp?
            estimatedPrice.setText("TODO step price");
        } else {
            estimatedPrice.setText("");
        }

        updateHeaderEstimate(roomTO);
    }

    private void updateHeaderEstimate(RoomTO roomTO) {

        CarpetRoomServiceCallBack carpetRoomServiceCallBack = weakReferenceCarpetRoomServiceCallBack.get();
        carpetRoomServiceCallBack.updateEstimateHeader(roomTO);
    }


    private void animateView(final View chooseADimension, final View showView) {

        Animation animation = AnimationUtils.loadAnimation(chooseADimension.getContext(), R.anim.slide_out_to_left);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showDimensionView(showView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        chooseADimension.setAnimation(animation);
        chooseADimension.setVisibility(View.INVISIBLE);
    }

    private void showDimensionView(View showView) {
        Animation animation = AnimationUtils.loadAnimation(showView.getContext(), R.anim.slide_in_from_right);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        showView.setAnimation(animation);
        showView.setVisibility(View.VISIBLE);
    }

}




