package prjs.adriano.com.sherlock.Classes.hop;

/**
 * Created by Adriano on 13/02/2018.
 */

public class Location {
    private String lat;
    private String lng;

    public Location(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
