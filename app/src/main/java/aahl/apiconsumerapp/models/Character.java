package aahl.apiconsumerapp.models;

// Sirve para /api/character/:id
public class Character {

    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private Object origin; // {name, url}
    private String gender;
    private Object location; // {name, url}
    private String image; // URL
    private String[] episode; // Array de URLs de episodios

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public Object getOrigin() {
        return origin;
    }

    public String getGender() {
        return gender;
    }

    public Object getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public String[] getEpisode() {
        return episode;
    }

}
