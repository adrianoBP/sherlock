package prjs.adriano.com.sherlock.splashes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.Requests.UtilHelper;
import prjs.adriano.com.sherlock.Services.AsyncDownloader;

public class AfterLogSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_log_splash);
        AsyncDownloader asyncDownloader = new AsyncDownloader();
        asyncDownloader.execute(AfterLogSplash.this, true);
        UtilHelper.getCurrencies(this);
        UtilHelper.getAirports(this);
        Global.setSync(this, true);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
}
