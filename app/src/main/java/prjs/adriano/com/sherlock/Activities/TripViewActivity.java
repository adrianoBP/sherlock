package prjs.adriano.com.sherlock.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.OnViewInflateListener;
import prjs.adriano.com.sherlock.Classes.Trip;
import prjs.adriano.com.sherlock.Classes.hop.Hop;
import prjs.adriano.com.sherlock.Classes.hop.Transit;
import prjs.adriano.com.sherlock.Classes.hop.Vehicle;
import prjs.adriano.com.sherlock.Classes.hop.Walk;
import prjs.adriano.com.sherlock.DBH.DBHTrip;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.Requests.UtilHelper;

import static prjs.adriano.com.sherlock.Activities.MainActivity.fancyShowCaseView;

public class TripViewActivity extends AppCompatActivity {

    DBHTrip dbhTrip;
    TextView tvFrom, tvTo, tvDepartureDate, tvArrivalDate, tvDepartureTime, tvArrivalTime;
    Trip trip = new Trip();
    ListView lvHops;
    final boolean[] canMessage = {true};
    FloatingActionButton fMap;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_view_card);


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarCard);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        Explode explode = new Explode();
        explode.excludeTarget(R.id.toolbar, true);
        getWindow().setEnterTransition(explode);

        dbhTrip = new DBHTrip(this);
        tvFrom = findViewById(R.id.tvFromView);
        tvTo = findViewById(R.id.tvToView);
        tvDepartureDate = findViewById(R.id.tvDepartureView);
        tvArrivalDate = findViewById(R.id.tvArrivalView);
        tvDepartureTime = findViewById(R.id.tvDepartureViewTime);
        tvArrivalTime = findViewById(R.id.tvArrivalViewTime);
        fMap = findViewById(R.id.fMap);

        final String id = getIntent().getStringExtra("EXTRA_ID");

        fMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripViewActivity.this, MapsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });


        trip = new Trip(dbhTrip.getTrip(id));

        if(Global.returnDownloadImageStatus(this))
            UtilHelper.fromLocaionToCoordinates(this, trip.getArrival_location(), true); //TODO remove comment (api usage)

        tvFrom.setText(trip.getDeparture_location());
        tvTo.setText(trip.getArrival_location());
        tvDepartureDate.setText(Global.convertToDate(String.valueOf(trip.getDeparture_time())));
        tvArrivalDate.setText(Global.convertToDate(String.valueOf(trip.getArrival_time())));
        tvDepartureTime.setText(Global.convertToTime(String.valueOf(trip.getDeparture_time())));
        tvArrivalTime.setText(Global.convertToTime(String.valueOf(trip.getArrival_time())));

        LinearLayout linearLayout = findViewById(R.id.llTripInfo);

        View hopStart = getLayoutInflater().inflate(R.layout.custom_adapter_hop_view_start, null);
        TextView textViewStart = hopStart.findViewById(R.id.tvCustomInstruction);
        textViewStart.setText(trip.getDeparture_location());
        linearLayout.addView(hopStart);

        for (final Hop hop : trip.getHops()) {
            View hopView = getLayoutInflater().inflate(R.layout.custom_adapter_hop_view, null);

            TextView tvCustomInstruction = hopView.findViewById(R.id.tvCustomInstruction);
            if (trip.getHops().size() > 0) {
                tvCustomInstruction.setText(hop.getInstruction());
            }


            ConstraintLayout clHop = hopView.findViewById(R.id.clHop);
            clHop.setOnClickListener(
                    new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                    animation1.setDuration(400);
                    view.startAnimation(animation1);
                    if (!(hop instanceof Walk)) {
                        canMessage[0] = false;
                        fancyShowCaseView = new FancyShowCaseView.Builder(TripViewActivity.this)
                                .closeOnTouch(true)
                                .customView(R.layout.activity_transit_info, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(View view) {


                                        TextView tvHeadsign, tvName, tvShortName, tvAgency, tvTime, tvArrivingTime, tvDepartureStop, tvArrivalStop;
                                        CircleImageView iv = view.findViewById(R.id.ivTypeColor);
                                        tvHeadsign = view.findViewById(R.id.tvHeadsign);
                                        tvShortName = view.findViewById(R.id.tvShortName);
                                        tvDepartureStop = view.findViewById(R.id.tvDepartureStop);
                                        tvArrivalStop = view.findViewById(R.id.tvArrivalStop);
                                        tvAgency = view.findViewById(R.id.tvAgency);
                                        tvTime = view.findViewById(R.id.tvTime);
                                        tvArrivingTime = view.findViewById(R.id.tvArrivingTime);

                                        Vehicle vehicle = dbhTrip.getVehicle(String.valueOf(((Transit) hop).getId()));

                                        tvHeadsign.setText(vehicle.getHeadSign());
                                        tvShortName.setText(vehicle.getShortName());
                                        tvDepartureStop.setText(((Transit) hop).getDepartureStop());
                                        tvArrivalStop.setText(((Transit) hop).getArrivalStop());
                                        tvTime.setText(Global.convertTime(String.valueOf(((Transit) hop).getDepartureTime())));
                                        tvArrivingTime.setText(Global.convertTime(String.valueOf(((Transit) hop).getArrivalTime())));

                                        String agencyShownText;
                                        if (!TextUtils.isEmpty(vehicle.getAgencyName())) {
                                            agencyShownText = vehicle.getAgencyName();
                                        } else if (!TextUtils.isEmpty(vehicle.getAgencyUrl())) {
                                            agencyShownText = vehicle.getAgencyUrl();
                                        } else {
                                            agencyShownText = "Agency info not aviable";
                                        }
                                        SpannableString agency = new SpannableString(agencyShownText);
                                        agency.setSpan(new UnderlineSpan(), 0, agency.length(), 0);
                                        tvAgency.setText(agency);
                                        final String[] url = {vehicle.getAgencyUrl()};
                                        tvAgency.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (!url[0].startsWith("http://") && !url[0].startsWith("https://"))
                                                    url[0] = "http://" + url[0];
                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url[0]));
                                                startActivity(browserIntent);
                                                TripViewActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                                            }
                                        });
                                        try {
                                            if (!Objects.equals(vehicle.getColor(), "null") && !TextUtils.isEmpty(vehicle.getColor())) {
                                                iv.setCircleBackgroundColor(Color.parseColor(vehicle.getColor()));

                                                int color = (int)Long.parseLong(vehicle.getColor().substring(1), 16);
                                                int r = (color >> 16) & 0xFF;
                                                int g = (color >> 8) & 0xFF;
                                                int b = (color) & 0xFF;
                                                int finalColor = Integer.parseInt(r+""+g+""+b);
                                                if(Global.isColorDark(finalColor)){
                                                    iv.setColorFilter(Color.WHITE);
                                                }
                                            }
                                            switch (vehicle.getType()) {
                                                case "Train":
                                                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_train, getApplicationContext().getTheme()));
                                                    break;
                                                case "High speed train":
                                                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_highspeedtrain, getApplicationContext().getTheme()));
                                                    break;
                                                case "Long distance train":
                                                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_longdistancetrain, getApplicationContext().getTheme()));
                                                    break;
                                                case "Bus":
                                                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_bus, getApplicationContext().getTheme()));
                                                    break;
                                                case "Ferry":
                                                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_ferry, getApplicationContext().getTheme()));
                                                    break;
                                                case "Subway":
                                                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_subway, getApplicationContext().getTheme()));
                                                    break;
                                                case "Commuter train":
                                                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_train, getApplicationContext().getTheme()));
                                                    break;
                                                default:
                                                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_shuttle, getApplicationContext().getTheme()));
                                            }



                                        } catch (Exception ex) {
                                            Log.e("VEHICLE", ex.getMessage());
                                        }


                                    }
                                }).closeOnTouch(false)
                                .build();
                        fancyShowCaseView.show();

                    } else if (canMessage[0]) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Walk for " + hop.getDistance_value() + "m ( " + hop.getDuration_value() / 60 + " mintes)", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                        TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        snackbar.setActionTextColor(getResources().getColor(R.color.secondary_light));
                        snackbar.setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });

                        snackbar.show();
                    }


                }
            });


            linearLayout.addView(hopView);
        }

        View hopEnd = getLayoutInflater().inflate(R.layout.custom_adapter_hop_view_end, null);
        TextView textViewEnd = hopEnd.findViewById(R.id.tvCustomInstruction);
        textViewEnd.setText(trip.getArrival_location());
        linearLayout.addView(hopEnd);
    }

    @Override
    public void onBackPressed() {
        try {
            if (fancyShowCaseView.isShowing()) {
                fancyShowCaseView.hide();
                canMessage[0] = true;
            } else {
                finish();
                TripViewActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        } catch (Exception ex) {
            finish();
            TripViewActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }

    }
}
