package prjs.adriano.com.sherlock.Services;

import android.content.Context;
import android.os.AsyncTask;

import static prjs.adriano.com.sherlock.Requests.TripHelper.getTrips;

public class AsyncDownloader extends AsyncTask<Object, Void, Void> {

    @Override
    protected Void doInBackground(Object... objects) {
        getTrips((Context)objects[0], (Boolean)objects[1]);
        return null;
    }
}
