package com.gobluegreen.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.viewholder.LocationsViewHolder;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.ItemLocationBinding;
import com.gobluegreen.app.fragment.LocationsCallBack;
import com.gobluegreen.app.to.LocationTO;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by David on 7/6/17.
 */
public class LocationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LocationTO> locationTOs;
    private WeakReference<LocationsCallBack> weakReferenceCarpetRoomServiceCallBack;

    public LocationsAdapter(GoBluegreenApplication application, List<LocationTO> locationTOs, WeakReference<LocationsCallBack> weakReferenceCarpetRoomServiceCallBack) {
        GoBluegreenApplication application1 = application;
        this.locationTOs = locationTOs;
        this.weakReferenceCarpetRoomServiceCallBack = weakReferenceCarpetRoomServiceCallBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        LocationsViewHolder locationsViewHolder = (LocationsViewHolder) holder;
        final ItemLocationBinding binding = locationsViewHolder.getBinding();

        final LocationTO locationTO = locationTOs.get(position);
        binding.setLocation(locationTO);

        binding.locationItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationsCallBack locationsCallBack = weakReferenceCarpetRoomServiceCallBack.get();
                locationsCallBack.moveToLocation(locationTO);
            }
        });

        binding.googleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationsCallBack locationsCallBack = weakReferenceCarpetRoomServiceCallBack.get();
                locationsCallBack.startGoogleMaps(locationTO);
            }
        });

    }

    @Override
    public int getItemCount() {
        return locationTOs.size();
    }
}
