package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

import br.com.epicdroid.travel.R;

public class CreateTravelFragment extends Fragment {

    private View view;
    private SqlAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_create_travel, container, false);

        init();
        return view;
    }

    private void init() {
        setHasOptionsMenu(true);
        adapter = Persistence.getAdapter(CreateTravelFragment.this.getActivity());
    }


}
