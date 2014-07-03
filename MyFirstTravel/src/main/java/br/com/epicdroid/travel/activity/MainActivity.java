package br.com.epicdroid.travel.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.Locale;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.fragment.FinanceFragment;
import br.com.epicdroid.travel.fragment.NoteFragment;
import br.com.epicdroid.travel.fragment.PlaceFragment;
import br.com.epicdroid.travel.fragment.ScheduleFragment;

public class MainActivity extends FragmentActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case NoteFragment.POSITION:
                    return new NoteFragment();
                case FinanceFragment.POSITION:
                    return new FinanceFragment();
                case ScheduleFragment.POSITION:
                    return new ScheduleFragment();
                case PlaceFragment.POSITION:
                    return new PlaceFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            invalidateOptionsMenu();
            switch (position) {
                case NoteFragment.POSITION:
                    return NoteFragment.NAME_TAB.toUpperCase();
                case FinanceFragment.POSITION:
                    return FinanceFragment.NAME_TAB.toUpperCase();
                case ScheduleFragment.POSITION:
                    return ScheduleFragment.NAME_TAB.toUpperCase();
                case PlaceFragment.POSITION:
                    return PlaceFragment.NAME_TAB.toUpperCase();

            }
            return null;
        }
    }

}
