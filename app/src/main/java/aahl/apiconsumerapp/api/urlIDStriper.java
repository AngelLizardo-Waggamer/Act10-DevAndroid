package aahl.apiconsumerapp.api;

public class urlIDStriper {

    // Mejor estandarizar esto vdd
    public static String getIDFromURL(String URL){
        return URL.substring(URL.lastIndexOf("/") + 1);
    }

}
