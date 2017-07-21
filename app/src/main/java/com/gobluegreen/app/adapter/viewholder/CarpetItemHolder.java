package com.gobluegreen.app.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gobluegreen.app.databinding.ItemCarpetCleaningServiceBinding;
import com.gobluegreen.app.to.RoomDescriptionItemTO;
import com.gobluegreen.app.to.RoomType;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by David on 7/6/17.
 */

public class CarpetItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ItemCarpetCleaningServiceBinding binding;
    private final List<RoomDescriptionItemTO> roomDescriptionItemTOs;
    private final WeakReference<CleaningServiceSelectedRoomCallback> roomCallbackWeakReference;

    public CarpetItemHolder(View rowView, List<RoomDescriptionItemTO> roomDescriptionItemTOs, WeakReference<CleaningServiceSelectedRoomCallback> roomCallbackWeakReference) {
        super(rowView);
        this.roomDescriptionItemTOs = roomDescriptionItemTOs;
        this.roomCallbackWeakReference = roomCallbackWeakReference;
        binding = DataBindingUtil.bind(rowView);

        rowView.setOnClickListener(this);
    }

    public ItemCarpetCleaningServiceBinding getBinding() {
        return binding;
    }

    @Override
    public void onClick(View v) {

        RoomDescriptionItemTO roomDescriptionItemTO = roomDescriptionItemTOs.get(getAdapterPosition());

        boolean isSelected = roomDescriptionItemTO.isSelected();
        if (isSelected) {
            roomDescriptionItemTO.setSelected(false);
            binding.carpetCleaningServiceCheckbox.setChecked(false);
        } else {
            roomDescriptionItemTO.setSelected(true);
            binding.carpetCleaningServiceCheckbox.setChecked(true);
        }

        final String carpetDescription = roomDescriptionItemTO.getDescription();
        RoomType roomType = RoomType.lookupRoomType(carpetDescription);

        CleaningServiceSelectedRoomCallback cleaningServiceSelectedRoomCallback = roomCallbackWeakReference.get();
        cleaningServiceSelectedRoomCallback.onRoomSelected(roomDescriptionItemTO.isSelected(), roomType);

    }
}
