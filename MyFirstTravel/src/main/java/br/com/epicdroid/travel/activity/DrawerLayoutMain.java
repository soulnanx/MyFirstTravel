package br.com.epicdroid.travel.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import br.com.epicdroid.travel.pojo.MyMenuItem;
import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.adapter.MenuAdapter;
import br.com.epicdroid.travel.fragment.DocumentFragment;
import br.com.epicdroid.travel.fragment.FinanceFragment;
import br.com.epicdroid.travel.fragment.NoteFragment;
import br.com.epicdroid.travel.fragment.PlaceFragment;
import br.com.epicdroid.travel.fragment.TravelFragment;

public class DrawerLayoutMain extends FragmentActivity {
    private android.support.v4.widget.DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerLinear;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private ListView mListView;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLinear = (LinearLayout) findViewById(R.id.left_drawer);
        mListView = (ListView) findViewById(R.id.list_menu);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mListView.setAdapter(new MenuAdapter(this, R.layout.drawer_list_item, new MyMenuItem().getItemsMenu()));
        mListView.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.abc_action_bar_home_description,  /* "open drawer" description for accessibility */
                R.string.abc_action_bar_home_description  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            if (mDrawerLayout.isDrawerOpen(mDrawerLinear)){
                mDrawerLayout.closeDrawer(mDrawerLinear);
            } else {
                mDrawerLayout.openDrawer(mDrawerLinear);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Bundle args = new Bundle();
        Fragment fragment = new TravelFragment();
        switch (position) {
            case TravelFragment.POSITION:
                args.putInt(String.valueOf(TravelFragment.POSITION), position);
                fragment = new TravelFragment();
                break;
            case NoteFragment.POSITION:
                args.putInt(String.valueOf(NoteFragment.POSITION), position);
                fragment = new NoteFragment();
                break;
            case FinanceFragment.POSITION:
                args.putInt(String.valueOf(FinanceFragment.POSITION), position);
                fragment = new FinanceFragment();
                break;
            case PlaceFragment.POSITION:
                args.putInt(String.valueOf(PlaceFragment.POSITION), position);
                fragment = new PlaceFragment();
                break;
            case DocumentFragment.POSITION:
                args.putInt(String.valueOf(DocumentFragment.POSITION), position);
                fragment = new DocumentFragment();
                break;
        }

        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        mListView.setItemChecked(position, true);
//        setTitle(item_menu[position]);
        mDrawerLayout.closeDrawer(mDrawerLinear);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}