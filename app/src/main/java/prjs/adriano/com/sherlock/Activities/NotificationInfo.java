package prjs.adriano.com.sherlock.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.splashes.StartupSplash;

public class NotificationInfo extends AppCompatActivity {

    TextView tvDeparture, tvArrival, tvAvarage, tvMaxPrice, tvMinPrice, tvAdvice, tvTimeCheapest, tvTimeExpansive, tvDateCheapest, tvDateExpansive, tvPriceCheapest, tvPriceExpansive, tvFetchCheapest, tvFetchExpansive;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NotificationInfo.this, StartupSplash.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_infov2);

        tvDeparture = findViewById(R.id.tvDepartureN);
        tvArrival = findViewById(R.id.tvArrivalN);
        tvAvarage = findViewById(R.id.tvAvgPrice);
        tvMaxPrice = findViewById(R.id.tvMaxPrice);
        tvMinPrice = findViewById(R.id.tvMinPrice);
        tvAdvice = findViewById(R.id.tvAdvice);
        tvTimeCheapest = findViewById(R.id.tvTimeCheapest);
        tvTimeExpansive = findViewById(R.id.tvTimeExpansive);
        tvDateCheapest = findViewById(R.id.tvDateCheapest);
        tvDateExpansive = findViewById(R.id.tvDateExpansive);
        tvPriceCheapest = findViewById(R.id.tvPriceCheapest);
        tvPriceExpansive = findViewById(R.id.tvPriceExpansive);
        tvFetchCheapest = findViewById(R.id.tvFetchCheapest);
        tvFetchExpansive = findViewById(R.id.tvFetchExpansive);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNotification);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.fontBright), PorterDuff.Mode.SRC_ATOP);

        ImageView ivNotification = findViewById(R.id.ivNotification);
        Random rng = new Random();
        int rnd = rng.nextInt(4) + 1;
        String name = "bgn" + rnd;
        int resource = getResources().getIdentifier(name, "drawable", getPackageName());
        ivNotification.setImageDrawable(getResources().getDrawable(resource));

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
                Intent intent = new Intent(NotificationInfo.this, StartupSplash.class);
                startActivity(intent);
                finish();
            } else {
                newString = extras.getString("DATA");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("DATA");
        }
        JSONObject dataOBJ = null;
        try {
            dataOBJ = new JSONObject(newString);
        } catch (JSONException e) {
            Log.e("JSON_ERROR_NOTIFICATION", e.getMessage());
            showSnack("Error reading the data!");
        }

        if (dataOBJ == null) {
            showSnack("Error reading the data!");
        } else {
            try {
                switch (dataOBJ.getInt("status")) {
                    case 200:
                        Integer id, tracks, counterMorning, counterAfternoon, counterEvening, counterNight, historicHighId, historicLowId;
                        Double day_0 = null, day_1 = null, day_2 = null, day_3 = null, day_4 = null, day_5 = null, day_6 = null, priceMorning = null, priceAfternoon = null, priceEvening = null, priceNight = null, avgPrice, historicHighValue, historicLowValue;
                        String departureIATA, departureName, departureDatetime, arrivalIATA, arrivalName, arrivalDatetime, historicHighDepartureDatetime, historicHighArrivalDatetime, historicHighFetchDate, historicLowDepartureDatetime, historicLowArrivalDatetime, historicLowFetchDate;

                        //ID
                        id = dataOBJ.getInt("id");
                        //TRACKS
                        tracks = dataOBJ.getInt("tracks");
                        //DEPARTURE
                        JSONObject departure = dataOBJ.getJSONObject("departure");
                        departureIATA = departure.getString("iata");
                        departureName = departure.getString("name");
                        departureDatetime = departure.getString("datetime");
                        //ARRIVAL
                        JSONObject arrival = dataOBJ.getJSONObject("arrival");
                        arrivalIATA = arrival.getString("iata");
                        arrivalName = arrival.getString("name");
                        arrivalDatetime = arrival.getString("datetime");
                        //INFO
                        JSONObject infoObj = dataOBJ.getJSONObject("info");
                        //INFO AVG_WEEKDAY
                        JSONObject infAvg_weekday = infoObj.getJSONObject("avg_weekday");
                        if (infAvg_weekday.has("day_0"))
                            day_0 = infAvg_weekday.getDouble("day_0");
                        if (infAvg_weekday.has("day_1"))
                            day_1 = infAvg_weekday.getDouble("day_1");
                        if (infAvg_weekday.has("day_2"))
                            day_2 = infAvg_weekday.getDouble("day_2");
                        if (infAvg_weekday.has("day_3"))
                            day_3 = infAvg_weekday.getDouble("day_3");
                        if (infAvg_weekday.has("day_4"))
                            day_4 = infAvg_weekday.getDouble("day_4");
                        if (infAvg_weekday.has("day_5"))
                            day_5 = infAvg_weekday.getDouble("day_5");
                        if (infAvg_weekday.has("day_6"))
                            day_6 = infAvg_weekday.getDouble("day_6");
                        //INFO TIMEZONE
                        JSONObject timezoneObj = infoObj.getJSONObject("timezone");
                        //INFO TIMEZONE PRICE
                        JSONObject timezonePriceObj = timezoneObj.getJSONObject("price");
                        if (timezonePriceObj.has("morning"))
                            priceMorning = timezonePriceObj.getDouble("morning");
                        if (timezonePriceObj.has("afternoon"))
                            priceAfternoon = timezonePriceObj.getDouble("afternoon");
                        if (timezonePriceObj.has("evening"))
                            priceEvening = timezonePriceObj.getDouble("evening");
                        if (timezonePriceObj.has("night"))
                            priceNight = timezonePriceObj.getDouble("night");
                        //INFO TIMEZONE COUNTER
                        JSONObject timezoneCounterObj = timezoneObj.getJSONObject("counter");
                        counterMorning = timezoneCounterObj.getInt("morning");
                        counterAfternoon = timezoneCounterObj.getInt("afternoon");
                        counterEvening = timezoneCounterObj.getInt("evening");
                        counterNight = timezoneCounterObj.getInt("night");
                        //INFO AVG_PRICE
                        avgPrice = infoObj.getDouble("avg_price");
                        //INFO HISTORIC_HIGH
                        JSONObject infoHistoricHighObj = infoObj.getJSONObject("historic_high");
                        historicHighValue = infoHistoricHighObj.getDouble("value");
                        //INFO HISTORIC_HIGH TRACK
                        JSONObject infoHistoricHighTrackObj = infoHistoricHighObj.getJSONObject("track");
                        historicHighId = infoHistoricHighTrackObj.getInt("id");
                        historicHighDepartureDatetime = infoHistoricHighTrackObj.getString("departure_datetime");
                        historicHighArrivalDatetime = infoHistoricHighTrackObj.getString("arrival_datetime");
                        historicHighFetchDate = infoHistoricHighTrackObj.getString("fetch_date");
                        //INFO HISTORIC_LOW
                        JSONObject infoHistoricLowObj = infoObj.getJSONObject("historic_low");
                        historicLowValue = infoHistoricLowObj.getDouble("value");
                        //INFO HISTORIC_LOW TRACK
                        JSONObject infoHistoricLowTrackObj = infoHistoricLowObj.getJSONObject("track");
                        historicLowId = infoHistoricLowTrackObj.getInt("id");
                        historicLowDepartureDatetime = infoHistoricLowTrackObj.getString("departure_datetime");
                        historicLowArrivalDatetime = infoHistoricLowTrackObj.getString("arrival_datetime");
                        historicLowFetchDate = infoHistoricLowTrackObj.getString("fetch_date");


                        DecimalFormat df2 = new DecimalFormat(".##");

                        //START FILL
                        getSupportActionBar().setTitle(departureIATA + " - " + arrivalIATA);
                        tvDeparture.setText(departureName);
                        tvArrival.setText(arrivalName);
                        tvAvarage.setText(df2.format(avgPrice));
                        tvMaxPrice.setText(df2.format(historicHighValue));
                        tvMinPrice.setText(df2.format(historicLowValue));
                        List<Double> doublesPrice = new ArrayList<>();
                        if (day_0 != null)
                            doublesPrice.add(day_0);
                        if (day_1 != null)
                            doublesPrice.add(day_1);
                        if (day_2 != null)
                            doublesPrice.add(day_2);
                        if (day_3 != null)
                            doublesPrice.add(day_3);
                        if (day_4 != null)
                            doublesPrice.add(day_4);
                        if (day_5 != null)
                            doublesPrice.add(day_5);
                        if (day_6 != null)
                            doublesPrice.add(day_6);
                        String dayHigh = "";
                        String dayLow = "";
                        double tmpPriceHigh = Collections.max(doublesPrice);
                        double tmpPriceLow = Collections.min(doublesPrice);

                        if (day_0 == tmpPriceHigh)
                            dayHigh = "Monday";
                        else if (day_1 == tmpPriceHigh)
                            dayHigh = "Tuesday";
                        else if (day_2 == tmpPriceHigh)
                            dayHigh = "Wednesday";
                        else if (day_3 == tmpPriceHigh)
                            dayHigh = "Thrusday";
                        else if (day_4 == tmpPriceHigh)
                            dayHigh = "Friday";
                        else if (day_5 == tmpPriceHigh)
                            dayHigh = "Saturday";
                        else if (day_6 == tmpPriceHigh)
                            dayHigh = "Sunday";

                        if (day_0 != null && day_0 == tmpPriceLow)
                            dayLow = "Monday";
                        else if (day_1 != null && day_1 == tmpPriceLow)
                            dayLow = "Tuesday";
                        else if (day_2 != null && day_2 == tmpPriceLow)
                            dayLow = "Wednesday";
                        else if (day_3 != null && day_3 == tmpPriceLow)
                            dayLow = "Thrusday";
                        else if (day_4 != null && day_4 == tmpPriceLow)
                            dayLow = "Friday";
                        else if (day_5 != null && day_5 == tmpPriceLow)
                            dayLow = "Saturday";
                        else if (day_6 != null && day_6 == tmpPriceLow)
                            dayLow = "Sunday";


                        List<Double> doublesZones = new ArrayList<>();
                        if (priceMorning != null && priceMorning != 0)
                            doublesZones.add(priceMorning);
                        if (priceAfternoon != null && priceAfternoon != 0)
                            doublesZones.add(priceAfternoon);
                        if (priceEvening != null && priceEvening != 0)
                            doublesZones.add(priceEvening);
                        if (priceNight != null && priceNight != 0)
                            doublesZones.add(priceNight);

                        String timezoneLow = "";
                        double tmpZoneLow = Collections.min(doublesZones);
                        if (priceMorning != null && tmpZoneLow == priceMorning)
                            timezoneLow = "morning";
                        else if (priceAfternoon != null && tmpZoneLow == priceAfternoon)
                            timezoneLow = "afternoon";
                        else if (priceEvening != null && tmpZoneLow == priceEvening)
                            timezoneLow = "evening";
                        else if (priceNight != null && tmpZoneLow == priceNight)
                            timezoneLow = "night";

                        String one = "According to our data, you shouldn't travel this route on ";
                        String n1 = "<font color='#f44336'>" + dayHigh + "</font>s, instead opt for ";
                        String n2 = "<font color='#8bc34a'>" + dayLow + "</font>s.\nAlso you might want to make a trip in the ";
                        String n3 = "<font color='#8bc34a'>" + timezoneLow + "</font>  to save some extra money!";

                        tvAdvice.setText(Html.fromHtml(one + n1 + n2 + n3));

                        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        DateFormat originalFormatFetch = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat targetTime = new SimpleDateFormat("HH:mm");
                        DateFormat targetDate = new SimpleDateFormat("dd/MM/yyyy");


                        tvTimeCheapest.setText(targetTime.format(originalFormat.parse(historicLowDepartureDatetime)));
                        tvTimeExpansive.setText(targetTime.format(originalFormat.parse(historicHighDepartureDatetime)));
                        tvDateCheapest.setText(targetDate.format(originalFormat.parse(historicLowDepartureDatetime)));
                        tvDateExpansive.setText(targetDate.format(originalFormat.parse(historicHighDepartureDatetime)));
                        tvPriceCheapest.setText(String.valueOf(historicLowValue));
                        tvPriceExpansive.setText(String.valueOf(historicHighValue));
                        tvFetchCheapest.setText(targetDate.format(originalFormatFetch.parse(historicLowFetchDate)));
                        tvFetchExpansive.setText(targetDate.format(originalFormatFetch.parse(historicHighFetchDate)));


                        ArrayList<BarData> dataList = new ArrayList<>();

                        BarData data;

                        if (day_0 != null) {
                            data = new BarData("Mon", Float.valueOf(df2.format(day_0)), df2.format(day_0) + "€");
                            dataList.add(data);
                        }
                        if (day_1 != null) {
                            data = new BarData("Tue", Float.valueOf(df2.format(day_1)), df2.format(day_1) + "€");
                            dataList.add(data);
                        }
                        if (day_2 != null) {
                            data = new BarData("Wed", Float.valueOf(df2.format(day_2)), df2.format(day_2) + "€");
                            dataList.add(data);
                        }
                        if (day_3 != null) {
                            data = new BarData("Thu", Float.valueOf(df2.format(day_3)), df2.format(day_3) + "€");
                            dataList.add(data);
                        }
                        if (day_4 != null) {
                            data = new BarData("Fri", Float.valueOf(df2.format(day_4)), df2.format(day_4) + "€");
                            dataList.add(data);
                        }
                        if (day_5 != null) {
                            data = new BarData("Sat", Float.valueOf(df2.format(day_5)), df2.format(day_5) + "€");
                            dataList.add(data);
                        }
                        if (day_6 != null) {
                            data = new BarData("Sun", Float.valueOf(df2.format(day_6)), df2.format(day_6) + "€");
                            dataList.add(data);
                        }
                        ChartProgressBar mChart = findViewById(R.id.ChartProgressBar);
                        mChart.setMaxValue(Float.valueOf(Collections.max(doublesPrice).toString()) + 5);
                        mChart.setDataList(dataList);
                        mChart.build();

                        PieChart pieChart = findViewById(R.id.pieChart);

                        pieChart.setUsePercentValues(true);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.setExtraOffsets(5, 10, 10, 5);

                        pieChart.setDragDecelerationFrictionCoef(0.99f);

                        pieChart.setDrawHoleEnabled(true);
                        pieChart.setHoleColor(Color.WHITE);
                        pieChart.setTransparentCircleRadius(60f);


                        ArrayList<PieEntry> entries = new ArrayList<>();

                        entries.add(new PieEntry(counterMorning, "Morning"));
                        entries.add(new PieEntry(counterAfternoon, "Afternoon"));
                        entries.add(new PieEntry(counterEvening, "Evening"));
                        entries.add(new PieEntry(counterNight, "Night"));

                        PieDataSet set = new PieDataSet(entries, "");
                        set.setSliceSpace(3f);
                        set.setSelectionShift(5f);
                        set.setColors(ColorTemplate.JOYFUL_COLORS);

                        PieData pieData = new PieData(set);
                        pieChart.setData(pieData);

                        break;
                    case 400:
                        showSnack(dataOBJ.getString("message"));
                        break;
                    case 500:
                        showSnack(dataOBJ.getString("message"));
                        break;
                    default:
                        showSnack("Unexpected error!");
                        break;
                }
            } catch (Exception ex) {
                Log.e("FILL_ERROR_NOTIFIC", ex.getMessage());
                showSnack("Error reading the data!");
            }
        }
    }


    private void showSnack(String message) {
        Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }
}
