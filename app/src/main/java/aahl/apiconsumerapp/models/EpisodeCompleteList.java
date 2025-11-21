package aahl.apiconsumerapp.models;

import java.util.List;

public class EpisodeCompleteList {

    private PageInfo info;
    private List<Episode> results;

    public PageInfo getInfo() {
        return info;
    }

    public List<Episode> getResults() {
        return results;
    }

}
