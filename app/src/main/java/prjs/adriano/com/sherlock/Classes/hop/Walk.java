package prjs.adriano.com.sherlock.Classes.hop;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;

/**
 * Created by Adriano on 13/02/2018.
 */

public class Walk extends Hop {

    private int id;
    private int id_trip;

    public int getId() {
        return id;
    }

    public int getId_trip() {
        return id_trip;
    }

    public Walk(Integer id, Integer id_trip, String distance, int distance_value, String duration, int duration_value, String instruction, int prox, LatLng startPoint, LatLng endPoint) {
        super(distance, distance_value, duration, duration_value, instruction, prox, startPoint, endPoint);
        this.id = id;
        this.id_trip =id_trip;
    }

    public Walk() {
    }

    public Walk(Hop hop) {
        super(hop);
    }
}
