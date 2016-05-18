package uam.eps.es.sensorshowcase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Ari on 18/05/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT=2;
    private String tabTitles[] = new String[] { "SENSORS", "INPUT"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment sensorListFragment = new SensorListFragment();
        return sensorListFragment;
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
