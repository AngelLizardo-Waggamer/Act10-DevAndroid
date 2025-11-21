package aahl.apiconsumerapp.models;

// Sirve para /api/character/:id
public class Character {

    public static class LocationReference {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private LocationReference origin;
    private String gender;
    private LocationReference location;
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

    public LocationReference getOrigin() {
        return origin;
    }

    public String getGender() {
        return gender;
    }

    public LocationReference getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public String[] getEpisode() {
        return episode;
    }

}
