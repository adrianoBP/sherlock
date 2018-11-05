package prjs.adriano.com.sherlock.Activities;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;
import java.util.List;

import prjs.adriano.com.sherlock.Classes.Trip;
import prjs.adriano.com.sherlock.Classes.hop.Hop;
import prjs.adriano.com.sherlock.Classes.hop.Transit;
import prjs.adriano.com.sherlock.DBH.DBHTrip;
import prjs.adriano.com.sherlock.R;

import static prjs.adriano.com.sherlock.Requests.UtilHelper.getLatitudeLongitude;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String tripID;


    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;
    public static final PatternItem DOT = new Dot();
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tripID = getIntent().getStringExtra("id");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        DBHTrip dbhTrip = new DBHTrip(this);
        Trip trip = dbhTrip.getTrip(tripID);

        for (Hop h : trip.getHops()) {
            if (h.getStartPoint() != null) {
                PolylineOptions polylineOptions = new PolylineOptions().add(h.getStartPoint()).add(h.getEndPoint()).width(5).color(Color.RED);
                if (h instanceof Transit) {
                    polylineOptions.pattern(PATTERN_POLYGON_ALPHA);
                    if (((Transit) h).getVehicle().getColor() != null) {
                        if (((Transit) h).getVehicle().getColor().length() == 7) {
                            Integer c = Color.parseColor(((Transit) h).getVehicle().getColor());
                            polylineOptions.color(c);
                        } else {
                            polylineOptions.color(Color.parseColor("#ffa000"));
                        }
                    }
                } else {
                    polylineOptions.color(Color.parseColor("#00897b"));
                }
                mMap.addPolyline(polylineOptions);
            }
        }
        if (trip.getHops().get(0).getStartPoint() != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trip.getHops().get(0).getStartPoint(), 15));
            mMap.addMarker(new MarkerOptions()
                    .position(trip.getHops().get(0).getStartPoint())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_point_a)));
        }
        if (trip.getHops().get(trip.getHops().size()-1).getEndPoint() != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(trip.getHops().get(trip.getHops().size()-1).getEndPoint())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_point_b)));
        }

        List<String> pos = getLatitudeLongitude(this);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.valueOf(pos.get(0)), Double.valueOf(pos.get(1))))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location)));

    }
}
