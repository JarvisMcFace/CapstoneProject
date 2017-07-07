package com.gobluegreen.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.viewholder.CarpetItemHolder;
import com.gobluegreen.app.databinding.ItemCarpetCleaningServiceBinding;
import com.gobluegreen.app.to.RoomDescriptionItemTO;
import com.gobluegreen.app.to.RoomType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 7/6/17.
 */
public class CarpetItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
        CarpetItemHolder carpetItemHolder = new CarpetItemHolder(view);

        return carpetItemHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        CarpetItemHolder carpetItemHolder = (CarpetItemHolder) holder;
        ItemCarpetCleaningServiceBinding binding = carpetItemHolder.getBinding();

        String carpetService = roomDescriptionItemTOs.get(position).getDescription();
        binding.carpetCleaningServiceLabel.setText(carpetService);

        binding.carpetCleaningServiceCheckbox.setChecked(roomDescriptionItemTOs.get(position).isSelected());
        binding.carpetCleaningServiceCheckbox.setOnCheckedChangeListener(null);
        binding.carpetCleaningServiceCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RoomDescriptionItemTO roomDescriptionItemTO = roomDescriptionItemTOs.get(holder.getAdapterPosition());
                roomDescriptionItemTO.setSelected(isChecked);
                final String carpetDescription = roomDescriptionItemTO.getDescription();
                RoomType roomType = RoomType.lookupRoomType(carpetDescription);

                if (isChecked) {
                    if (!roomTypeList.contains(roomType)) {
                        roomTypeList.add(roomType);
                    }
                } else {
                    roomTypeList.remove(roomType);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return roomDescriptionItemTOs.size();
    }

    public List<RoomType> getRoomTypeList() {
        return roomTypeList;
    }
}
