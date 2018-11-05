package prjs.adriano.com.sherlock.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.Objects;

import prjs.adriano.com.sherlock.DBH.DBHTrip;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;

public class SettingsActivity extends AppCompatActivity {

    //TODO main currency

    //COMPONENTS
    Spinner sStartingPage;
    Switch switchAutoLogin, switchDownloadImage, switchAllowNotifications;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Components initialization
        switchAutoLogin = findViewById(R.id.switch1);
        switchDownloadImage = findViewById(R.id.switch2);
        switchAllowNotifications = findViewById(R.id.switch3);
        sStartingPage = findViewById(R.id.sStartingPage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tSettings);
        toolbar.setTitleTextColor(getResources().getColor(R.color.fontBright));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        backArrow.setColorFilter(getResources().getColor(R.color.fontBright), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);

        fillComponents();

        switchAutoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.setAutologinStatus(SettingsActivity.this, !Global.returnAutologinStatus(SettingsActivity.this));
                if (Global.returnAutologinStatus(SettingsActivity.this)) {
                    switchAutoLogin.setChecked(true);
                } else {
                    switchAutoLogin.setChecked(false);
                }
            }
        });

        switchDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.setDownloadImageStatus(SettingsActivity.this, !Global.returnDownloadImageStatus(SettingsActivity.this));
                if(Global.returnDownloadImageStatus(SettingsActivity.this)){
                    switchDownloadImage.setChecked(true);
                }else {
                    switchDownloadImage.setChecked(false);
                }
            }
        });

        switchAllowNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.setAllowNotifications(SettingsActivity.this, !Global.returnAllowNotifications(SettingsActivity.this));
                if(Global.returnAllowNotifications(SettingsActivity.this))
                    switchAllowNotifications.setChecked(true);
                else
                    switchAllowNotifications.setChecked(false);
            }
        });

        sStartingPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String val = adapterView.getItemAtPosition(i).toString();
                Global.editPreferredStartingPage(fromPageNameToPageId(val), SettingsActivity.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Button bLogOut = findViewById(R.id.bLogOut);

        bLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(400);
                view.startAnimation(animation1);
                Global.setAutologinStatus(SettingsActivity.this, false);
                finish();
                Intent i = getBaseContext().getPackageManager().
                        getLaunchIntentForPackage(getBaseContext().getPackageName());
                Objects.requireNonNull(i).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                DBHTrip dbhTrip = new DBHTrip(SettingsActivity.this);
                dbhTrip.clearTrips();
                finishAffinity();

            }
        });


        loadComponents();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SettingsActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }

    void loadComponents() {
        switchAutoLogin.setChecked(Global.returnAutologinStatus(this));
        switchDownloadImage.setChecked(Global.returnDownloadImageStatus(this));
        sStartingPage.setSelection(Global.returnPreferredStartingPage(this));
        switchAllowNotifications.setChecked(Global.returnAllowNotifications(this));
    }

    void fillComponents() {
        ArrayAdapter<String> preferredPageAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{getResources().getString(R.string.tab_trips), getResources().getString(R.string.tab_home), getResources().getString(R.string.tab_profile)});
        preferredPageAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sStartingPage.setAdapter(preferredPageAdapter);
    }

    String fromPageIdToPageName(int id) {
        switch (id) {
            case 0:
                return getResources().getString(R.string.tab_trips);
            case 1:
                return getResources().getString(R.string.tab_home);
            case 2:
                return getResources().getString(R.string.tab_profile);
            default:
                return getResources().getString(R.string.tab_home);
        }
    }

    public static int fromPageNameToPageId(String name) {
        switch (name) {
            case "TRIPS":
                return 0;
            case "HOME":
                return 1;
            case "UTILITY":
                return 2;
            default:
                return 0;
        }
    }

}
