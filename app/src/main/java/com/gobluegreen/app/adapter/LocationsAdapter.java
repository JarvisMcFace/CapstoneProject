package com.gobluegreen.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gobluegreen.app.R;
import com.gobluegreen.app.adapter.viewholder.LocationsViewHolder;
import com.gobluegreen.app.application.GoBluegreenApplication;
import com.gobluegreen.app.databinding.ItemLocationBinding;
import com.gobluegreen.app.to.LocationTO;

import java.util.List;

/**
 * Created by David on 7/6/17.
 */
public class LocationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private GoBluegreenApplication application;
    private List<LocationTO> locationTOs;

    public LocationsAdapter(GoBluegreenApplication application,  List<LocationTO> locationTOs) {
        this.application = application;
        this.locationTOs = locationTOs;
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

        LocationTO locationTO = locationTOs.get(position);
        binding.setLocation(locationTO);

    }

    @Override
    public int getItemCount() {
        return locationTOs.size();
    }
}
