package aahl.apiconsumerapp.ui.Locations.LogicaRecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.interfaces.LocationClickListener;
import aahl.apiconsumerapp.models.Location;

public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    private List<Location> locations;
    private LocationClickListener listener;

    public void setOnClickListener(LocationClickListener listener){
        this.listener = listener;
    }

    public LocationAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_localizacion, parent, false);
        return new LocationViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.tvNombreLocalizacion.setText(location.getName());
        holder.tvTipoLocalizacion.setText(location.getType());
        holder.tvDimensionLocalizacion.setText(location.getDimension());
    }

    public int getItemCount() {
        return locations.size();
    }
}
