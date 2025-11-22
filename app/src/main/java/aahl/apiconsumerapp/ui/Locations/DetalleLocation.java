package aahl.apiconsumerapp.ui.Locations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.models.Location;

public class DetalleLocation extends Fragment {

    private TextView tvNombreUbicacion;
    private TextView tvTipoUbicacion;
    private TextView tvDimensionUbicacion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.disp_detalle_location, container, false);

        tvNombreUbicacion = view.findViewById(R.id.tvNombreUbicacion);
        tvTipoUbicacion = view.findViewById(R.id.tvTipoUbicacion);
        tvDimensionUbicacion = view.findViewById(R.id.tvDimensionUbicacion);

        // TODO: Obtener las URLs de los personajes que habitan la localización y cargarlos en el RecyclerView

        // Obtener el episodio del Bundle
        if (getArguments() != null) {
            Location location = getArguments().getSerializable("location", Location.class);
            if (location != null) {
                mostrarDatosLocation(location);
            }
        }

        return view;
    }

    private void mostrarDatosLocation(Location location) {
        tvNombreUbicacion.setText(location.getName());
        tvTipoUbicacion.setText(location.getType());
        tvDimensionUbicacion.setText(location.getDimension());
    }

}
