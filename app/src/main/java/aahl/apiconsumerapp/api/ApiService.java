package aahl.apiconsumerapp.api;

import aahl.apiconsumerapp.models.CharacterCompleteList;
import aahl.apiconsumerapp.models.Episode;
import aahl.apiconsumerapp.models.EpisodeCompleteList;
import aahl.apiconsumerapp.models.Location;
import aahl.apiconsumerapp.models.LocationCompleteList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ApiService {

    @GET("character")
    Call<CharacterCompleteList> getCharacters();

    @GET("character?page={page}")
    Call<CharacterCompleteList> getCharactersByPage(@Path("page") int page);

    @GET("character/{id}")
    Call<Character> getCharacterDetails(@Path("id") int characterId);

    @GET("episode")
    Call<EpisodeCompleteList> getEpisodes();

    @GET("episode?page={page}")
    Call<EpisodeCompleteList> getEpisodesByPage(@Path("page") int page);

    @GET("episode/{id}")
    Call<Episode> getEpisodeDetails(@Path("id") int episodeId);

    @GET("location")
    Call<LocationCompleteList> getLocations();

    @GET("location?page={page}")
    Call<LocationCompleteList> getLocationsByPage(@Path("page") int page);");

    @GET("location/{id}")
    Call<Location> getLocationDetails(@Path("id") int locationId);
}
