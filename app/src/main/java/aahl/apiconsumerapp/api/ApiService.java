package aahl.apiconsumerapp.api;

import java.util.List;

import aahl.apiconsumerapp.models.Character;
import aahl.apiconsumerapp.models.CharacterCompleteList;
import aahl.apiconsumerapp.models.Episode;
import aahl.apiconsumerapp.models.EpisodeCompleteList;
import aahl.apiconsumerapp.models.Location;
import aahl.apiconsumerapp.models.LocationCompleteList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("character")
    Call<CharacterCompleteList> getCharactersByPage(@Query("page") int page);

    @GET("character/{id}")
    Call<Character> getCharacterDetails(@Path("id") int characterId);

    @GET("character/{ids}")
    Call<List<Character>> getCharactersByIds(@Path("ids") String characterIds);

    @GET("episode")
    Call<EpisodeCompleteList> getEpisodesByPage(@Query("page") int page);

    @GET("episode/{id}")
    Call<Episode> getEpisodeDetails(@Path("id") int episodeId);

    @GET("episode/{ids}")
    Call<List<Episode>> getEpisodesByIds(@Path("ids") String episodeIds);

    @GET("location")
    Call<LocationCompleteList> getLocationsByPage(@Query("page") int page);

    @GET("location/{id}")
    Call<Location> getLocationDetails(@Path("id") int locationId);

    @GET("location/{ids}")
    Call<List<Location>> getLocationsByIds(@Path("ids") String locationIds);
}
