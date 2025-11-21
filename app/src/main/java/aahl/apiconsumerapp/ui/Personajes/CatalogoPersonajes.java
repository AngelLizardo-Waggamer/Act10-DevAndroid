package aahl.apiconsumerapp.ui.Personajes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.api.ApiService;
import aahl.apiconsumerapp.api.RetrofitClient;
import aahl.apiconsumerapp.models.Character;
import aahl.apiconsumerapp.models.CharacterCompleteList;
import aahl.apiconsumerapp.models.PageInfo;
import aahl.apiconsumerapp.ui.Personajes.LogicaRecyclerViews.CharacterAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CatalogoPersonajes extends Fragment {

    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton fabMasElementos;
    private CharacterAdapter adapter;
    private List<Character> characterList = new ArrayList<>();
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
                    if (lastVisibleItemPosition >= totalItemsCount - 3 && hasMorePages && !isLoading){
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

    private void configurarRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CharacterAdapter(characterList);
        recyclerView.setAdapter(adapter);

        // Configuración del scroll listener para que el fab aparezca o desaparezca
        configurarScrollListenerEnRecycler(layoutManager);
    }

    private void cargarPersonajes(int page) {
        // No se pueden cargar más elementos si se están cargando otros elementos
        if (isLoading) return;

        // Indicar que está cargando elementos
        isLoading = true;

        // Request de Retrofit
        Call<CharacterCompleteList> call = apiService.getCharactersByPage(page);
        call.enqueue(new Callback<CharacterCompleteList>() {
            @Override
            public void onResponse(Call<CharacterCompleteList> call, Response<CharacterCompleteList> response) {
                isLoading = false;

                if (response.isSuccessful() && response.body() != null) {
                    CharacterCompleteList completeList = response.body();
                    PageInfo pageInfo = completeList.getInfo();
                    List<Character> newCharacters = completeList.getResults();

                    // Agregar nuevos personajes a la lista
                    int startPosition = characterList.size();
                    characterList.addAll(newCharacters);
                    adapter.notifyItemRangeInserted(startPosition, newCharacters.size());

                    // Verificar si existen más páginas
                    hasMorePages = pageInfo.hasNextPage();

                    // Si no tiene, ocultar el FAB. Este no volverá a aparecer.
                    if (!hasMorePages) {
                        fabMasElementos.hide();
                    }
                } else {
                    showToast("Error al cargar personajes");
                }
            }

            @Override
            public void onFailure(Call<CharacterCompleteList> call, Throwable t) {
                isLoading = false;
                showToast("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void cargarMasElementos() {
        if (!hasMorePages || isLoading) return;

        currentPage++;
        fabMasElementos.hide();
        cargarPersonajes(currentPage);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Iniciar la vista que se va a devolver
        View view = inflater.inflate(R.layout.fragment_catalogo_personajes, container, false);

        // Inicializar el recycler y el floatingActionButton
        recyclerView = view.findViewById(R.id.recycler_view_personajes);
        fabMasElementos = view.findViewById(R.id.fab_maselementos_personajes);

        // Configuración del recyclerView
        configurarRecyclerView();

        // Configuración de Retrofit
        Retrofit retrofitClient = RetrofitClient.getRetrofitInstance();
        apiService = retrofitClient.create(ApiService.class);

        // Cargar la primera pagina
        cargarPersonajes(currentPage);

        // Click listener del FAB
        fabMasElementos.setOnClickListener(v -> cargarMasElementos());

        // Devolver la vista
        return view;
    }

}
