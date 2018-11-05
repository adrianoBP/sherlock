package prjs.adriano.com.sherlock.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import prjs.adriano.com.sherlock.Classes.Currency;
import prjs.adriano.com.sherlock.R;

public class SpinnerAdapterCurrencies extends BaseAdapter {

    private List<Currency> currencies;
    private LayoutInflater layoutInflater;

    public SpinnerAdapterCurrencies(List<Currency> currencies, Activity activity) {
        this.currencies = currencies;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return currencies.size();
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
            view = layoutInflater.inflate(R.layout.custom_adapter_spinner_currency, null);
        }
        TextView tvCode = view.findViewById(R.id.tvCode);
        tvCode.setText(currencies.get(position).getCode());
        TextView tvName = view.findViewById(R.id.tvNameCurrency);
        tvName.setText(currencies.get(position).getName());
        TextView tvSymbol = view.findViewById(R.id.tvSymbol);
        tvSymbol.setText("["+currencies.get(position).getSymbol()+"]");
        TextView tvCountry = view.findViewById(R.id.tvAirportName);
        tvCountry.setText(currencies.get(position).getCountry());
        return view;
    }
}
