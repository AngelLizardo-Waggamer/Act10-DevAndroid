package aahl.apiconsumerapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://rickandmortyapi.com/api/";

    /**
     * Construye una entidad de retrofit en caso de que no esté definida, es decir, al inicio de la aplicación será nula pero cuando se llame por primera vez
     * se construirá y para las siguientes llamadas no se volverá a construir.
     * @return
     */
    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }

}
