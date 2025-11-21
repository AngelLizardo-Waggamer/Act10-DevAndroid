package aahl.apiconsumerapp.ui.Locations.LogicaRecyclerViews;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.interfaces.LocationClickListener;

public class LocationViewHolder extends RecyclerView.ViewHolder {

    public TextView tvNombreLocalizacion;
    public TextView tvTipoLocalizacion;
    public TextView tvDimensionLocalizacion;
    public Button btnDetallesLocalizacion;

    public LocationViewHolder(@NonNull View itemView, final LocationClickListener listener) {
        super(itemView);
        tvNombreLocalizacion = itemView.findViewById(R.id.tvNombre_item_localizacion);
        tvTipoLocalizacion = itemView.findViewById(R.id.tvTipo_item_localizacion);
        tvDimensionLocalizacion = itemView.findViewById(R.id.tvDimension_item_localizacion);
        btnDetallesLocalizacion = itemView.findViewById(R.id.btnDetalles_item_localizacion);

        btnDetallesLocalizacion.setOnClickListener(v -> {
            if (listener != null) {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onLocationClicked(position);
                }
            }
        });
    }

}

