package prjs.adriano.com.sherlock.Classes.hop;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adriano on 25/02/2018.
 */

public class Transit extends Hop {
    public String getDepartureStop() {
        return departureStop;
    }

    public void setDepartureStop(String departureStop) {
        this.departureStop = departureStop;
    }

    public Integer getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Integer departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalStop() {
        return arrivalStop;
    }

    public void setArrivalStop(String arrivalStop) {
        this.arrivalStop = arrivalStop;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getnStops() {
        return nStops;
    }

    public void setnStops(int nStops) {
        this.nStops = nStops;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    private String departureStop;
    private Integer departureTime;
    private String arrivalStop;
    private Integer id;
    private Integer id_trip;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_trip() {
        return id_trip;
    }

    public void setId_trip(Integer id_trip) {
        this.id_trip = id_trip;
    }

    public Transit(int id, int id_trip, String distance, int distance_value, String duration, int duration_value, String instruction, String departureStop, Integer departureTime, String arrivalStop, Integer arrivalTime, int nStops, Vehicle vehicle, int prox, LatLng startPoint, LatLng endPoint) {
        super(distance, distance_value, duration, duration_value, instruction, prox, startPoint, endPoint);
        this.departureStop = departureStop;
        this.departureTime = departureTime;
        this.arrivalStop = arrivalStop;
        this.arrivalTime = arrivalTime;
        this.nStops = nStops;
        this.vehicle = vehicle;
        this.id = id;
        this.id_trip = id_trip;

    }

    private Integer arrivalTime;
    private int nStops;
    private Vehicle vehicle;

    public Transit(Hop hop, JSONObject obj){
        super(hop);
        try {

            departureStop = obj.getJSONObject("departure_stop").getString("name");
            departureTime = obj.getJSONObject("departure_time").getInt("value");
            arrivalStop = obj.getJSONObject("arrival_stop").getString("name");
            arrivalTime = obj.getJSONObject("arrival_time").getInt("value");
            nStops = obj.getInt("num_stops");
            vehicle = new Vehicle(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
