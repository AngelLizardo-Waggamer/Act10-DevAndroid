package aahl.apiconsumerapp.models;

import java.util.List;

public class LocationCompleteList {

    private Object info; // {count, pages, next, prev}
    private List<Location> results; // Lista de lugares hasta la página indicada

    public Object getInfo() {
        return info;
    }

    public List<Location> getResults() {
        return results;
    }

}
