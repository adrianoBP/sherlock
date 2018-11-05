package prjs.adriano.com.sherlock.Classes;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import prjs.adriano.com.sherlock.Classes.hop.Hop;
import prjs.adriano.com.sherlock.Classes.hop.Transit;
import prjs.adriano.com.sherlock.Classes.hop.Walk;
import prjs.adriano.com.sherlock.DBH.DBHTrip;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.Requests.TripHelper;

import static prjs.adriano.com.sherlock.Activities.MainActivity.fancyShowCaseView;
import static prjs.adriano.com.sherlock.DBH.DBHTrip.allTrips;
import static prjs.adriano.com.sherlock.tabs.Tab1Trips.updateListViewTrips;

/**
 * Created by Adriano on 13/02/2018.
 */

public class TripFinder {


    public static void findTrips(final Context context, final String departure, final String arrival, final String datetime) {

        final int[] size = {0};
        final List<Trip> trips = new ArrayList<>();
        final LottieAnimationView animationView1 = ((Activity) context).findViewById(R.id.anim_loading_trips);
        final LottieAnimationView animationView2 = ((Activity) context).findViewById(R.id.anim_success);
        final LinearLayout linearLayout = ((Activity) context).findViewById(R.id.lltripSelector);

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + departure + "&destination=" + arrival + "&mode=transit&departure_time=" + datetime + "&alternatives=true&key=" + context.getResources().getString(R.string.API_google_directions);

        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        int prox = 0;
                        ArrayList<List<Hop>> allHops = new ArrayList<>();
                        boolean found = false;
                        try {
                            JSONObject jOb = new JSONObject(response);
                            if (Objects.equals("OK", jOb.getString("status"))) {
                                found = true;
                                JSONArray routes = jOb.getJSONArray("routes");
                                for (int i = 0; i < routes.length(); i++) {
                                    List<Hop> hops = new ArrayList<>();
                                    JSONObject route = routes.getJSONObject(i);
                                    JSONArray legs = route.getJSONArray("legs");
                                    Trip trip = new Trip();
                                    for (int j = 0; j < legs.length(); j++) {

                                        JSONObject leg = legs.getJSONObject(j);
                                        JSONArray steps = leg.getJSONArray("steps");

                                        try {
                                            JSONObject dep = leg.getJSONObject("departure_time");
                                            trip.setDeparture_time(dep.getInt("value"));

                                        } catch (Exception ex) {
                                            trip.setDeparture_time(Integer.valueOf(datetime));
                                        }

                                        trip.setDeparture_location(departure);
                                        trip.setArrival_location(arrival);

                                        for (int k = 0; k < steps.length(); k++) {

                                            JSONObject step = steps.getJSONObject(k);

                                            if (step.has("steps")) {
                                                List<Hop> sameHops = new ArrayList<>(add(step, false, prox));
                                                hops.addAll(sameHops);
                                                prox += sameHops.size();
                                                JSONArray stepsInStep = step.getJSONArray("steps");
//                                                prox++;
                                                for (int u = 0; u < stepsInStep.length(); u++) {

                                                    JSONObject stepInStep = stepsInStep.getJSONObject(u);
                                                    List<Hop> sameHopsInStep = new ArrayList<>(add(stepInStep, false, prox));
                                                    hops.addAll(sameHopsInStep);
                                                    prox += sameHopsInStep.size();
                                                }
                                            } else {
                                                List<Hop> sameHops = new ArrayList<>(add(step, false, prox));
                                                hops.addAll(sameHops);
                                                prox += sameHops.size();
                                            }
                                        }
                                        try {
                                            JSONObject arr = leg.getJSONObject("arrival_time");
                                            trip.setArrival_time(arr.getInt("value"));

                                        } catch (Exception ex) {
                                            Integer counter = 0;
                                            for (Hop h : hops) {
                                                counter += h.getDuration_value();
                                            }
                                            trip.setArrival_time(Integer.valueOf(datetime) + counter);
                                        }
                                        trip.setHops(hops);
                                        trips.add(trip);
                                    }

                                    allHops.add(hops);
                                }

                            } else {
                                found = false;
                            }
                        } catch (Exception ex) {
                            Log.e("TRIPS", ex.getMessage());
                        }
                        size[0] = allHops.size();


                        if (!found) {
                            fancyShowCaseView.hide();
                            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Route not found! Try anothe one!", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                        }
                                    })
                                    .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        } else {
                            animationView1.cancelAnimation();
                            animationView1.clearAnimation();

                            animationView1.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            for (final Trip trip : trips) {
                                final View tripCard = ((Activity) context).getLayoutInflater().inflate(R.layout.card_select_trip, null);

                                TextView tvDepartureTime = tripCard.findViewById(R.id.tvDepartureTime);
                                TextView tvArrivalTime = tripCard.findViewById(R.id.tvArrivalTime);
                                tvDepartureTime.setText(Global.convertToTime(String.valueOf(trip.getDeparture_time())));
                                tvArrivalTime.setText(Global.convertToTime(String.valueOf(trip.getArrival_time())));


                                final LinearLayout llSteps = tripCard.findViewById(R.id.llStepsSelect);

                                View initzr = ((Activity) context).getLayoutInflater().inflate(R.layout.custom_adapter_hop_select_initlzr, null);
                                final ImageView ivUpDown = initzr.findViewById(R.id.ivUpDownSteps);

                                final TextView tvInstructions = initzr.findViewById(R.id.tvInstruction);

                                ivUpDown.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (Objects.equals("down", ivUpDown.getContentDescription())) {
                                            ivUpDown.setImageDrawable(context.getDrawable(R.drawable.ic_up));
                                            ivUpDown.setContentDescription("up");
                                            for (Hop hop : trip.getHops()) {
                                                View stepView = ((Activity) context).getLayoutInflater().inflate(R.layout.custom_adapter_hop_select, null);

                                                TextView tvInstruction = stepView.findViewById(R.id.tvInstruction);
                                                tvInstruction.setText(hop.getInstruction());

                                                llSteps.addView(stepView);
                                            }

                                        } else {
                                            ivUpDown.setImageDrawable(context.getDrawable(R.drawable.ic_down));
                                            ivUpDown.setContentDescription("down");
                                            llSteps.removeViews(1, trip.getHops().size());
                                        }
                                    }
                                });

                                tvInstructions.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (Objects.equals("down", ivUpDown.getContentDescription())) {
                                            ivUpDown.setImageDrawable(context.getDrawable(R.drawable.ic_up));
                                            ivUpDown.setContentDescription("up");
                                            for (Hop hop : trip.getHops()) {
                                                View stepView = ((Activity) context).getLayoutInflater().inflate(R.layout.custom_adapter_hop_select, null);

                                                TextView tvInstruction = stepView.findViewById(R.id.tvInstruction);
                                                tvInstruction.setText(hop.getInstruction());

                                                llSteps.addView(stepView);
                                            }

                                        } else {
                                            ivUpDown.setImageDrawable(context.getDrawable(R.drawable.ic_down));
                                            ivUpDown.setContentDescription("down");
                                            llSteps.removeViews(1, trip.getHops().size());
                                        }
                                    }
                                });

                                Button bSelect = tripCard.findViewById(R.id.bSelect);
                                bSelect.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        linearLayout.setVisibility(View.GONE);

                                        animationView2.setVisibility(View.VISIBLE);
                                        animationView2.cancelAnimation();
                                        animationView2.setImageAssetsFolder("assets");
                                        animationView2.clearAnimation();
                                        animationView2.setAnimation("anim_success.json");
                                        animationView2.loop(false);  //for continuous looping
                                        animationView2.playAnimation();//start animation

                                        DBHTrip dbhTrip = new DBHTrip(context);
                                        dbhTrip.addTrip(trip);
                                        allTrips = dbhTrip.getAllTrips();
                                        TripHelper.sendTripsData(context);

                                        updateListViewTrips(context, "");
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                //Do something after 100ms
                                                fancyShowCaseView.hide();

                                            }
                                        }, 2200);
//                                        TripHelper.sendTripsData(context);
                                    }
                                });

                                bSelect.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View view) {
                                        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                                        animation1.setDuration(2000);
                                        view.startAnimation(animation1);
                                        return true;
                                    }
                                });

                                llSteps.addView(initzr);

                                linearLayout.addView(tripCard);
                            }


                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("TRIPS", error.getMessage());
                } catch (Exception e) {
                    Log.e("TRIPS", e.getMessage());
                }
                fancyShowCaseView.hide();
                Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Server error!", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
        queue.add(stringRequest);


    }

    private static Hop lastHop = null;

    private static List<Hop> add(JSONObject obj, boolean head, int prox) {
        List<Hop> toAddHops = new ArrayList<>();

        Hop mainHop = new Hop();
        try {
            String distance = obj.getJSONObject("distance").getString("text");
            int distance_value = obj.getJSONObject("distance").getInt("value");
            String duration = obj.getJSONObject("duration").getString("text");
            int duration_value = obj.getJSONObject("duration").getInt("value");
            String instructions;
            LatLng startPos = new LatLng(Float.valueOf(obj.getJSONObject("start_location").getString("lat")),Float.valueOf(obj.getJSONObject("start_location").getString("lng")));
            LatLng endPos = new LatLng(Float.valueOf(obj.getJSONObject("end_location").getString("lat")),Float.valueOf(obj.getJSONObject("end_location").getString("lng")));

            if (obj.has("html_instructions")) {
                instructions = Global.removeHtml(obj.getString("html_instructions"));
            } else {
                instructions = obj.getString("travel_mode").toLowerCase();

            }

            if(obj.has("steps")){
                startPos = null;
                endPos = null;
            }


            instructions = head ? instructions + ":" : instructions;
            Hop hop = new Hop(distance, distance_value, duration, duration_value, instructions, prox, startPos, endPos);
            mainHop = new Hop(hop);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        try {
            switch (obj.getString("travel_mode")) {
                case "WALKING":
                    mainHop = new Walk(mainHop);
                    break;
                case "TRANSIT":
                    mainHop = new Transit(mainHop, obj.getJSONObject("transit_details"));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(lastHop != null) {
            if (Objects.equals(mainHop.getInstruction(), lastHop.getInstruction())) {
                Hop intermediateHop = new Hop(mainHop);
                if(mainHop instanceof Walk) {
                    intermediateHop = new Walk(mainHop);
                }
                intermediateHop.setInstruction("Go straight on");
                mainHop.setIndex(mainHop.getIndex()+1);
                toAddHops.add(intermediateHop);
            }
        }

        lastHop = new Hop(mainHop);

        toAddHops.add(mainHop);
        return toAddHops;
    }


}
