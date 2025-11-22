package aahl.apiconsumerapp.ui.Locations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.api.ApiService;
import aahl.apiconsumerapp.api.RetrofitClient;
import aahl.apiconsumerapp.models.Location;
import aahl.apiconsumerapp.models.LocationCompleteList;
import aahl.apiconsumerapp.models.PageInfo;
import aahl.apiconsumerapp.ui.Locations.LogicaRecyclerViews.LocationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CatalogoLocations extends Fragment {

    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton fabMasElementos;
    private LocationAdapter adapter;
    private List<Location> locationList = new ArrayList<>();
    private ApiService apiService;

    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean hasMorePages = true;

    private void showToast(String message) {
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
        adapter = new LocationAdapter(locationList);

        adapter.setOnClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("location", locationList.get(position));

            // Navegar al fragmento de detalle
            Navigation.findNavController(recyclerView)
                    .navigate(R.id.action_catalogoLocations_to_detalleLocation, bundle);
        });

        recyclerView.setAdapter(adapter);

        // Configuración del scroll listener para que el fab aparezca o desaparezca
        configurarScrollListenerEnRecycler(layoutManager);
    }

    private void cargarLocations(int page){
        // No se pueden cargar más elementos si se están cargando otros elementos
        if (isLoading) return;

        // Indicar que está cargando elementos
        isLoading = true;

        // Request de Retrofit
        Call<LocationCompleteList> call = apiService.getLocationsByPage(page);
        call.enqueue(new Callback<LocationCompleteList>() {
            @Override
            public void onResponse(Call<LocationCompleteList> call, Response<LocationCompleteList> response) {
                isLoading = false;

                if (response.isSuccessful() && response.body() != null) {
                    LocationCompleteList completeList = response.body();
                    PageInfo pageInfo = completeList.getInfo();
                    List<Location> newLocations = completeList.getResults();

                    // Agregar nuevas localizaciones a la lista
                    int startPosition = locationList.size();
                    locationList.addAll(newLocations);
                    adapter.notifyItemRangeInserted(startPosition, newLocations.size());

                    // Verificar si existen más páginas
                    hasMorePages = pageInfo.hasNextPage();

                    // Si no tiene, ocultar el FAB. Este no volverá a aparecer.
                    if (!hasMorePages) {
                        fabMasElementos.hide();
                    }
                }
            }

            @Override
            public void onFailure(Call<LocationCompleteList> call, Throwable t) {
                isLoading = false;
                showToast("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void cargarMasLocations(){
        if (!hasMorePages || isLoading) return;

        currentPage++;
        fabMasElementos.hide();
        cargarLocations(currentPage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Iniciar la vista que se va a devolver
        View view = inflater.inflate(R.layout.fragment_catalogo_locations, container, false);

        // Inicializar el recycler y el floatingActionButton
        recyclerView = view.findViewById(R.id.recycler_view_locations);
        fabMasElementos = view.findViewById(R.id.fab_maselementos_locations);

        // Configuración del recyclerView
        configurarRecyclerView();

        // Configuración de Retrofit
        Retrofit retrofitClient = RetrofitClient.getRetrofitInstance();
        apiService = retrofitClient.create(ApiService.class);

        // Cargar la primera pagina SI la lista está vacía (soy un genio)
        if (locationList.isEmpty()) cargarLocations(currentPage);

        // Click listener del FAB
        fabMasElementos.setOnClickListener(v -> cargarMasLocations());

        // Devolver la vista
        return view;

    }
}