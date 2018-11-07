package prjs.adriano.com.sherlock.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import me.toptas.fancyshowcase.FancyShowCaseView;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.Requests.UtilHelper;
import prjs.adriano.com.sherlock.tabs.Tab1Trips;
import prjs.adriano.com.sherlock.tabs.Tab2Home;
import prjs.adriano.com.sherlock.tabs.Tab3Utility;

import static prjs.adriano.com.sherlock.tabs.Tab1Trips.updateListViewTrips;

//TODO APP: logout from all
//TODO APP: more testing about saved trips
//TODO APP: check version (send it and check it) while making http request (either from APP or backend)
//TODO APP: performance in info
//TODO APP: FKING FIXING NAVBAR
//TODO APP: Review "Allow notifications" (version checking)
//TODO APP: Animation when opening the app not right (fade missing)

//TODO WEB: better graph info (ie date range, average price for them month)
//TODO WEB: Admin view users/devce per user/last login(add field on db + API call when app starts)
//TODO WEB: Log number of API calls: new database w/ APIiD + nOfCalls

//Done
//TODO APP: TIME (ie sunset, sunrise) get time in location (using API?) / get timezone from latitude longitude

public class MainActivity extends AppCompatActivity {

    //CONSTANTS

    //VARS
    int defaultTab = 2; // Default starting page ID
    public static FabSpeedDial fSorter;
    //Global global;

    //COMPONENTS
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onStop() {
        super.onStop();
//        TripHelper.sendTripsData(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
//        TripHelper.sendTripsData(this);
        appBarLayout.setExpanded(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        TripHelper.sendTripsData(this);
        appBarLayout.setExpanded(true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        appBarLayout.setExpanded(true);
    }

    public static FancyShowCaseView fancyShowCaseView;
    public static AppBarLayout appBarLayout;
    public static Button bObserve;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        appBarLayout.setExpanded(true);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        appBarLayout.setExpanded(true);
        try {
            if (fancyShowCaseView.isShowing())
                fancyShowCaseView.hide();
            else
                finish();
        } catch (Exception ex) {
            finish();
        }

    }

    @Override
    protected void onDestroy() {
//        TripHelper.sendTripsData(this);
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        UtilHelper.sendToken(this);
        fancyShowCaseView = new FancyShowCaseView.Builder(this).build();

        appBarLayout = findViewById(R.id.appbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        fSorter = (FabSpeedDial) this.findViewById(R.id.fSorter);
        if (SettingsActivity.fromPageNameToPageId(getResources().getString(R.string.tab_trips)) == Global.returnPreferredStartingPage(MainActivity.this)) {
            fSorter.setVisibility(View.VISIBLE);
        }

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        mViewPager.setAdapter(mSectionsPagerAdapter);
                    }
                }
        );

        final AppBarLayout appBarLayout = findViewById(R.id.appbar);
        TabLayout tabLayout = findViewById(R.id.tabs);


        if (appBarLayout.getLayoutParams() != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
            AppBarLayout.Behavior appBarLayoutBehaviour = new AppBarLayout.Behavior();
            appBarLayoutBehaviour.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return false;
                }
            });
            layoutParams.setBehavior(appBarLayoutBehaviour);
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Animation myAnim;
                switch (tab.getText().toString()) {
                    case "TRIPS":
                        fSorter.show();
                        myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_up);
                        fSorter.startAnimation(myAnim);
                        break;
                    default:
                        fSorter.hide();
                        myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_down);
                        myAnim.setDuration(200);
                        fSorter.startAnimation(myAnim);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.setCurrentItem(Global.returnPreferredStartingPage(this)); //sets opening tab on applicatoin start

        if (!Global.getOnline(this)) {

            Snackbar.make(this.findViewById(android.R.id.content), "You are OFFLINE! Be careful!", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
        }

        appBarLayout.setExpanded(true);


        fSorter.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                Global.editPreferredSorting(menuItem.getTitle().toString(), MainActivity.this);
                updateListViewTrips(MainActivity.this, menuItem.getTitle().toString());
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            ((Activity) this).overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Tab1Trips();
                case 1:
                    return new Tab2Home();
                case 2:
                    return new Tab3Utility();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }


}
