package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.dialogs.DialogCreateTravel;
import br.com.epicdroid.travel.entity.Travel;

public class TravelFragment extends Fragment {

    public static final int POSITION = 0;
    public static final String NAME_TAB = "travel";
    private View view;
    private SqlAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        adapter = Persistence.getAdapter(TravelFragment.this.getActivity());

        if (isNotTravelSet()){
            view = inflater.inflate(R.layout.fragment_no_travel, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_travel, container, false);
            init();
        }

        return view;
    }

    private boolean isNotTravelSet() {
        try {
            if (adapter.findAll(Travel.class).isEmpty()){
                return true;
            }
        } catch (Exception e){return true;}

        return false;
    }


    private void init() {
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
                new DialogCreateTravel(TravelFragment.this.getActivity(), this).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
