package br.com.epicdroid.travel.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.Locale;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.fragment.DocumentFragment;
import br.com.epicdroid.travel.fragment.FinanceFragment;
import br.com.epicdroid.travel.fragment.NoteFragment;
import br.com.epicdroid.travel.fragment.PlaceFragment;
import br.com.epicdroid.travel.fragment.TravelFragment;
import br.com.epicdroid.travel.components.SimpleAlertDialog;

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
                case TravelFragment.POSITION:
                    return new TravelFragment();
                case NoteFragment.POSITION:
                    return new NoteFragment();
                case FinanceFragment.POSITION:
                    return new FinanceFragment();
//                case ScheduleFragment.POSITION:
//                    return new ScheduleFragment();
                case PlaceFragment.POSITION:
                    return new PlaceFragment();
                case DocumentFragment.POSITION:
                    return new DocumentFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            invalidateOptionsMenu();
            switch (position) {
                case TravelFragment.POSITION:
                    return TravelFragment.NAME_TAB.toUpperCase();
                case NoteFragment.POSITION:
                    return NoteFragment.NAME_TAB.toUpperCase();
                case FinanceFragment.POSITION:
                    return FinanceFragment.NAME_TAB.toUpperCase();
//                case ScheduleFragment.POSITION:
//                    return ScheduleFragment.NAME_TAB.toUpperCase();
                case PlaceFragment.POSITION:
                    return PlaceFragment.NAME_TAB.toUpperCase();
                case DocumentFragment.POSITION:
                    return DocumentFragment.NAME_TAB.toUpperCase();

            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        new SimpleAlertDialog(this, "My First Travel", "Do you want to exit application?") {
            @Override
            public void onDialogReturn(boolean isPositive) {
                if (!isPositive){
                    finish();
                    System.exit(0);
                }
            }
        };
    }

}
