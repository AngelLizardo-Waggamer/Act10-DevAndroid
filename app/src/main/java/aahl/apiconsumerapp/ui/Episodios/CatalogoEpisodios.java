package aahl.apiconsumerapp.ui.Episodios;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.api.ApiService;
import aahl.apiconsumerapp.api.RetrofitClient;
import aahl.apiconsumerapp.models.Episode;
import aahl.apiconsumerapp.models.EpisodeCompleteList;
import aahl.apiconsumerapp.models.PageInfo;
import aahl.apiconsumerapp.ui.Episodios.LogicaRecyclerViews.EpisodeAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CatalogoEpisodios extends Fragment {

    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton fabMasElementos;
    private EpisodeAdapter adapter;
    private List<Episode> episodeList = new ArrayList<>();
    private ApiService apiService;

    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean hasMorePages = true;

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void configurarScrollListenerEnRecycler(LinearLayoutManager layoutManager){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Obtener la posición del último elemento visible en el recycler y el total de elementos.
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int totalItemsCount = layoutManager.getItemCount();

                if (dy > 0 /* Bajando */) {

                    // Si la posición del último item visible es mayor al total de items menos tres,
                    // lo cual significa que está cerca de llegar al final, y todavía hay más
                    // páginas de personajes para consultar, se muestra el FAB
                    if (lastVisibleItemPosition >= totalItemsCount - 1 && hasMorePages && !isLoading){
                        fabMasElementos.show();
                    }

                } else if (dy < 0 /* Subiendo */) {

                    // Si el usuario sube, el último elemento deja de verse, entonces el FAB se debe
                    // de ocultar
                    fabMasElementos.hide();
                }
            }
        });
    }

    private void configurarRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EpisodeAdapter(episodeList);

        adapter.setOnClickListener(position -> {
            // Crear el Bundle con el episodio
            Bundle bundle = new Bundle();
            bundle.putSerializable("episode", episodeList.get(position));

            // Navegar al fragmento de detalle
            Navigation.findNavController(recyclerView)
                    .navigate(R.id.action_catalogoEpisodios_to_detalleEpisodio, bundle);
        });

        recyclerView.setAdapter(adapter);

        configurarScrollListenerEnRecycler(layoutManager);
    }

    private void cargarEpisodios(int page){
        // No se pueden cargar más elementos si se están cargando otros elementos
        if (isLoading) return;

        // Indicar que se está cargando elementos
        isLoading = true;

        // Request de Retrofit
        Call<EpisodeCompleteList> call = apiService.getEpisodesByPage(page);
        call.enqueue(new Callback<EpisodeCompleteList>() {
            @Override
            public void onResponse(Call<EpisodeCompleteList> call, Response<EpisodeCompleteList> response) {
                isLoading = false;

                if (response.isSuccessful() && response.body() != null) {
                    EpisodeCompleteList completeList = response.body();
                    PageInfo pageInfo = completeList.getInfo();
                    List<Episode> newEpisodes = completeList.getResults();

                    // Agregar nuevos episodios a la lista
                    int startPosition = episodeList.size();
                    episodeList.addAll(newEpisodes);
                    adapter.notifyItemRangeInserted(startPosition, newEpisodes.size());

                    // Verificar si existen más páginas
                    hasMorePages = pageInfo.hasNextPage();

                    // Si no tiene, ocultar el FAB. Este no volverá a aparecer.
                    if (!hasMorePages) {
                        fabMasElementos.hide();
                    }
                } else {
                    showToast("Error al cargar episodios");
                }
            }

            @Override
            public void onFailure(Call<EpisodeCompleteList> call, Throwable t) {
                isLoading = false;
                showToast("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void cargarMasEpisodios() {
        if (!hasMorePages || isLoading) return;

        currentPage++;
        fabMasElementos.hide();
        cargarEpisodios(currentPage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Iniciar la vista que se va a devolver
        View view = inflater.inflate(R.layout.fragment_catalogo_episodios, container, false);

        // Inicializar el recycler y el floatingActionButton
        recyclerView = view.findViewById(R.id.recycler_view_episodios);
        fabMasElementos = view.findViewById(R.id.fab_maselementos_episodios);

        // Configuración del recyclerView
        configurarRecyclerView();

        // Configuración de Retrofit
        Retrofit retrofitClient = RetrofitClient.getRetrofitInstance();
        apiService = retrofitClient.create(ApiService.class);

        // Cargar la primera pagina SI la lista está vacía (soy un genio)
        if (episodeList.isEmpty()) cargarEpisodios(currentPage);

        // Click listener del FAB
        fabMasElementos.setOnClickListener(v -> cargarMasEpisodios());

        // Devolver la vista
        return view;
    }
}