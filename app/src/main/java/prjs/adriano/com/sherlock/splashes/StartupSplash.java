package prjs.adriano.com.sherlock.splashes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Objects;

import prjs.adriano.com.sherlock.Activities.LoginActivity;
import prjs.adriano.com.sherlock.BuildConfig;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;

import static prjs.adriano.com.sherlock.Services.Global.checkDownloadImageInitialization;
import static prjs.adriano.com.sherlock.Services.Global.returnLatestVersion;

public class StartupSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_splash);

        final LottieAnimationView animationView1 = findViewById(R.id.animation_view1);
        //set animation file from assets
        animationView1.setImageAssetsFolder("assets");
        animationView1.setAnimation("loading_main.json");
        animationView1.loop(true);  //for continuous looping
        animationView1.playAnimation();//start animation

        int DELAY_TIME = 2500;

        checkDownloadImageInitialization(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                if(!Global.returnLocked(StartupSplash.this)) {
                    if(Objects.equals(returnLatestVersion(StartupSplash.this), BuildConfig.VERSION_NAME)) {
                        if (Global.checkAutologinInitialization(StartupSplash.this)) {
                            if (Global.returnAutologinStatus(StartupSplash.this)) {
                                intent = new Intent(StartupSplash.this, AfterLogSplash.class);
                            } else {
                                intent = new Intent(StartupSplash.this, LoginActivity.class);
                            }
                        } else {
                            intent = new Intent(StartupSplash.this, LoginActivity.class);
                        }
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        StartupSplash.this.finish();
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }else{
                        Toast.makeText(StartupSplash.this, "Please update Sherlock.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }else{
                    Toast.makeText(StartupSplash.this, "Application temporaty locked, try again later.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }, DELAY_TIME);
    }
}
