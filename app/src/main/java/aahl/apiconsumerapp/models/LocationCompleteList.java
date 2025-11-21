package aahl.apiconsumerapp.models;

import java.util.List;

public class LocationCompleteList {

    private PageInfo info;
    private List<Location> results;

    public PageInfo getInfo() {
        return info;
    }

    public List<Location> getResults() {
        return results;
    }

}
