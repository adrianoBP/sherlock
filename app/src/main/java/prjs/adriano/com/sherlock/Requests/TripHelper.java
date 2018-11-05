package prjs.adriano.com.sherlock.Requests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import prjs.adriano.com.sherlock.Activities.MainActivity;
import prjs.adriano.com.sherlock.Classes.Trip;
import prjs.adriano.com.sherlock.Classes.hop.Hop;
import prjs.adriano.com.sherlock.Classes.hop.Transit;
import prjs.adriano.com.sherlock.Classes.hop.Vehicle;
import prjs.adriano.com.sherlock.Classes.hop.Walk;
import prjs.adriano.com.sherlock.DBH.DBHTrip;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;

import static prjs.adriano.com.sherlock.DBH.DBHTrip.allTrips;
import static prjs.adriano.com.sherlock.tabs.Tab1Trips.srlMain;
import static prjs.adriano.com.sherlock.tabs.Tab1Trips.updateListViewTrips;

/**
 * Created by Adriano on 12/02/2018.
 */

public class TripHelper {

    public static void sendTripsData(final Context context) {
        DBHTrip dbhTrip = new DBHTrip(context);

//        List<Trip> tripsArray = new ArrayList<>(dbhTrip.getAllTrips());

        Map<String, Object> mainObject = new HashMap<>();
        mainObject.put("uid", Global.returnUid(context));

        List<Map<String, Object>> trips = new ArrayList<>();
        for (Trip trip : allTrips) {

            Map<String, Object> tripObj = new HashMap<>();
            tripObj.put("id", trip.getId());
            tripObj.put("departure_location", trip.getDeparture_location());
            tripObj.put("departure_time", trip.getDeparture_time());
            tripObj.put("arrival_location", trip.getArrival_location());
            tripObj.put("arrival_time", trip.getArrival_time());

            List<Map<String, Object>> walks = new ArrayList<>();
            List<Map<String, Object>> transits = new ArrayList<>();
            List<Map<String, Object>> vehicles = new ArrayList<>();

            for (Hop hop : trip.getHops()) {
                if (hop instanceof Walk) {
                    Map<String, Object> walkObj = new HashMap<>();
                    walkObj.put("id", ((Walk) hop).getId() + "");
                    walkObj.put("id_trip", ((Walk) hop).getId_trip() + "");
                    walkObj.put("distance", hop.getDistance() + "");
                    walkObj.put("distance_value", hop.getDistance_value() + "");
                    walkObj.put("duration", hop.getDuration() + "");
                    walkObj.put("duration_value", hop.getDuration_value() + "");
                    walkObj.put("instruction", hop.getInstruction() + "");
                    walkObj.put("prox", hop.getIndex() + "");
                    if (hop.getStartPoint() != null) {
                        walkObj.put("start_point_x", hop.getStartPoint().latitude + "");
                        walkObj.put("start_point_y", hop.getStartPoint().longitude + "");
                        walkObj.put("end_point_x", hop.getEndPoint().latitude + "");
                        walkObj.put("end_point_y", hop.getEndPoint().longitude + "");
                    } else {
                        walkObj.put("start_point_x", null);
                        walkObj.put("start_point_y", null);
                        walkObj.put("end_point_x", null);
                        walkObj.put("end_point_y", null);
                    }

                    walks.add(walkObj);
                } else if (hop instanceof Transit) {
                    Vehicle vehicle = ((Transit) hop).getVehicle();

                    Map<String, Object> transitObj = new HashMap<>();
                    transitObj.put("id", ((Transit) hop).getId() + "");
                    transitObj.put("id_trip", ((Transit) hop).getId_trip() + "");
                    transitObj.put("distance", hop.getDistance() + "");
                    transitObj.put("distance_value", hop.getDistance_value() + "");
                    transitObj.put("duration", hop.getDuration() + "");
                    transitObj.put("duration_value", hop.getDuration_value() + "");
                    transitObj.put("instruction", hop.getInstruction() + "");
                    transitObj.put("departure_stop", ((Transit) hop).getDepartureStop() + "");
                    transitObj.put("departure_time", ((Transit) hop).getDepartureTime() + "");
                    transitObj.put("arrival_stop", ((Transit) hop).getArrivalStop() + "");
                    transitObj.put("arrival_time", ((Transit) hop).getArrivalTime() + "");
                    transitObj.put("n_stops", ((Transit) hop).getnStops() + "");
                    transitObj.put("id_vehicle", vehicle.getId() + "");
                    transitObj.put("prox", hop.getIndex() + "");
                    if (hop.getStartPoint() != null) {
                        transitObj.put("start_point_x", hop.getStartPoint().latitude + "");
                        transitObj.put("start_point_y", hop.getStartPoint().longitude + "");
                        transitObj.put("end_point_x", hop.getEndPoint().latitude + "");
                        transitObj.put("end_point_y", hop.getEndPoint().longitude + "");
                    } else {
                        transitObj.put("start_point_x", null);
                        transitObj.put("start_point_y", null);
                        transitObj.put("end_point_x", null);
                        transitObj.put("end_point_y", null);
                    }
                    transits.add(transitObj);

                    Map<String, Object> vehicleObj = new HashMap<>();
                    vehicleObj.put("id", vehicle.getId() + "");
                    vehicleObj.put("id_trip", vehicle.getId_trip() + "");
                    vehicleObj.put("headsign", vehicle.getHeadSign() + "");
                    vehicleObj.put("name", vehicle.getName() + "");
                    vehicleObj.put("short_name", vehicle.getShortName() + "");
                    vehicleObj.put("color", vehicle.getColor() + "");
                    vehicleObj.put("type", vehicle.getType() + "");
                    vehicleObj.put("agency_name", vehicle.getAgencyName() + "");
                    vehicleObj.put("agency_url", vehicle.getAgencyUrl() + "");
                    vehicles.add(vehicleObj);
                }
            }
            tripObj.put("walks", walks);
            tripObj.put("transits", transits);
            tripObj.put("vehicles", vehicles);
            trips.add(tripObj);
        }
        mainObject.put("trips", trips);
        String url = context.getString(R.string.serverURL) + "/trip/add.php";

        JSONObject jo = new JSONObject(mainObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(mainObject),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String success = response.getString("success");
                            if (Objects.equals(success, "true")) {
                                Global.setSync(context, true);
                            } else {
                                Log.e("TRIPS_ADD", response.getString("message"));
                            }
                        } catch (JSONException e) {
                            Log.e("TRIPS_ADD", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TRIPS_ADD", error.toString() + "\n+_" + error.getLocalizedMessage());
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);

    }

    public static void getTrips(final Context context, final boolean start) {
        final DBHTrip dbhTrip = new DBHTrip(context);
        String url = context.getString(R.string.serverURL) + "/trip/get.php";

        Map<String, String> params = new HashMap<>();
        params.put("uid", Global.returnUid(context));

//        allTrips.clear();
//        dbhTrip.clearTrips();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    String success = response.getString("success");
                                    if (Objects.equals(success, "true")) {

                                        allTrips.clear();
                                        dbhTrip.clearTrips();
//                                List<Trip> trips = new ArrayList<>();

                                        JSONArray result = response.getJSONArray("result");
                                        for (int i = 0; i < result.length(); i++) {
                                            List<Hop> hops = new ArrayList<>();

                                            JSONObject tripObj = result.getJSONObject(i);
                                            JSONArray walks = tripObj.getJSONArray("walks");
                                            JSONArray transits = tripObj.getJSONArray("transits");
                                            JSONArray vehicles = tripObj.getJSONArray("vehicles");


                                            for (int w = 0; w < walks.length(); w++) {
                                                JSONObject walkObj = walks.getJSONObject(w);
                                                LatLng startPoint = null;
                                                LatLng endPoint = null;
                                                if (!TextUtils.isEmpty(walkObj.getString("start_point_x"))) {
                                                    startPoint = new LatLng(Float.valueOf(walkObj.getString("start_point_x")), Float.valueOf(walkObj.getString("start_point_y")));
                                                    endPoint = new LatLng(Float.valueOf(walkObj.getString("end_point_x")), Float.valueOf(walkObj.getString("end_point_y")));
                                                }
                                                Walk walk = new Walk(
                                                        walkObj.getInt("id"),
                                                        walkObj.getInt("id_trip"),
                                                        walkObj.getString("distance"),
                                                        walkObj.getInt("distance_value"),
                                                        walkObj.getString("duration"),
                                                        walkObj.getInt("duration_value"),
                                                        walkObj.getString("instruction"),
                                                        walkObj.getInt("prox"),
                                                        startPoint,
                                                        endPoint
                                                );

                                                hops.add(walk);
                                            }

                                            List<Vehicle> vehiclesArray = new ArrayList<>();
                                            for (int v = 0; v < vehicles.length(); v++) {
                                                JSONObject vehicleObj = vehicles.getJSONObject(v);
                                                Vehicle vehicle = new Vehicle(
                                                        vehicleObj.getInt("id"),
                                                        vehicleObj.getInt("id_trip"),
                                                        vehicleObj.getString("headsign"),
                                                        vehicleObj.getString("name"),
                                                        vehicleObj.getString("short_name"),
                                                        vehicleObj.getString("color"),
                                                        vehicleObj.getString("type"),
                                                        vehicleObj.getString("agency_name"),
                                                        vehicleObj.getString("agency_url")
                                                );
                                                vehiclesArray.add(vehicle);
                                            }

                                            for (int t = 0; t < transits.length(); t++) {
                                                JSONObject transitObj = transits.getJSONObject(t);
                                                Vehicle vehicle = new Vehicle();
                                                for (Vehicle v : vehiclesArray) {
                                                    if (v.getId() == transitObj.getInt("id_vehicle")) {
                                                        vehicle = v;
                                                    }
                                                }
                                                LatLng startPoint = null;
                                                LatLng endPoint = null;
                                                if (!TextUtils.isEmpty(transitObj.getString("start_point_x"))) {
                                                    startPoint = new LatLng(Float.valueOf(transitObj.getString("start_point_x")), Float.valueOf(transitObj.getString("start_point_y")));
                                                    endPoint = new LatLng(Float.valueOf(transitObj.getString("end_point_x")), Float.valueOf(transitObj.getString("end_point_y")));
                                                }
                                                Transit transit = new Transit(
                                                        transitObj.getInt("id"),
                                                        transitObj.getInt("id_trip"),
                                                        transitObj.getString("distance"),
                                                        transitObj.getInt("distance_value"),
                                                        transitObj.getString("duration"),
                                                        transitObj.getInt("duration_value"),
                                                        transitObj.getString("instruction"),
                                                        transitObj.getString("departure_stop"),
                                                        transitObj.getInt("departure_time"),
                                                        transitObj.getString("arrival_stop"),
                                                        transitObj.getInt("arrival_time"),
                                                        transitObj.getInt("n_stops"),
                                                        vehicle,
                                                        transitObj.getInt("prox"),
                                                        startPoint,
                                                        endPoint

                                                );

                                                hops.add(transit);
                                            }


                                            Trip trip = new Trip(
                                                    tripObj.getInt("id"),
                                                    tripObj.getString("departure_location"),
                                                    tripObj.getString("arrival_location"),
                                                    tripObj.getInt("departure_time"),
                                                    tripObj.getInt("arrival_time"),
                                                    hops
                                            );

                                            allTrips.add(trip);
                                            dbhTrip.addTrip(trip);


//                                    trips.add(trip);
                                        }
//                                for (Trip trip : trips) {
//                                    dbhTrip.addTrip(trip);
//                                }
                                        if (start) {
                                            Intent intent = new Intent(context, MainActivity.class);
                                            ((Activity) context).finish();
                                            context.startActivity(intent);
                                            ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                        } else {
                                            ((Activity) context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                                    updateListViewTrips(context, Global.returnPreferredSorting(context));
                                                }
                                            });
                                        }

                                    } else {
                                        allTrips = dbhTrip.getAllTrips();
                                        Log.i("I!", response.getString("message"));
                                        if (Objects.equals(response.getString("message"), "No data found.")) {
                                            if (start) {
                                                Intent intent = new Intent(context, MainActivity.class);
                                                ((Activity) context).finish();
                                                context.startActivity(intent);
                                                ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                            }
                                            if (srlMain != null) {
                                                ((Activity) context).runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        srlMain.setRefreshing(false);
                                                    }
                                                });
                                            }
                                        } else {
                                            ((Activity) context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                                    updateListViewTrips(context, Global.returnPreferredSorting(context));
                                                }
                                            });
                                        }
                                    }
                                    Global.setOnline(context, true);
                                } catch (JSONException e) {
                                    Log.e("TRIPS_GET", e.toString());
                                    allTrips = dbhTrip.getAllTrips();
                                }
                            }
                        }).start();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("TRIPS_GET", error.toString());
                                if (error instanceof NoConnectionError) {
                                    Global.setOnline(context, false);
                                    if (start) {
                                        Intent intent = new Intent(context, MainActivity.class);
                                        ((Activity) context).finish();
                                        context.startActivity(intent);
                                        ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                    } else {
                                        ((Activity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                                updateListViewTrips(context, Global.returnPreferredSorting(context));
                                            }
                                        });
                                    }
                                }

                            }
                        }).start();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }

}