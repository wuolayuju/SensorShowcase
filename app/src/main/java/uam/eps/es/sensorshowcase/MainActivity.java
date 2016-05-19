package uam.eps.es.sensorshowcase;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import uam.eps.es.sensorshowcase.actuators.ActuatorListFragment;
import uam.eps.es.sensorshowcase.external_devices.ExternalDevicesFragment;
import uam.eps.es.sensorshowcase.input_devices.InputDevicesListFragment;
import uam.eps.es.sensorshowcase.sensors.SensorListFragment;

public class MainActivity extends AppCompatActivity {

    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * Created by Ari on 18/05/2016.
     */
    public static class PagerAdapter extends FragmentStatePagerAdapter {

        private final int PAGE_COUNT=4;
        private String tabTitles[] = new String[] { "SENSORS", "INPUT DEVICES", "ACTUATORS", "EXTERNAL DEVICES"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SensorListFragment();
                case 1:
                    return new InputDevicesListFragment();
                case 2:
                    return new ActuatorListFragment();
                case 3:
                    return new ExternalDevicesFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
