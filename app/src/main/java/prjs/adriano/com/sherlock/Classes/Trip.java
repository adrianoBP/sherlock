package prjs.adriano.com.sherlock.Classes;

import java.util.ArrayList;
import java.util.List;

import prjs.adriano.com.sherlock.Classes.hop.Hop;

/**
 * Created by Adriano on 12/02/2018.
 */

public class Trip {

    private Integer id;
    private String departure_location;
    private String arrival_location;

    public Trip(Integer id, String departure_location, String arrival_location, Integer departure_time, Integer arrival_time, List<Hop> hops) {
        this.hops = new ArrayList<>();
        this.id = id;
        this.departure_location = departure_location;
        this.arrival_location = arrival_location;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.hops.addAll(hops);
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer departure_time;
    private Integer arrival_time;
    private List<Hop> hops;

    public List<Hop> getHops() {

        return hops;
    }

    public Trip(Trip trip){
        if (trip!=null) {
            this.hops = new ArrayList<>();
            this.id = trip.id;
            this.departure_location = trip.departure_location;
            this.arrival_location = trip.arrival_location;
            this.departure_time = trip.departure_time;
            this.arrival_time = trip.arrival_time;
            this.hops.addAll(trip.hops);
        }
    }

    public void setHops(List<Hop> hops) {
        this.hops = new ArrayList<>();
        this.hops.addAll(hops);
    }

    public Trip(){this.hops = new ArrayList<>();}

    public String getDeparture_location() {

        return departure_location;
    }

    public void setDeparture_location(String departure_location) {
        this.departure_location = departure_location;
    }

    public String getArrival_location() {
        return arrival_location;
    }

    public void setArrival_location(String arrival_location) {
        this.arrival_location = arrival_location;
    }

    public Integer getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Integer departure_time) {
        this.departure_time = departure_time;
    }

    public Integer getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(Integer arrival_time) {
        this.arrival_time = arrival_time;
    }

    public Integer getDuration(){
        int duration = 0;
        for(Hop h : hops){
            duration+=h.getDuration_value();
        }
        return  duration;
    }

}
