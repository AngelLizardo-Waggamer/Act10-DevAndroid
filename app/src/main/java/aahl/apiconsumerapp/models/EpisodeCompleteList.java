package aahl.apiconsumerapp.models;

import java.util.List;

public class EpisodeCompleteList {

    private Object info; // {count, pages, next, prev}
    private List<Episode> results; // Lista de Episodios hasta la página indicada

    public Object getInfo() {
        return info;
    }

    public List<Episode> getResults() {
        return results;
    }

}
