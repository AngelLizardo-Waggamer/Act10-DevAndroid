package aahl.apiconsumerapp.ui.Episodios;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.api.ApiService;
import aahl.apiconsumerapp.api.RetrofitClient;
import aahl.apiconsumerapp.api.urlIDStriper;
import aahl.apiconsumerapp.models.Character;
import aahl.apiconsumerapp.models.Episode;
import aahl.apiconsumerapp.ui.Personajes.LogicaRecyclerViews.CharacterAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetalleEpisodio extends Fragment {

    private TextView tvNombreEpisodio;
    private TextView tvCodigoEpisodio;
    private TextView tvFechaEmisionEpisodio;
    private RecyclerView recyclerView;
    private CharacterAdapter adapter;
    private ApiService apiService;
    private List<Character> charactersList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.disp_detalle_episodio, container, false);

        tvNombreEpisodio = view.findViewById(R.id.tvNombreEpisodio);
        tvCodigoEpisodio = view.findViewById(R.id.tvCodigoEpisodio);
        tvFechaEmisionEpisodio = view.findViewById(R.id.tvFechaEmisionEpisodio);
        recyclerView = view.findViewById(R.id.rvPersonajesEpisodio);

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

        if (episode.getCharacters() != null) {
            obtenerPersonajes(episode.getCharacters());
            configurarRecyclerViewPersonajes();
        }
    }

    private void configurarRecyclerViewPersonajes(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CharacterAdapter(charactersList);

        adapter.setOnClickListener(position -> {
            // Crear el Bundle con el personaje
            Bundle bundle = new Bundle();
            bundle.putSerializable("character", charactersList.get(position));

            // Navegar al fragmento de detalle
            Navigation.findNavController(recyclerView)
                    .navigate(R.id.action_detalleEpisodio_to_detallePersonaje, bundle);
        });

        recyclerView.setAdapter(adapter);
    }

    private void obtenerPersonajes(String[] charactersURLs) {

        Retrofit retrofitClient = RetrofitClient.getRetrofitInstance();
        apiService = retrofitClient.create(ApiService.class);
        StringBuilder charactersIds = new StringBuilder();

        for (String charURL : charactersURLs) {
            charactersIds
                    .append(urlIDStriper.getIDFromURL(charURL))
                    .append(",");
        }

        // Por default siempre queda una coma al final que está mal, se tiene que eliminar
        charactersIds.deleteCharAt(charactersIds.length() - 1);

        String ids = charactersIds.toString();

        // Si es un solo ID, la API devuelve un objeto, si son múltiples devuelve un array
        if (ids.contains(",")) {
            // Múltiples personajes - devuelve array
            Call<List<Character>> call = apiService.getCharactersByIds(ids);
            call.enqueue(new Callback<List<Character>>() {
                @Override
                public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        charactersList.clear();
                        charactersList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Character>> call, Throwable t) {
                    Log.e("DetalleEpisodio", "Error al obtener personajes: " + t.getMessage());
                    Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Un solo personaje - devuelve objeto
            int characterId = Integer.parseInt(ids);
            Call<Character> call = apiService.getCharacterDetails(characterId);
            call.enqueue(new Callback<Character>() {
                @Override
                public void onResponse(Call<Character> call, Response<Character> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        charactersList.clear();
                        charactersList.add(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Character> call, Throwable t) {
                    Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
