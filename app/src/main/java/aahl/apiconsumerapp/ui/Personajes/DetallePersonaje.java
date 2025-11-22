package aahl.apiconsumerapp.ui.Personajes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.models.Character;

public class DetallePersonaje extends Fragment {

    private ImageView ivImagenPersonaje;
    private TextView tvNombrePersonaje;
    private TextView tvEstadoPersonaje;
    private TextView tvEspeciePersonaje;
    private TextView tvTipoPersonaje;
    private TextView tvGeneroPersonaje;
    private TextView tvOrigenPersonaje;
    private TextView tvUbicacionPersonaje;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.disp_detalle_personaje, container, false);

        ivImagenPersonaje = view.findViewById(R.id.ivImagenPersonaje);
        tvNombrePersonaje = view.findViewById(R.id.tvNombrePersonaje);
        tvEstadoPersonaje = view.findViewById(R.id.tvEstadoPersonaje);
        tvEspeciePersonaje = view.findViewById(R.id.tvEspeciePersonaje);
        tvTipoPersonaje = view.findViewById(R.id.tvTipoPersonaje);
        tvGeneroPersonaje = view.findViewById(R.id.tvGeneroPersonaje);
        tvOrigenPersonaje = view.findViewById(R.id.tvOrigenPersonaje);
        tvUbicacionPersonaje = view.findViewById(R.id.tvUbicacionPersonaje);

        // TODO: Obtener las URLs de los episodios donde aparece y cargar los episodios en el RecyclerView

        // Obtener el personaje del Bundle
        if (getArguments() != null) {
            Character character = getArguments().getSerializable("character", Character.class);
            if (character != null) {
                mostrarDatosPersonaje(character);
            }
        }

        return view;
    }

    private void mostrarDatosPersonaje(Character character) {
        tvNombrePersonaje.setText(character.getName());
        tvEstadoPersonaje.setText(character.getStatus());
        tvEspeciePersonaje.setText(character.getSpecies());
        tvTipoPersonaje.setText(character.getType().isEmpty() ? "N/A" : character.getType());
        tvGeneroPersonaje.setText(character.getGender());

        if (character.getOrigin() != null) {
            tvOrigenPersonaje.setText(character.getOrigin().getName());
            tvOrigenPersonaje.setOnClickListener(v -> {
               // TODO: Navegar a disp_detalles_location basado en una URL de origen
            });
        }

        if (character.getLocation() != null) {
            tvUbicacionPersonaje.setText(character.getLocation().getName());
            tvUbicacionPersonaje.setOnClickListener(v -> {
                // TODO: Navegar a disp_detalles_location basado en una URL de origen
            });
        }

        int radiusPx = (int) (9 * ivImagenPersonaje.getContext().getResources().getDisplayMetrics().density);

        // Cargar la imagen usando Glide
        if (getContext() != null) {
            Glide.with(getContext())
                    .load(character.getImage())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)
                    .centerCrop()
                    .transform(new RoundedCorners(radiusPx))
                    .into(ivImagenPersonaje);
        }
    }

}
