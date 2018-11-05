package prjs.adriano.com.sherlock.tabs;

/*
  Created by Adriano on 21/01/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.santalu.maskedittext.MaskEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.OnViewInflateListener;
import prjs.adriano.com.sherlock.Classes.TripFinder;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.Requests.UtilHelper;

import static android.app.Activity.RESULT_OK;
import static prjs.adriano.com.sherlock.Activities.MainActivity.appBarLayout;
import static prjs.adriano.com.sherlock.Activities.MainActivity.fancyShowCaseView;
import static prjs.adriano.com.sherlock.Requests.UtilHelper.getLatitudeLongitude;


public class Tab2Home extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    //COMPONENTS
    Button btnDatePicker, btnTimePicker, bInsert;
    View rootView;
    MaskEditText etDepartureDate;
    EditText etDeparture, etArrival;
    FloatingActionButton fabSwap;


    //VALUES
    String date = "";
    boolean depDate = true;
    boolean depPlace = true;
    private final int REQUEST_CODE_PLACEPICKER = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab2home, container, false);

        //COMPONENTS INITIALIZATION
        etDepartureDate  = rootView.findViewById(R.id.etDepartureDate);
        etDeparture = rootView.findViewById(R.id.etDeparture);
        etArrival = rootView.findViewById(R.id.etArrival);
        bInsert = rootView.findViewById(R.id.bInsert);
        fabSwap = rootView.findViewById(R.id.fabSwap);


        ///OTHER
        List<String> latitudeLongitude = new ArrayList<>(getLatitudeLongitude(getContext()));
        UtilHelper.fromCoordinatesToLocation(getContext(), latitudeLongitude.get(0), latitudeLongitude.get(1), false); //TODO remove comment (API usage)


        //region LISTENERS
        etDeparture.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    depPlace = true;
                    startPlacePickerActivity();
                }
            }
        });
        etDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                depPlace = true;
                startPlacePickerActivity();
            }
        });
        etArrival.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    depPlace = false;
                    startPlacePickerActivity();
                }
            }
        });
        etArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                depPlace = false;
                startPlacePickerActivity();
            }
        });
        etDepartureDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    pickDateTime();
                }
            }
        });
        etDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDateTime();
            }
        });
        fabSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = etArrival.getText().toString();
                etArrival.setText(etDeparture.getText().toString());
                etDeparture.setText(temp);
                appBarLayout.setExpanded(true);
            }
        });
        bInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(1000);
                view.startAnimation(animation1);

                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String inputDateStr=etDepartureDate.getText().toString();
                Date date = null;
                try {
                    date = inputFormat.parse(inputDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputDateStr = outputFormat.format(date);

                Timestamp timestamp = Timestamp.valueOf(outputDateStr);
                final long sec = timestamp.getTime()/1000;

                fancyShowCaseView = new FancyShowCaseView.Builder((Activity)getContext())
                        .focusOn(view)
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
                                appBarLayout.setExpanded(true);
                                String departure = etDeparture.getText().toString();
                                String arrival = etArrival.getText().toString();

                                TripFinder.findTrips(getContext(), departure, arrival, String.valueOf(sec));
                            }
                        }).closeOnTouch(false)
                        .build();
                fancyShowCaseView.show();

            }
        });
        //endregion

        UtilHelper.sendToken(getContext());

        startComponents();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void startPlacePickerActivity(){
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

        try {
            Intent intent = intentBuilder.build(getActivity());
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
            ((Activity) getContext()).overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_PLACEPICKER && resultCode== RESULT_OK)
            displaySelectedPlaceFromPlacePicker(data);
    }


    private void displaySelectedPlaceFromPlacePicker(Intent data){
        Place placeSelected = PlacePicker.getPlace(data, getActivity());

        String name = placeSelected.getName().toString();
        String address= placeSelected.getAddress().toString();
        appBarLayout.setExpanded(true);
        if(depPlace)
            etDeparture.setText(address);
        else
            etArrival.setText(address);
        appBarLayout.setExpanded(true);

    }
    void pickDateTime(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(Tab2Home.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePicker");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(Tab2Home.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true);
        timePickerDialog.show(getActivity().getFragmentManager(), "DatePicker");
        date = String.format("%02d%02d%02d",dayOfMonth, monthOfYear+1, year );

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        date = String.format(date+" %02d%02d", hourOfDay, minute);
        etDepartureDate.setText(date);
        appBarLayout.setExpanded(true);

    }

    void startComponents(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String finalDate = dateFormat.format(currentTime);
        etDepartureDate.setText(finalDate);
        appBarLayout.setExpanded(true);

    }
}
