package prjs.adriano.com.sherlock.Classes.hop;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;

/**
 * Created by Adriano on 13/02/2018.
 */

public class Hop {

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getDistance_value() {
        return distance_value;
    }

    public void setDistance_value(int distance_value) {
        this.distance_value = distance_value;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getDuration_value() {
        return duration_value;
    }

    public void setDuration_value(int duration_value) {
        this.duration_value = duration_value;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }


    private String distance;
    private int distance_value;
    private String duration;
    private int duration_value;
    private String instruction;
    private int index;
    private LatLng startPoint;
    private LatLng endPoint;

    public LatLng getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(LatLng startPoint) {
        this.startPoint = startPoint;
    }

    public LatLng getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(LatLng endPoint) {
        this.endPoint = endPoint;
    }

    public int getIndex() {
        return index;
    }

    public Hop(String distance, int distance_value, String duration, int duration_value, String instruction, int index, LatLng startPoint, LatLng endPoint) {
        this.distance = distance;
        this.distance_value = distance_value;
        this.duration = duration;
        this.duration_value = duration_value;
        this.instruction = instruction;
        this.index = index;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public void setIndex(int index) {
        this.index = index;
    }

//    public Hop(String distance, int distance_value, String duration, int duration_value, String instruction, int index) {
//        this.distance = distance;
//        this.distance_value = distance_value;
//        this.duration = duration;
//        this.duration_value = duration_value;
//        this.instruction = instruction;
//        this.index = index;
//    }

    public Hop(){}

    public Hop(Hop hop){
        this.distance = hop.getDistance();
        this.distance_value = hop.getDistance_value();
        this.duration = hop.getDuration();
        this.duration_value = hop.getDuration_value();
        this.instruction = hop.getInstruction();
        this.index = hop.getIndex();
        this.startPoint = hop.getStartPoint();
        this.endPoint = hop.getEndPoint();
    }
}
