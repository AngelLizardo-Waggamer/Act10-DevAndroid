package aahl.apiconsumerapp.models;

// Sirve para /api/location/:id
public class Location {

    private int id;
    private String name;
    private String type;
    private String dimension;
    private String[] residents; // Array de URLs de personajes

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDimension() {
        return dimension;
    }

    public String[] getResidents() {
        return residents;
    }

}
