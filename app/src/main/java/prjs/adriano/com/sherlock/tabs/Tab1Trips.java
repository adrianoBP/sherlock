package prjs.adriano.com.sherlock.tabs;

/*
  Created by Adriano on 21/01/2018.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;

import prjs.adriano.com.sherlock.Activities.TripViewActivity;
import prjs.adriano.com.sherlock.Classes.Trip;
import prjs.adriano.com.sherlock.DBH.DBHTrip;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.Requests.TripHelper;
import prjs.adriano.com.sherlock.Services.AsyncDownloader;

import static prjs.adriano.com.sherlock.DBH.DBHTrip.allTrips;


public class Tab1Trips extends Fragment {

    //COMPONETNS
    public static ListView lvTrips;
//    static List<Trip> trips = new ArrayList<>();
    View rootView;
    public static CustomAdapterTripSmall customAdapterTripSmall;
    static DBHTrip dbhTrip;
    public static SwipeRefreshLayout srlMain;

    @Override
    public void onResume() {
        super.onResume();
        updateListViewTrips((rootView).getContext(), Global.returnPreferredSorting(getContext()));
    }

    public static void updateListViewTrips(Context context, final String sorter) {
        dbhTrip = new DBHTrip(context);
        allTrips = dbhTrip.getAllTrips();


        Collections.sort(allTrips, new Comparator<Trip>() {
            public int compare(Trip obj1, Trip obj2) {
                switch (sorter) {
                    case "departure ASC":
                        return obj1.getDeparture_time().compareTo(obj2.getDeparture_time());
                    case "departure DESC":
                        return obj2.getDeparture_time().compareTo(obj1.getDeparture_time());
                    case "arrival ASC":
                        return obj1.getArrival_time().compareTo(obj2.getArrival_time());
                    case "arrival DESC":
                        return obj2.getArrival_time().compareTo(obj1.getArrival_time());
                    case "duration ASC":
                        return obj1.getDuration().compareTo(obj2.getDuration());
                    case "duration DESC":
                        return obj2.getDuration().compareTo(obj1.getDuration());
                    default:
                        return obj2.getDeparture_time().compareTo(obj1.getDeparture_time());
                }
            }
        });

        customAdapterTripSmall.notifyDataSetChanged();

        if (srlMain != null)
            srlMain.setRefreshing(false);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab1trips, container, false);
//        trips = new ArrayList<>();
        // COMPONENT INITIALIZATION
        lvTrips = rootView.findViewById(R.id.lvTrips);
        customAdapterTripSmall = new CustomAdapterTripSmall();
        lvTrips.setAdapter(customAdapterTripSmall);

        srlMain = rootView.findViewById(R.id.srlMain);

        srlMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AsyncDownloader asyncDownloader = new AsyncDownloader();
                asyncDownloader.execute(getContext(), false);
//                TripHelper.getTrips(getContext(), false);
            }
        });

        srlMain.setColorSchemeColors(getContext().getResources().getColor(R.color.secondary_dark));

        lvTrips.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (lvTrips.getChildAt(0) != null) {
                    srlMain.setEnabled(lvTrips.getFirstVisiblePosition() == 0 && lvTrips.getChildAt(0).getTop() == 0);
                }
            }
        });


        return rootView;
    }

    public class CustomAdapterTripSmall extends BaseAdapter {

        @Override
        public int getCount() {
            return allTrips.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_adapter_trip_small, null);


            TextView tvDate = view.findViewById(R.id.tvCustomInstruction);
            TextView tvFrom = view.findViewById(R.id.tvFrom);
            TextView tvTo = view.findViewById(R.id.tvTo);
            TextView tvID = view.findViewById(R.id.tvID);
            if (allTrips.size() > 0) {
                tvDate.setText(Global.convertTimeToDate(allTrips.get(i).getDeparture_time().toString()));
                tvFrom.setText(allTrips.get(i).getDeparture_location());
                tvTo.setText(allTrips.get(i).getArrival_location());
                tvID.setText(allTrips.get(i).getId() + "");
            }

            ConstraintLayout clParent = view.findViewById(R.id.clParent);
            clParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!srlMain.isRefreshing()) {

                        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                        animation1.setDuration(400);
                        view.startAnimation(animation1);

                        TextView tvID = view.findViewById(R.id.tvID);

                        Intent intent = new Intent(getActivity(), TripViewActivity.class);
                        intent.putExtra("EXTRA_ID", tvID.getText().toString());
                        startActivity(intent);
                        ((Activity) getContext()).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                }
            });

            clParent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                    animation1.setDuration(3000);
                    view.startAnimation(animation1);

                    final TextView tvID = view.findViewById(R.id.tvID);
                    final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dlg_option);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    FloatingActionButton fabDelete = dialog.findViewById(R.id.fabDelete);
                    fabDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dbhTrip.deleteTrip(Integer.valueOf(tvID.getText().toString()))) {
                                updateListViewTrips((rootView).getContext(), Global.returnPreferredSorting(getContext()));
                                TripHelper.sendTripsData(getActivity());
                                Snackbar.make(((Activity) getContext()).findViewById(android.R.id.content), "DELETED TRIP WITH ID: " + tvID.getText().toString(), Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            } else {
                                Snackbar.make(((Activity) getContext()).findViewById(android.R.id.content), "Error deleting trip, try again.", Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            }
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    return true;
                }
            });


            return view;
        }
    }
}
