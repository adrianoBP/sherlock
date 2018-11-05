package prjs.adriano.com.sherlock.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import prjs.adriano.com.sherlock.Classes.Airport;
import prjs.adriano.com.sherlock.R;

public class SpinnerAdapterAirports extends BaseAdapter {

    private List<Airport> airports;
    private LayoutInflater layoutInflater;

    public SpinnerAdapterAirports(List<Airport> airports, Activity activity) {
        this.airports = airports;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return airports.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(convertView == null){
            view = layoutInflater.inflate(R.layout.custom_adapter_spinner_airport, null);
        }
        TextView tvCode = view.findViewById(R.id.tvIata);
        tvCode.setText(airports.get(position).getIata());
        TextView tvName = view.findViewById(R.id.tvAirportName);
        tvName.setText(airports.get(position).getName());
        return view;
    }
}
