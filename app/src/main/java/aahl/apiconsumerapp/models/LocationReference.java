package aahl.apiconsumerapp.models;

import java.io.Serializable;

public class LocationReference implements Serializable {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
