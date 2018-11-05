package prjs.adriano.com.sherlock.DBH;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import prjs.adriano.com.sherlock.Classes.Airport;
import prjs.adriano.com.sherlock.Classes.Currency;

public class DBHUtil extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME="sherlock_util";

    public DBHUtil(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CURRENCY = "CREATE TABLE currency(" +
                "id INTEGER PRIMARY KEY," +
                "code TEXT," +
                "name TEXT," +
                "symbol TEXT," +
                "value TEXT," +
                "country TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_CURRENCY);
        String CREATE_AIRPRT = "CREATE TABLE airport(" +
                "iata TEXT PRIMARY KEY," +
                "name TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_AIRPRT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void addCurrency(Currency currency){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues currencyVals = new ContentValues();
        currencyVals.put("id", currency.getId());
        currencyVals.put("code", currency.getCode());
        currencyVals.put("name", currency.getName());
        currencyVals.put("symbol", currency.getSymbol());
        currencyVals.put("value", currency.getValue());
        currencyVals.put("country", currency.getCountry());
        long id = db.insert("currency", null, currencyVals);

    }

    public boolean checkCurrencyEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM currency";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        return icount <= 0;
    }

    public void updateCurrencies(List<Currency> currencies){
        SQLiteDatabase db = this.getWritableDatabase();

        for(Currency currency : currencies){
            ContentValues currencyVals = new ContentValues();
            currencyVals.put("id", currency.getId());
            currencyVals.put("code", currency.getCode());
            currencyVals.put("name", currency.getName());
            currencyVals.put("symbol", currency.getSymbol());
            currencyVals.put("value", currency.getValue());
            currencyVals.put("country", currency.getCountry());

            db.update("currency", currencyVals, "id="+currency.getId(), null);
        }
    }

    public List<Currency> getCurrencies(){
        List<Currency> currencies = new ArrayList<>();

        String query = "SELECT id, code, name, symbol, value, country FROM currency ORDER BY code ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Currency currency = new Currency(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                currencies.add(currency);

            }while (cursor.moveToNext());
        }

        db.close();

        return currencies;
    }

    public Currency getCurrencyFromCode(String code){
        String query = "SELECT id, code, name, symbol, value, country FROM currency WHERE code='"+code+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return new Currency(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
        }else{
            return new Currency();
        }
    }

    public void addAirport(Airport airport){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues airportVals = new ContentValues();
        airportVals.put("iata", airport.getIata());
        airportVals.put("name", airport.getName());
        long id = db.insert("airport", null, airportVals);
    }

    public boolean checkAirportEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM airport";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        db.close();

        return icount <= 0;
    }

    public void updateAirports(List<Airport> airports){
        SQLiteDatabase db = this.getWritableDatabase();

        for(Airport airport : airports){
            ContentValues airportVals = new ContentValues();
            airportVals.put("iata", airport.getIata());
            airportVals.put("name", airport.getName());

            db.update("airport", airportVals, "iata='"+airport.getIata()+"'", null);
        }


    }

    public List<Airport> getAirports(){
        List<Airport> airports = new ArrayList<>();

        String query = "SELECT iata, name FROM airport ORDER BY iata ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Airport airport= new Airport(
                        cursor.getString(0),
                        cursor.getString(1)
                );
                airports.add(airport);
            }while (cursor.moveToNext());
        }

        db.close();

        return airports;
    }

    public Airport getAirportFromIata(String iata){
        String query = "SELECT iata, name FROM airport WHERE iata='"+iata+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return new Airport(
                    cursor.getString(0),
                    cursor.getString(1)
            );
        }else{
            return new Airport();
        }
    }
}
