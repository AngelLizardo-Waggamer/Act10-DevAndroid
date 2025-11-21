package aahl.apiconsumerapp.api;

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
    Call<CharacterCompleteList> getCharacters();

    @GET("character")
    Call<CharacterCompleteList> getCharactersByPage(@Query("page") int page);

    @GET("character/{id}")
    Call<Character> getCharacterDetails(@Path("id") int characterId);

    @GET("episode")
    Call<EpisodeCompleteList> getEpisodes();

    @GET("episode")
    Call<EpisodeCompleteList> getEpisodesByPage(@Query("page") int page);

    @GET("episode/{id}")
    Call<Episode> getEpisodeDetails(@Path("id") int episodeId);

    @GET("location")
    Call<LocationCompleteList> getLocations();

    @GET("location")
    Call<LocationCompleteList> getLocationsByPage(@Query("page") int page);

    @GET("location/{id}")
    Call<Location> getLocationDetails(@Path("id") int locationId);
}
