package com.gobluegreen.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.viewholder.CarpetItemHolder;
import com.gobluegreen.app.adapter.viewholder.CleaningServiceSelectedRoomCallback;
import com.gobluegreen.app.databinding.ItemCarpetCleaningServiceBinding;
import com.gobluegreen.app.to.RoomDescriptionItemTO;
import com.gobluegreen.app.to.RoomType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 7/6/17.
 */
public class CarpetItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CleaningServiceSelectedRoomCallback {

    private List<RoomDescriptionItemTO> roomDescriptionItemTOs;
    private List<RoomType> roomTypeList;

    public CarpetItemAdapter(List<RoomDescriptionItemTO> roomDescriptionItemTOs) {
        this.roomDescriptionItemTOs = roomDescriptionItemTOs;
        roomTypeList = new ArrayList<>();
        for (RoomDescriptionItemTO roomDescriptionItemTO : roomDescriptionItemTOs) {
            if (roomDescriptionItemTO.isSelected()) {
                roomTypeList.add(RoomType.lookupRoomType(roomDescriptionItemTO.getDescription()));
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carpet_cleaning_service, parent, false);
        WeakReference<CleaningServiceSelectedRoomCallback> carpetRoomServiceCallBackWeakReference = new WeakReference<CleaningServiceSelectedRoomCallback>(this);
        CarpetItemHolder carpetItemHolder = new CarpetItemHolder(view, roomDescriptionItemTOs, carpetRoomServiceCallBackWeakReference);
        return carpetItemHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        CarpetItemHolder carpetItemHolder = (CarpetItemHolder) holder;
        final ItemCarpetCleaningServiceBinding binding = carpetItemHolder.getBinding();

        RoomDescriptionItemTO roomDescriptionItemTO =  roomDescriptionItemTOs.get(position);
        String carpetService = roomDescriptionItemTO.getDescription();
        binding.carpetCleaningServiceLabel.setText(carpetService);

        boolean isSelected = roomDescriptionItemTO.isSelected();
        if (isSelected) {
            binding.carpetCleaningServiceCheckbox.setChecked(true);
        } else {
            binding.carpetCleaningServiceCheckbox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return roomDescriptionItemTOs.size();
    }

    public List<RoomType> getRoomTypeList() {
        return roomTypeList;
    }

    @Override
    public void onRoomSelected(boolean isChecked, RoomType roomType) {

        if (isChecked) {
            if (!roomTypeList.contains(roomType)) {
                roomTypeList.add(roomType);
            }
        } else {
            roomTypeList.remove(roomType);
        }
    }
}
