package prjs.adriano.com.sherlock.Services;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.flags.impl.DataUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import prjs.adriano.com.sherlock.BuildConfig;

/**
 * Created by Adriano on 04/02/2018.
 */

public class Global extends Application {

    //region Autologin
    public static boolean returnAutologinStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String status = sharedPreferences.getString("autologin", "");
        return Boolean.valueOf(status);
    }

    public static void setAutologinStatus(Context context, boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("autologin", String.valueOf(status));
        editor.apply();
    }

    public static boolean checkAutologinInitialization(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("autologin")) {
            return true;
        } else {
            setAutologinStatus(context, true);
            return false;
        }
    }

    //endregion
    //region download image
    public static boolean returnDownloadImageStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String status = sharedPreferences.getString("downloadimage", "");
        return Boolean.valueOf(status);
    }

    public static void setDownloadImageStatus(Context context, boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("downloadimage", String.valueOf(status));
        editor.apply();
    }

    public static boolean checkDownloadImageInitialization(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("downloadimage")) {
            return true;
        } else {
            setDownloadImageStatus(context, true);
            return false;
        }
    }

    //endregion
    //region allow notifications
    public static boolean returnAllowNotifications(Context context) {
        checkAllowNotifications(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String status = sharedPreferences.getString("allownotificatinons", "");
        return Boolean.valueOf(status);
    }

    public static void setAllowNotifications(Context context, boolean allow) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("allownotificatinons", String.valueOf(allow));
        editor.apply();
    }

    public static boolean checkAllowNotifications(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("allownotificatinons")) {
            return true;
        } else {
            setAllowNotifications(context, true);
            return false;
        }
    }

    //endregion
    //region allow locked
    public static boolean returnLocked(Context context) {
        checkLocked(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String status = sharedPreferences.getString("lock", "");
        return Boolean.valueOf(status);
    }

    public static void setLocked(Context context, boolean lock) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lock", String.valueOf(lock));
        editor.apply();
    }

    public static boolean checkLocked(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("lock")) {
            return true;
        } else {
            setLocked(context, false);
            return false;
        }
    }

    //endregion
    //region Preferred starting page
    public static int returnPreferredStartingPage(Context context) {
        checkPreferredStartingPageInitialization(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String status = sharedPreferences.getString("startingpage", "");
        return Integer.valueOf(status);
    }

    public static void editPreferredStartingPage(int pageID, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("startingpage", String.valueOf(pageID));
        editor.apply();
    }

    public static boolean checkPreferredStartingPageInitialization(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("startingpage")) {
            return true;
        } else {
            editPreferredStartingPage(0, context);
            return false;
        }
    }

    //endregion
    //region Preferred starting page
    public static String returnLatestVersion(Context context) {
        checkLatestVersion(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String latestVersion = sharedPreferences.getString("latestversion", "");
        return latestVersion;
    }

    public static void editLatestVersion(String latestversion, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("latestversion", String.valueOf(latestversion));
        editor.apply();
    }

    public static boolean checkLatestVersion(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("latestversion")) {
            return true;
        } else {
            editLatestVersion(BuildConfig.VERSION_NAME, context);
            return false;
        }
    }

    //endregion
    //region uid
    public static String returnUid(Context context) {
        checkUid(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid", "");
    }

    public static void setUid(Context context, String uid) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid", uid);
        editor.apply();
    }

    public static boolean checkUid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("uid")) {
            return true;
        } else {
            setUid(context, "-1");
            return false;
        }
    }
    //endregion

    //region sync
    public static boolean getSync(Context context) {
        checkSync(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String syn = sharedPreferences.getString("sync", "");
        return Boolean.valueOf(syn);
    }

    public static void setSync(Context context, boolean syn) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sync", String.valueOf(syn));
        editor.apply();
    }

    public static boolean checkSync(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("sync")) {
            return true;
        } else {
            setSync(context, false);
            return false;
        }
    }
    //endregion

    //region online
    public static boolean getOnline(Context context) {
//        checkOnline(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String online = sharedPreferences.getString("online", "");
        return Boolean.valueOf(online);
    }

    public static void setOnline(Context context, boolean online) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("online", String.valueOf(online));
        editor.apply();
    }

    public static boolean checkOnline(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("online")) {
            return true;
        } else {
//            setSync(context, false);
            return false;
        }
    }
    //endregion

    //region image url

    public static String getImageurl(Context context) {
        checkImageurl(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String url = sharedPreferences.getString("imageurl", "");
        return url;
    }

    public static void setImageurl(Context context, String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imageurl", url);
        editor.apply();
    }

    public static boolean checkImageurl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("imageurl")) {
            return true;
        } else {
            setImageurl(context, "https://www.noao.edu/image_gallery/images/d4/androy.jpg");
            return false;
        }
    }

    //endregion
    //region SORTER
    public static String returnPreferredSorting(Context context) {
        checkPreferredSortingInitialization(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String status = sharedPreferences.getString("sorting", "");
        return status;
    }

    public static void editPreferredSorting(String sort, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sorting", sort);
        editor.apply();
    }

    public static boolean checkPreferredSortingInitialization(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("sorting")) {
            return true;
        } else {
            editPreferredSorting("departure DESC", context);
            return false;
        }
    }
    //endregion

    //region nearbyAirports

    public static void setNearby(Context context, String nearby) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nearbyairports", nearby);
        editor.apply();
    }

    public static String getNearby(Context context) {
        checkNerabyAirports(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String nearby = sharedPreferences.getString("nearbyairports", "");
        return nearby;
    }

    public static void checkNerabyAirports(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (!sharedPreferences.contains("nearbyairports")) {
            setNearby(context, "BGY%Milan Bergamo_MXP%Milan Malpensa_VRN%Verona");
        }
    }
    //endregion

    //TODO FUTURE cryptation

    //UTILITY
    public static String removeHtml(String html) {
        html = html.replaceAll("<(.*?)\\>", "");
        html = html.replaceAll("<(.*?)\\\n", "");
        html = html.replaceFirst("(.*?)\\>", "");
        html = html.replaceAll("&nbsp;", "");
        html = html.replaceAll("&amp;", "");
        return html;
    }

    public static String convertTime(String datetime) {
        try {
            Date date = new Date(1000L * Integer.valueOf(datetime));
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Calendar time = Calendar.getInstance();
            time.setTime(date);
            date = time.getTime();
            return sdf.format(date);
        } catch (Exception ex) {
            return "--:--";
        }
    }

    public static String convertToDateTime(String datetime) {
        try {
            Date date = new Date(1000L * Integer.valueOf(datetime));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Calendar time = Calendar.getInstance();
            time.setTime(date);
            date = time.getTime();
            return sdf.format(date);
        } catch (Exception ex) {
            return "dd/MM/yyyy  AT  HH:mm";
        }
    }

    public static String convertToTime(String datetime) {
        try {
            Date date = new Date(1000L * Integer.valueOf(datetime));
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Calendar time = Calendar.getInstance();
            time.setTime(date);
            date = time.getTime();
            return sdf.format(date);
        } catch (Exception ex) {
            return "HH:mm";
        }
    }

    public static String convertToDate(String datetime) {
        try {
            Date date = new Date(1000L * Integer.valueOf(datetime));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar time = Calendar.getInstance();
            time.setTime(date);
            date = time.getTime();
            return sdf.format(date);
        } catch (Exception ex) {
            return "dd/MM/yyyy";
        }
    }

    public static String convertTimeToDate(String datetime) {
        try {
            Date date = new Date(1000L * Integer.valueOf(datetime));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"/* HH:mm"*/);
            Calendar time = Calendar.getInstance();
            time.setTime(date);
            date = time.getTime();
            return sdf.format(date);
        } catch (Exception ex) {
            return "--/--/----"/* --:--"*/;
        }
    }

    public static String convertDateToStrDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"/* HH:mm"*/);
            Calendar time = Calendar.getInstance();
            time.setTime(date);
            date = time.getTime();
            return sdf.format(date);
        } catch (Exception ex) {
            return "2018-01-01"/* --:--"*/;
        }
    }

    public static String from12to24(String input) {

        String formattedInput = "";
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm:ss a");
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
        try {
           formattedInput = date24Format.format(date12Format.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(TextUtils.split(formattedInput, ":")[0]));// for 6 hour
        calendar.set(Calendar.MINUTE, Integer.parseInt(TextUtils.split(formattedInput, ":")[1]));// for 0 min

        //Desired format: 24 hour format: Change the pattern as per the need
        DateFormat outputformat = new SimpleDateFormat("HH:mm");
        outputformat.setTimeZone(TimeZone.getDefault());

        String output = null;
        output = outputformat.format(calendar.getTime());

        return output;
    }

    public static void buildAlertMessageNoGps(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please turn ON your GPS connection")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public static String getLocation(Context context, LocationManager locationManager) {
        String res = "0,0";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location location = null;
            Location location1 = null;
            Location location2 = null;
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            double latti = 0;
            double longi = 0;


            if (location == null && location1 == null && location2 == null) {
                Log.e("LOCATION", "Location error!");
            } else {
                if (location != null) {
                    latti = location.getLatitude();
                    longi = location.getLongitude();

                } else if (location1 != null) {
                    latti = location1.getLatitude();
                    longi = location1.getLongitude();

                } else {
                    latti = location2.getLatitude();
                    longi = location2.getLongitude();

                }

                String lattitude = String.valueOf(latti);
                String longitude = String.valueOf(longi);

                res = lattitude + "," + longitude;
            }
            return res;
        }
        return res;
    }

    public static String getIdentifier(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness < 0.5) {
            return false; // It's a light color
        } else {
            return true; // It's a dark color
        }
    }
}
