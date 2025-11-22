package aahl.apiconsumerapp.ui.Personajes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.api.ApiService;
import aahl.apiconsumerapp.api.RetrofitClient;
import aahl.apiconsumerapp.api.urlIDStriper;
import aahl.apiconsumerapp.models.Character;
import aahl.apiconsumerapp.models.Episode;
import aahl.apiconsumerapp.models.Location;
import aahl.apiconsumerapp.ui.Episodios.LogicaRecyclerViews.EpisodeAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetallePersonaje extends Fragment {

    private ImageView ivImagenPersonaje;
    private TextView tvNombrePersonaje;
    private TextView tvEstadoPersonaje;
    private TextView tvEspeciePersonaje;
    private TextView tvTipoPersonaje;
    private TextView tvGeneroPersonaje;
    private TextView tvOrigenPersonaje;
    private TextView tvUbicacionPersonaje;
    private RecyclerView recyclerView;
    private EpisodeAdapter adapter;
    private ApiService apiService;
    private List<Episode> episodesList = new ArrayList<>();


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
        recyclerView = view.findViewById(R.id.rvEpisodiosPersonaje);

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
                obtenerLocationYNavegar(character.getOrigin().getUrl(), v);
            });
        }

        if (character.getLocation() != null) {
            tvUbicacionPersonaje.setText(character.getLocation().getName());
            tvUbicacionPersonaje.setOnClickListener(v -> {
                obtenerLocationYNavegar(character.getLocation().getUrl(), v);
            });
        }

        int radiusPx = (int) (9 * ivImagenPersonaje.getContext().getResources().getDisplayMetrics().density);

        // Cargar la imagen usando Glide
        Glide.with(ivImagenPersonaje.getContext())
                .load(character.getImage())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .centerCrop()
                .transform(new RoundedCorners(radiusPx))
                .into(ivImagenPersonaje);

        if (character.getEpisode() != null) {
            obtenerEpisodios(character.getEpisode());
            configurarRecyclerViewEpisodios();
        }
    }

    private void configurarRecyclerViewEpisodios(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EpisodeAdapter(episodesList);

        adapter.setOnClickListener(position -> {
            // Crear el Bundle con el episodio
            Bundle bundle = new Bundle();
            bundle.putSerializable("episode", episodesList.get(position));

            // Navegar al fragmento de detalle
            Navigation.findNavController(recyclerView)
                    .navigate(R.id.action_detallePersonaje_to_detalleEpisodio, bundle);
        });

        recyclerView.setAdapter(adapter);
    }

    private void obtenerEpisodios(String[] episodesURLs) {

        Retrofit retrofitClient = RetrofitClient.getRetrofitInstance();
        apiService = retrofitClient.create(ApiService.class);
        StringBuilder episodesIds = new StringBuilder();

        for (String epURL : episodesURLs) {
            episodesIds
                    .append(urlIDStriper.getIDFromURL(epURL))
                    .append(",");
        }

        // Por default siempre queda una coma al final que está mal, se tiene que eliminar
        episodesIds.deleteCharAt(episodesIds.length() - 1);

        String ids = episodesIds.toString();

        // Si es un solo ID, la API devuelve un objeto, si son múltiples devuelve un array
        if (ids.contains(",")) {
            // Múltiples episodios - devuelve array
            Call<List<Episode>> call = apiService.getEpisodesByIds(ids);
            call.enqueue(new Callback<List<Episode>>() {
                @Override
                public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        episodesList.clear();
                        episodesList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Episode>> call, Throwable t) {
                    Log.e("DetallePersonaje", "Error al obtener episodios: " + t.getMessage());
                    Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Un solo episodio - devuelve objeto
            int episodeId = Integer.parseInt(ids);
            Call<Episode> call = apiService.getEpisodeDetails(episodeId);
            call.enqueue(new Callback<Episode>() {
                @Override
                public void onResponse(Call<Episode> call, Response<Episode> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        episodesList.clear();
                        episodesList.add(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Episode> call, Throwable t) {
                    Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void obtenerLocationYNavegar(String locationURL, View view) {

        if (locationURL.isEmpty()) {
            Toast.makeText(getContext(), "No se pudo obtener la localización", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofitClient = RetrofitClient.getRetrofitInstance();
        apiService = retrofitClient.create(ApiService.class);

        int Id = Integer.parseInt(urlIDStriper.getIDFromURL(locationURL));

        Call<Location> call = apiService.getLocationDetails(Id);
        call.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Location location = response.body();

                    // Crear el Bundle con la localización
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("location", location);

                    // Navegar al fragmento de detalle de localización
                    Navigation.findNavController(view)
                            .navigate(R.id.action_detallePersonaje_to_detalleLocation, bundle);
                } else {
                    Toast.makeText(getContext(), "No se pudo obtener la localización", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
