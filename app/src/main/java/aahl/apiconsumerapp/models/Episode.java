package aahl.apiconsumerapp.models;

// Sirve para /api/episode/:id
public class Episode {

    private int id;
    private String name;
    private String air_date;
    private String episode; // Codigo de episodio "S{x}E{y}
    private String[] characters; // Array de URLs de personajes

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAir_date() {
        return air_date;
    }

    public String getEpisode() {
        return episode;
    }

    public String[] getCharacters() {
        return characters;
    }

}
