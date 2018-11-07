package prjs.adriano.com.sherlock.Requests;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.OnViewInflateListener;
import prjs.adriano.com.sherlock.Classes.Airport;
import prjs.adriano.com.sherlock.Classes.Currency;
import prjs.adriano.com.sherlock.Classes.TripFinder;
import prjs.adriano.com.sherlock.DBH.DBHUtil;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.Services.MySingleton;

import static prjs.adriano.com.sherlock.Activities.MainActivity.bObserve;
import static prjs.adriano.com.sherlock.Activities.MainActivity.fancyShowCaseView;
import static prjs.adriano.com.sherlock.tabs.Tab3Utility.routes;

public class UtilHelper {

    public static void getCurrencies(final Context context) {
        String url = context.getString(R.string.serverURL) + "/util/getcurrencies.php";
        final DBHUtil dbhUtil = new DBHUtil(context);
        final boolean tableEmpty = dbhUtil.checkCurrencyEmpty();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            List<Currency> currencies = new ArrayList<>();
                            try {
                                JSONObject currencyOBJ = response.getJSONObject(i);
                                Currency currency = new Currency(
                                        currencyOBJ.getInt("id"),
                                        currencyOBJ.getString("code"),
                                        currencyOBJ.getString("name"),
                                        currencyOBJ.getString("symbol"),
                                        currencyOBJ.getString("value"),
                                        currencyOBJ.getString("country"));
                                currencies.add(currency);
                            } catch (JSONException e) {
                                Log.e("CURRENCYv0", e.getMessage());
                            }

                            for (Currency currency : currencies) {
                                if (!tableEmpty) {
                                    dbhUtil.updateCurrencies(currencies);
                                    break;
                                } else {
                                    dbhUtil.addCurrency(currency);
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("CURRENCYv1", error.getMessage());
                } catch (Exception e) {
                    Log.e("CURRENCYv2", e.getMessage());
                }
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }

    public static void getAirports(final Context context) {
        String url = context.getString(R.string.serverURL) + "/util/getairports.php";
        final DBHUtil dbhUtil = new DBHUtil(context);
        final boolean tableEmpty = dbhUtil.checkAirportEmpty();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            List<Airport> airports = new ArrayList<>();
                            try {
                                JSONObject airportOBJ = response.getJSONObject(i);
                                Airport airport = new Airport(
                                        airportOBJ.getString("iata"),
                                        airportOBJ.getString("name")
                                );
                                airports.add(airport);
                            } catch (JSONException e) {
                                Log.e("AIRPORTv0", e.getMessage());
                            }

                            for (Airport airport : airports) {
                                if (!tableEmpty) {
                                    dbhUtil.updateAirports(airports);
                                    break;
                                } else {
                                    dbhUtil.addAirport(airport);
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("AIRPORT", error.getMessage());
                } catch (Exception e) {
                    Log.e("AIRPORTv2", e.getMessage());
                }
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }

    public static void getSunriseSunset(final Context context, String latitude, String longitude) {
        String url = "https://api.sunrise-sunset.org/json?lat=" + latitude + "&lng=" + longitude + "&date=today"; // + date
        final DBHUtil dbhUtil = new DBHUtil(context);
        final boolean tableEmpty = dbhUtil.checkCurrencyEmpty();


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String sunrise = "--:--";
                        String sunset = "--:--";
                        try {
                            JSONObject results = response.getJSONObject("results");

                            sunrise = results.getString("sunrise");
                            sunset = results.getString("sunset");

//                            SimpleDateFormat inputSDF = new SimpleDateFormat("HH:mm:ss a");
//                            inputSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
//                            Date dSunrise = inputSDF.parse(sunrise);
//                            Date dSunset = inputSDF.parse(sunset);
////
//                            SimpleDateFormat outputSDF = new SimpleDateFormat("HH:mm:ss a");
//                            sunrise =  outputSDF.format(dSunrise);
//                            sunset =  outputSDF.format(dSunset);

                            sunrise = Global.from12to24(sunrise);
                            sunset = Global.from12to24(sunset);

                        } catch (JSONException e) {
                            Log.e("SUNRISE_SUNSET", e.getMessage());
                        }

                        TextView tvSunrise = ((Activity) context).findViewById(R.id.tvSunrise);
                        TextView tvSunset = ((Activity) context).findViewById(R.id.tvSunset);
                        tvSunrise.setText(sunrise);
                        tvSunset.setText(sunset);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("SUNRISE_SUNSET", error.getMessage());
                } catch (Exception e) {
                    Log.e("SUNRISE_SUNSETv2", "??");
                }
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }

    public static void getNearbyAirportsRyn(final Context context, String latitude, String longitude) {
        String url = "http://apigateway.ryanair.com/pub/v1/aggregate/3/common?" +
                "embedded=nearbyAirports" +
                "&latitude=" + latitude +
                "&longitude=" + longitude + "" +
                "&nearbyAirportsLimit=3" +
                "&apikey=" + context.getResources().getString(R.string.API_ryanair);

        final TextView tvIata1 = ((Activity) context).findViewById(R.id.tIata1);
        final TextView tvIata2 = ((Activity) context).findViewById(R.id.tIata2);
        final TextView tvIata3 = ((Activity) context).findViewById(R.id.tIata3);
        final TextView airportName1 = ((Activity) context).findViewById(R.id.tAirportName1);
        final TextView airportName2 = ((Activity) context).findViewById(R.id.tAirportName2);
        final TextView airportName3 = ((Activity) context).findViewById(R.id.tAirportName3);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray results = response.getJSONArray("nearbyAirports");

                            List<List<String>> airports = new ArrayList<>();
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject airport = results.getJSONObject(i);
                                List<String> airportData = new ArrayList<>();
                                airportData.add(airport.getString("iataCode"));
                                airportData.add(airport.getString("name"));

                                airports.add(airportData);
                            }

                            tvIata1.setText(airports.get(0).get(0));
                            airportName1.setText(airports.get(0).get(1));
                            tvIata2.setText(airports.get(1).get(0));
                            airportName2.setText(airports.get(1).get(1));
                            tvIata3.setText(airports.get(2).get(0));
                            airportName3.setText(airports.get(2).get(1));
                            String nearby = airports.get(0).get(0) + "%" + airports.get(0).get(1) + "_" + airports.get(1).get(0) + "%" + airports.get(1).get(1) + "_" + airports.get(2).get(0) + "%" + airports.get(2).get(1);
                            Global.setNearby(context, nearby);
                        } catch (Exception ex) {
                            try {
                                Log.e("NEARBY_ARIPORTS", ex.getMessage());

                            } catch (Exception exx) {
                                Log.e("NEARBY_ARIPORTSv2", exx.getMessage());
                            }

                            String nearby = Global.getNearby(context);
                            String[] airportInfo = nearby.split("_");
                            String[] a1 = airportInfo[0].split("%");
                            String[] a2 = airportInfo[1].split("%");
                            String[] a3 = airportInfo[2].split("%");

                            tvIata1.setText(a1[0]);
                            airportName1.setText(a1[1]);
                            tvIata2.setText(a2[0]);
                            airportName2.setText(a2[1]);
                            tvIata3.setText(a3[0]);
                            airportName3.setText(a3[1]);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("NEARBY_AIRPORTS", error.getMessage());
                    String nearby = Global.getNearby(context);
                    String[] airportInfo = nearby.split("_");
                    String[] a1 = airportInfo[0].split("%");
                    String[] a2 = airportInfo[1].split("%");
                    String[] a3 = airportInfo[2].split("%");

                    tvIata1.setText(a1[0]);
                    airportName1.setText(a1[1]);
                    tvIata2.setText(a2[0]);
                    airportName2.setText(a2[1]);
                    tvIata3.setText(a3[0]);
                    airportName3.setText(a3[1]);
                } catch (Exception e) {
                    Log.e("NEARBY_AIRPORTS v2", e.getMessage());
                }
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);

    }

    public static void fromCoordinatesToLocation(final Context context, String latitude, String longitude, final boolean search, final String... params) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?" +
                "latlng=" + latitude + "," + longitude +
                "&key=" + context.getResources().getString(R.string.API_google_geocoding);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (Objects.equals("OK", response.getString("status"))) {

                                JSONArray results = response.getJSONArray("results");
                                JSONObject resObj = results.getJSONObject(0);
                                final String address = resObj.getString("formatted_address");

                                if (search) {
                                    fancyShowCaseView = new FancyShowCaseView.Builder((Activity) context)
                                            .closeOnTouch(true)
                                            .customView(R.layout.activity_select_trip, new OnViewInflateListener() {
                                                @Override
                                                public void onViewInflated(View view) {
                                                    FloatingActionButton fabCloseFindTrip = view.findViewById(R.id.fabCloseFindTrip);
                                                    fabCloseFindTrip.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            fancyShowCaseView.hide();
                                                        }
                                                    });

                                                    final LottieAnimationView animationView1 = (LottieAnimationView) view.findViewById(R.id.anim_loading_trips);
                                                    animationView1.setImageAssetsFolder("assets");
                                                    animationView1.setAnimation("loading_trips.json");
                                                    animationView1.loop(true);  //for continuous looping
                                                    animationView1.playAnimation();//start animation

                                                    String departure = address;
                                                    String arrival = params[0];

                                                    TripFinder.findTrips(context, departure, arrival, params[1]);
                                                }
                                            }).closeOnTouch(false)
                                            .build();
                                    fancyShowCaseView.show();
                                } else {
                                    View viewContext = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
                                    EditText eFrom = viewContext.findViewById(R.id.etDeparture);
                                    eFrom.setText(address);
                                }

                            } else {
                                Toast.makeText(context, "Error converting the data", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception ex) {
                            Log.e("COORDINATES", ex.getMessage());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("COORDINATS", error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);

    }

    public static void fromLocaionToCoordinates(final Context context, String address, final boolean getImage, final String... params) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?" +
                "address=" + address +
                "&key=" + context.getResources().getString(R.string.API_google_geocoding);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (Objects.equals("OK", response.getString("status"))) {

                                JSONArray results = response.getJSONArray("results");
                                JSONObject locationObj = ((results.getJSONObject(0)).getJSONObject("geometry")).getJSONObject("location");

                                String latitude = locationObj.getString("lat");
                                String longitude = locationObj.getString("lng");

                                if (getImage) {
                                    fromCoordinatesToImageid(context, latitude, longitude, true);
                                }

                            } else {
                                Toast.makeText(context, "Error converting the data", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception ex) {
                            Log.e("LOCATION", ex.getMessage());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("LOCATION", error.getMessage());
                } catch (Exception e) {
                    Log.e("LOCATION", e.getMessage());

                }
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }

    private static void fromCoordinatesToImageid(final Context context, String latitude, String longitude, final boolean getImage) {
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
                "safe_search=3&" +
                "lat=" + latitude + "&" +
                "lon=" + longitude + "&" +
                "format=json&" +
                "nojsoncallback=1&" +
                "api_key=" + context.getString(R.string.API_flickr);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (Objects.equals("ok", response.getString("stat"))) {

                                JSONObject photosObj = response.getJSONObject("photos");

                                JSONArray photosId = photosObj.getJSONArray("photo");

                                int random = (int) (Math.random() * photosId.length() + 1);

                                JSONObject photoObj = photosId.getJSONObject(random);
                                String id = photoObj.getString("id");

                                if (getImage) {
                                    fromImageidToUrl(context, id);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }

    private static void fromImageidToUrl(final Context context, String id) {
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getSizes&" +
                "photo_id=" + id + "&" +
                "format=json&" +
                "nojsoncallback=1&" +
                "api_key=" + context.getString(R.string.API_flickr);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (Objects.equals("ok", response.getString("stat"))) {

                                JSONObject sizesObj = response.getJSONObject("sizes");

                                JSONArray sizes = sizesObj.getJSONArray("size");

                                String url = "https://www.noao.edu/image_gallery/images/d4/androy.jpg";


                                for (int i = 0; i < sizes.length(); i++) {
                                    if (Objects.equals("Large", sizes.getJSONObject(i).getString("label")))
                                        url = sizes.getJSONObject(i).getString("source");
                                }

                                ImageView imageView = ((Activity) context).findViewById(R.id.ivFlickrImage);
                                Picasso.with(context).load(url).placeholder(R.drawable.placeholder)
                                        .error(R.drawable.placeholder)
                                        .into(imageView, new com.squareup.picasso.Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {

                                            }
                                        });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FLICK_ERROR", error.getMessage());
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }


    public static void sendToken(final Context context) {

        String app_server_url = "https://projectsherlock.ddns.net/admin/fcm_insert.php"; //"https://projectsherlock.ddns.net/test/fcm_insert.php";

        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(context.getString(R.string.FCM_TOKEN), "");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TOKEN_STATUS", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Log.e("TOKEN_ERROR", error.getMessage());
                        } catch (Exception e) {
                            Log.e("TOKEN_ERRORv2", e.getMessage() + " It may be caused by an internal server error (500)");
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", Global.getIdentifier(context));
                params.put("fcm_token", token);
                params.put("uid", Global.returnUid(context));

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);
    }

    public static void sendRequest(final Context context, String iataDeparture, String iataArrival) {
        String url = context.getString(R.string.serverURL) + "/request/addv2.php";

        Map<String, String> params = new HashMap<>();
        params.put("id_user", Global.returnUid(context));
        params.put("iata_departure", iataDeparture);
        params.put("iata_arrival", iataArrival);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (Objects.equals(response.getString("success"), "true")) {
                                bObserve.setText("OBSERVATION STARTED!");
                            } else {
                                bObserve.setText("ROUTE NOT FOUND, TRY ANOTHER ONE");
                                Log.e("REQUEST", response.getString("message"));
                            }
                        } catch (JSONException e) {
                            bObserve.setText("TRY AGAIN!");
                            Log.e("REQUEST_EXCEPTION", e.getMessage());
                        } finally {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    bObserve.setText("OBSERVE");
                                }
                            }, 1500);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bObserve.setText("UNABLE TO SOLVE, TRY ANOTHER ONE");
                try {
                    Log.e("REQUESTv1", error.getMessage());
                } catch (Exception e) {
                    Log.e("REQUESTv2", e.getMessage());
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bObserve.setText("OBSERVE");
                    }
                }, 1500);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }

    public static void getRoutes(final Context context) {
        String url = context.getString(R.string.serverURL) + "/util/getroutes.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        routes = new HashMap<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String key = jsonObject.keys().next();
                                String value = jsonObject.getString(key);
                                if (routes != null && routes.containsKey(key))
                                    routes.get(key).add(value);
                                else
                                    routes.put(key, new ArrayList<String>(Collections.singletonList(value)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }


    public static List<String> getLatitudeLongitude(Context context) {
        String latitude = "0";
        String longitude = "0";
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Global.buildAlertMessageNoGps(context);
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            String res = Global.getLocation(context, locationManager);
            latitude = res.split(",")[0];
            longitude = res.split(",")[1];
        }
        final String resLatitude = latitude;
        final String resLongitude = longitude;
        return new ArrayList<String>() {{
            add(resLatitude);
            add(resLongitude);
        }};
    }

}
