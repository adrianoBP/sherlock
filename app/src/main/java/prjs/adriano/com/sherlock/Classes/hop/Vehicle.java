package prjs.adriano.com.sherlock.Classes.hop;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adriano on 25/02/2018.
 */

public class Vehicle {
    private Integer id;
    private Integer id_trip;
    private String headSign;
    private String name;
    private String shortName;
    private String color;
    private String type;
    private String agencyName;
    private String agencyUrl;

    public Vehicle() {
    }

    public Vehicle(Integer id, Integer id_trip, String headSign, String name, String shortName, String color, String type, String agencyName, String agencyUrl) {

        this.id = id;
        this.id_trip = id_trip;
        this.headSign = headSign;
        this.name = name;
        this.shortName = shortName;
        this.color = color;
        this.type = type;
        this.agencyName = agencyName;
        this.agencyUrl = agencyUrl;
    }

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

    public Vehicle(JSONObject obj){
        try {
            this.headSign = obj.getString("headsign");

            JSONObject lineInfo = obj.getJSONObject("line");

            if(lineInfo.has("name"))
                this.name = name;
            else
                this.name = null;
            if(lineInfo.has("short_name"))
                this.shortName = lineInfo.getString("short_name");
            if(lineInfo.has("color"))
                this.color = lineInfo.getString("color");
            this.type = lineInfo.getJSONObject("vehicle").getString("name");
            if(lineInfo.getJSONArray("agencies").getJSONObject(0).has("name"))
                this.agencyName = lineInfo.getJSONArray("agencies").getJSONObject(0).getString("name");
            if(lineInfo.getJSONArray("agencies").getJSONObject(0).has("url"))
                this.agencyUrl = lineInfo.getJSONArray("agencies").getJSONObject(0).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    public String getHeadSign() {

        return headSign;
    }

    public void setHeadSign(String headSign) {
        this.headSign = headSign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyUrl() {
        return agencyUrl;
    }

    public void setAgencyUrl(String agencyUrl) {
        this.agencyUrl = agencyUrl;
    }
}
