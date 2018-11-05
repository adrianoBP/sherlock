package prjs.adriano.com.sherlock.tabs;

/*
  Created by Adriano on 21/01/2018.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prjs.adriano.com.sherlock.Adapters.SpinnerAdapterAirports;
import prjs.adriano.com.sherlock.Adapters.SpinnerAdapterCurrencies;
import prjs.adriano.com.sherlock.Classes.Airport;
import prjs.adriano.com.sherlock.Classes.Currency;
import prjs.adriano.com.sherlock.DBH.DBHUtil;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.Requests.UtilHelper;

import static prjs.adriano.com.sherlock.Activities.MainActivity.appBarLayout;
import static prjs.adriano.com.sherlock.Activities.MainActivity.bObserve;
import static prjs.adriano.com.sherlock.Requests.UtilHelper.getLatitudeLongitude;


public class Tab3Utility extends Fragment {

    View rootView;
    DBHUtil dbhUtil;

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab3utility, container, false);


        dbhUtil = new DBHUtil(getContext());
        fillSpinners(rootView);
        FloatingActionButton fabSwapCurrencies = rootView.findViewById(R.id.fabSwapCurrencies);
        EditText etCurrency1 = rootView.findViewById(R.id.etCurrency1);
        EditText etCurrency2 = rootView.findViewById(R.id.etCurrency2);
        final Spinner sCurrencies1 = rootView.findViewById(R.id.sCurrencies1);
        final Spinner sCurrencies2 = rootView.findViewById(R.id.sCurrencies2);
        ConstraintLayout cvSunrise = rootView.findViewById(R.id.cvSunrise);
        ConstraintLayout cvSunset = rootView.findViewById(R.id.cvSunset);
        Button bFindAirportRoute = rootView.findViewById(R.id.bFindAirpotRoute);
        final EditText etIataAirport = rootView.findViewById(R.id.etIataAirport);
        final TextView tvIata1 = rootView.findViewById(R.id.tIata1);
        etCurrency2.setEnabled(false);
        bObserve = rootView.findViewById(R.id.bObserve);
        final Spinner sAirport1 = rootView.findViewById(R.id.sAirports1);
        final Spinner sAirport2 = rootView.findViewById(R.id.sAirports2);


        //region LISTENERS
        etIataAirport.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                appBarLayout.setExpanded(true);
            }
        });
        fabSwapCurrencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer index = null;
                index = sCurrencies1.getSelectedItemPosition();
                sCurrencies1.setSelection(sCurrencies2.getSelectedItemPosition());
                sCurrencies2.setSelection(index);
            }
        });
        sCurrencies1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calculateConversion(rootView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sCurrencies1.setSelection(35);
                sCurrencies2.setSelection(120);
                calculateConversion(rootView);
            }
        });
        sCurrencies2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calculateConversion(rootView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sCurrencies1.setSelection(35);
                sCurrencies2.setSelection(120);
                calculateConversion(rootView);
            }
        });
        etCurrency1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                calculateConversion(rootView);
                return false;
            }
        });

        cvSunrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUtilInfo();
            }
        });
        cvSunset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUtilInfo();

            }
        });


        bFindAirportRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(400);
                view.startAnimation(animation1);
                if (etIataAirport.getText().toString().length() == 3) {
                    String[] params = {etIataAirport.getText().toString() + " airport", String.valueOf(System.currentTimeMillis() / 1000)};
                    List<String> latitudeLongitude = new ArrayList<>(getLatitudeLongitude(getContext()));
                    UtilHelper.fromCoordinatesToLocation(getContext(), latitudeLongitude.get(0), latitudeLongitude.get(1), true, params);
                } else {
                    Toast.makeText(getContext(), "Field code required", Toast.LENGTH_SHORT).show();
                }

            }
        });

        bObserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(400);
                view.startAnimation(animation1);
                final TextView tvIata1 = sAirport1.getSelectedView().findViewById(R.id.tvIata);
                final TextView tvIata2 = sAirport2.getSelectedView().findViewById(R.id.tvIata);
                UtilHelper.sendRequest(getContext(), tvIata1.getText().toString(), tvIata2.getText().toString());

            }
        });

        //endregion


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getUtilInfo();
    }

    public void getUtilInfo() {
        List<String> latitudeLongitude = new ArrayList<>(getLatitudeLongitude(getContext()));
        UtilHelper.getSunriseSunset(rootView.getContext(), latitudeLongitude.get(0), latitudeLongitude.get(1)); //TODO remove comment (API usage)
        UtilHelper.getNearbyAirportsRyn(rootView.getContext(), latitudeLongitude.get(0), latitudeLongitude.get(1)); //TODO remove comment (API usage)
    }

    public static Map<String, List<String>> routes = new HashMap<>();

    public static Spinner sAirports1;
    public static Spinner sAirports2;

    public void fillSpinners(View view) {
        DBHUtil dbhUtil = new DBHUtil(((Activity) getContext()));

        //spinner currency
        List<Currency> currencies = new ArrayList<>(dbhUtil.getCurrencies());

        SpinnerAdapterCurrencies adapterCurrencies = new SpinnerAdapterCurrencies(currencies, (Activity) view.getContext());

        Spinner sCurrencies1 = view.findViewById(R.id.sCurrencies1);
        Spinner sCurrencies2 = view.findViewById(R.id.sCurrencies2);

        sCurrencies1.setAdapter(adapterCurrencies);
        sCurrencies2.setAdapter(adapterCurrencies);

        sCurrencies1.setSelection(35);
        sCurrencies2.setSelection(120);

        //spinner airports
        List<Airport> airports = new ArrayList<>(dbhUtil.getAirports());

        SpinnerAdapterAirports adapterAirports = new SpinnerAdapterAirports(airports, (Activity) view.getContext());

        sAirports1 = view.findViewById(R.id.sAirports1);
        sAirports2 = view.findViewById(R.id.sAirports2);

        sAirports1.setAdapter(adapterAirports);
        sAirports2.setAdapter(adapterAirports);

        sAirports1.setSelection(1);
        sAirports2.setSelection(2);


    }

    public void calculateConversion(View view) {
        DBHUtil dbhUtil = new DBHUtil(view.getContext());

        Spinner sCurrencies1 = view.findViewById(R.id.sCurrencies1);
        Spinner sCurrencies2 = view.findViewById(R.id.sCurrencies2);

        EditText etCurrency1 = view.findViewById(R.id.etCurrency1);
        EditText etCurrency2 = view.findViewById(R.id.etCurrency2);

        TextView tvCode1 = sCurrencies1.getSelectedView().findViewById(R.id.tvCode);
        TextView tvCode2 = sCurrencies2.getSelectedView().findViewById(R.id.tvCode);

        String code1 = tvCode1.getText().toString();
        String code2 = tvCode2.getText().toString();

        Currency currency1 = dbhUtil.getCurrencyFromCode(code1);
        Currency currency2 = dbhUtil.getCurrencyFromCode(code2);

        double any2Eur = 1 / Double.valueOf(currency1.getValue());

        double multiplier;

        try {
            multiplier = Double.valueOf(etCurrency1.getText().toString());
        } catch (Exception ex) {
            multiplier = 0;
        }

        etCurrency2.setText(
                String.valueOf(
                        multiplier * any2Eur * Double.valueOf(currency2.getValue())
                )
        );

    }


}
