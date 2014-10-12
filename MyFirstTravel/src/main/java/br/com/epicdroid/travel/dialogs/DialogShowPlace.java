package br.com.epicdroid.travel.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.adapter.PlaceAdapter;
import br.com.epicdroid.travel.entity.Place;

public class DialogShowPlace extends DialogFragment {

    private UIHelper uiHelper;
    private SqlAdapter adapter;
    private DialogShowPlace fragment;
    private Place place;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_show_place, container);
        init();
//        initEvents();
        return view;
    }

    private void init() {
        fragment = this;
        uiHelper = new UIHelper();
        adapter = Persistence.getAdapter(fragment.getActivity());
        getDialog().setTitle("My places");
        setFields();
    }

    private void setFields(){
        uiHelper.placeList.setAdapter(
                new PlaceAdapter(
                        fragment.getActivity(),
                        R.layout.item_place,
                        adapter.findAll(Place.class)));
    }

//    private void initEvents() {
//        uiHelper.btnClose.setOnClickListener(eventClose());
//    }

    private View.OnClickListener eventClose() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogShowPlace.this.dismiss();
            }
        };
    }

        private class UIHelper {
        ListView placeList;

        public UIHelper() {
            this.placeList = (ListView) view.findViewById(R.id.place_list);

        }
    }


}
