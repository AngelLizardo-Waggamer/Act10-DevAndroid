package aahl.apiconsumerapp.ui.Episodios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.models.Episode;

public class DetalleEpisodio extends Fragment {

    private TextView tvNombreEpisodio;
    private TextView tvCodigoEpisodio;
    private TextView tvFechaEmisionEpisodio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.disp_detalle_episodio, container, false);

        tvNombreEpisodio = view.findViewById(R.id.tvNombreEpisodio);
        tvCodigoEpisodio = view.findViewById(R.id.tvCodigoEpisodio);
        tvFechaEmisionEpisodio = view.findViewById(R.id.tvFechaEmisionEpisodio);

        // TODO: Obtener las URLs de los personajes que aparecen en el episodio y cargarlos en el RecyclerView

        // Obtener el episodio del Bundle
        if (getArguments() != null) {
            Episode episode = getArguments().getSerializable("episode", Episode.class);
            if (episode != null) {
                mostrarDatosEpisodio(episode);
            }
        }

        return view;
    }

    private void mostrarDatosEpisodio(Episode episode){
        tvNombreEpisodio.setText(episode.getName());
        tvCodigoEpisodio.setText(episode.getEpisode());
        tvFechaEmisionEpisodio.setText(episode.getAir_date());
    }

}
