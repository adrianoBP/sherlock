package prjs.adriano.com.sherlock.DBH;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import prjs.adriano.com.sherlock.Classes.Trip;
import prjs.adriano.com.sherlock.Classes.hop.Hop;
import prjs.adriano.com.sherlock.Classes.hop.Transit;
import prjs.adriano.com.sherlock.Classes.hop.Vehicle;
import prjs.adriano.com.sherlock.Classes.hop.Walk;

/**
 * Created by Adriano on 04/02/2018.
 */

public class DBHTrip extends SQLiteOpenHelper {

    //VALUES
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "sherlock";

    public static List<Trip> allTrips = new ArrayList<>();

    public DBHTrip(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TRIP = "CREATE TABLE trip ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "departure_location TEXT," +
                "arrival_location TEXT," +
                "departure_time TEXT," +
                "arrival_time TEXT)";
        sqLiteDatabase.execSQL(CREATE_TRIP);
        String CREATE_VEHICLE = "CREATE TABLE vehicle (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_trip INTEGER," +
                "headsign TEXT," +
                "name TEXT," +
                "short_name TEXT," +
                "color TEXT," +
                "type TEXT," +
                "agency_name TEXT," +
                "agency_url TEXT," +
                "FOREIGN KEY(id_trip) REFERENCES trip(id) ON DELETE CASCADE ON UPDATE CASCADE)";
        sqLiteDatabase.execSQL(CREATE_VEHICLE);
        String CREATE_WALK = "CREATE TABLE walk (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_trip INTEGER," +
                "distance TEXT," +
                "distance_value INTEGER," +
                "duration TEXT," +
                "duration_value INTEGER," +
                "instruction TEXT," +
                "prox INTEGER," +
                "start_point_x STRING," +
                "start_point_y STRING," +
                "end_point_x STRING," +
                "end_point_y STRING," +
                "FOREIGN KEY(id_trip) REFERENCES trip(id) ON DELETE CASCADE ON UPDATE CASCADE)";
        sqLiteDatabase.execSQL(CREATE_WALK);
        String CREATE_TRANSIT = "CREATE TABLE transit (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_trip INTEGER," +
                "distance TEXT," +
                "distance_value INTEGER," +
                "duration TEXT," +
                "duration_value INTEGER," +
                "instruction TEXT," +
                "departure_stop TEXT," +
                "departure_time INTEGER," +
                "arrival_stop TEXT," +
                "arrival_time INTEGER," +
                "n_stops INTEGER," +
                "id_vehicle INTEGER," +
                "prox INTEGER," +
                "start_point_x STRING," +
                "start_point_y STRING," +
                "end_point_x STRING," +
                "end_point_y STRING," +
                "FOREIGN KEY(id_trip) REFERENCES trip(id) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY(id_vehicle) REFERENCES vehicle(id) ON DELETE CASCADE ON UPDATE CASCADE)";
        sqLiteDatabase.execSQL(CREATE_TRANSIT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void addTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues tripVals = new ContentValues();
//        tripVals.put("id", Global.returnNextTripId(context));
        tripVals.put("departure_location", TextUtils.join(" ", trip.getDeparture_location().split("\\+")));
        tripVals.put("arrival_location", TextUtils.join(" ", trip.getArrival_location().split("\\+")));
        tripVals.put("departure_time", trip.getDeparture_time());
        tripVals.put("arrival_time", trip.getArrival_time());
        long id = db.insert("trip", null, tripVals);

        if (id >= 0) {
            for (Hop h : trip.getHops()) {
                addHop(h, (int) id);
            }
        }
    }

    private void addHop(Hop hop, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues hopVals = new ContentValues();
        hopVals.put("id_trip", id);
        hopVals.put("distance", hop.getDistance());
        hopVals.put("distance_value", hop.getDistance_value());
        hopVals.put("duration", hop.getDuration());
        hopVals.put("duration_value", hop.getDuration_value());
        hopVals.put("instruction", hop.getInstruction());
        hopVals.put("prox", hop.getIndex());
        if (hop.getStartPoint() != null) {
            hopVals.put("start_point_x", hop.getStartPoint().latitude);
            hopVals.put("start_point_y", hop.getStartPoint().longitude);
            hopVals.put("end_point_x", hop.getEndPoint().latitude);
            hopVals.put("end_point_y", hop.getEndPoint().longitude);
        } else {
            hopVals.putNull("start_point_x");
            hopVals.putNull("start_point_y");
            hopVals.putNull("end_point_x");
            hopVals.putNull("end_point_y");
        }
        if (hop instanceof Walk)
            db.insert("walk", null, hopVals);
        else if (hop instanceof Transit) {
            hopVals.put("departure_stop", ((Transit) hop).getDepartureStop());
            hopVals.put("departure_time", ((Transit) hop).getDepartureTime());
            hopVals.put("arrival_stop", ((Transit) hop).getArrivalStop());
            hopVals.put("arrival_time", ((Transit) hop).getArrivalTime());
            hopVals.put("n_stops", ((Transit) hop).getnStops());
            hopVals.put("id_vehicle", addVehicle(((Transit) hop).getVehicle(), id));
            long idTransit = db.insert("transit", null, hopVals);
        }

    }

    private Integer addVehicle(Vehicle vehicle, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vehicleVals = new ContentValues();
        vehicleVals.put("id_trip", id);
        vehicleVals.put("headsign", vehicle.getHeadSign());
        vehicleVals.put("name", vehicle.getName());
        vehicleVals.put("short_name", vehicle.getShortName());
        vehicleVals.put("color", vehicle.getColor());
        vehicleVals.put("type", vehicle.getType());
        vehicleVals.put("agency_name", vehicle.getAgencyName());
        vehicleVals.put("agency_url", vehicle.getAgencyUrl());

        long idVehicle = db.insert("vehicle", null, vehicleVals);


        return (int) idVehicle;

    }

    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        String query = "SELECT * FROM trip ORDER BY departure_time ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Trip trip = new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), getHopsById(cursor.getInt(0)));

                trips.add(trip);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return trips;
    }

    public Trip getTrip(String id) {
        String query = "SELECT * FROM trip WHERE id=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Trip trip;
        if (cursor.moveToFirst()) {
            do {
                trip = new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), getHopsById(cursor.getInt(0)));
            } while (cursor.moveToNext());
        } else {
            trip = null;
        }
        cursor.close();

        return trip;
    }

    public boolean deleteTrip(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("trip", "id=" + id, null) > 0;
    }


    public Vehicle getVehicle(String id) {
        String query = "SELECT id, id_trip, headsign, name, short_name, color, type, agency_name, agency_url FROM vehicle WHERE id=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Vehicle vehicle;
        if (cursor.moveToFirst()) {
            do {

                vehicle = new Vehicle(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));


            } while (cursor.moveToNext());
        } else {
            vehicle = null;
        }
        cursor.close();
        db.close();

        return vehicle;
    }

    private List<Hop> getHopsById(Integer tripId) {
        List<Hop> hops = new ArrayList<>();
        String query =
                "SELECT t.id, t.id_trip, t.distance, t.distance_value, t.duration, t.duration_value, t.instruction, t.departure_stop, t.departure_time, t.arrival_stop, t.arrival_time, t.n_stops," +
                        "v.id, v.id_trip, v.headsign, v.name, v.short_name, v.color, v.type, v.agency_name, v.agency_url, t.id_vehicle, t.prox, t.start_point_x, t.start_point_y, t.end_point_x, t.end_point_y " +
                        "FROM transit t, vehicle v " +
                        "WHERE t.id_trip=" + tripId + " AND v.id=t.id" +
                        " UNION " +
                        "SELECT w.id, w.id_trip, w.distance, w.distance_value, w.duration, w.duration_value, w.instruction, NULL as departure_stop, NULL as departure_time, NULL as arriva_stop, NULL as arrival_time," +
                        "NULL as n_stops, NULL as id, NULL as id_trip, NULL as headsign, NULL as name, NULL as short_name, NULL as color, NULL as type, NULL as agency_name, NULL as agency_url, " +
                        "NULL as id_vehicle, w.prox, start_point_x, start_point_y, end_point_x, end_point_y " +
                        "FROM walk w " +
                        "WHERE w.id_trip=" + tripId + " ORDER BY prox ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                LatLng startPoint = null;
                LatLng endPoint = null;
                if (!TextUtils.isEmpty(cursor.getString(23))) {
                    startPoint = new LatLng(Float.valueOf(cursor.getString(23)), Float.valueOf(cursor.getString(24)));
                    endPoint = new LatLng(Float.valueOf(cursor.getString(25)), Float.valueOf(cursor.getString(26)));
                }
                if (TextUtils.isEmpty(cursor.getString(7))) {
                    Walk walk = new Walk(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getInt(3),
                            cursor.getString(4),
                            cursor.getInt(5),
                            cursor.getString(6),
                            cursor.getInt(22),
                            startPoint,
                            endPoint

                    );
                    hops.add(walk);
                } else {
                    Vehicle vehicle = new Vehicle(
                            cursor.getInt(12),
                            cursor.getInt(13),
                            cursor.getString(14),
                            cursor.getString(15),
                            cursor.getString(16),
                            cursor.getString(17),
                            cursor.getString(18),
                            cursor.getString(19),
                            cursor.getString(20)
                    );
                    Transit transit = new Transit(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getInt(3),
                            cursor.getString(4),
                            cursor.getInt(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getInt(8),
                            cursor.getString(9),
                            cursor.getInt(10),
                            cursor.getInt(11),
                            vehicle,
                            cursor.getInt(22),
                            startPoint,
                            endPoint
                    );
                    hops.add(transit);
                }
            }
            while (cursor.moveToNext());
        }


        return hops;
    }

    public void clearTrips() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("trip", "", null);
        db.delete("vehicle", "", null);
        db.delete("walk", "", null);
        db.delete("transit", "", null);
        db.close();
    }

}
