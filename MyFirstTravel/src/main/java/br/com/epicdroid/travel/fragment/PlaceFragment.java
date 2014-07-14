package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.dialogs.DialogCreatePlace;

public class PlaceFragment extends Fragment {

    public static final int POSITION = 4;
    public static final String NAME_TAB = "places";
    private View view;
    private SupportMapFragment mMapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_places, container, false);
        setHasOptionsMenu(true);
        init();
        return view;
    }

    private void init() {
        createMap();
    }

    private void createMap(){
        mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.mapas, mMapFragment).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_new_note:
                new DialogCreatePlace(PlaceFragment.this.getActivity(), this).show();
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//
//        try {
//            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//            ft.remove(mMapFragment);
//            ft.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
