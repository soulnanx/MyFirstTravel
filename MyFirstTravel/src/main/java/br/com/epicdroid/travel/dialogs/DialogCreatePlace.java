package br.com.epicdroid.travel.dialogs;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;
import com.google.android.gms.maps.SupportMapFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.components.DialogDatePicker;
import br.com.epicdroid.travel.entity.Place;
import br.com.epicdroid.travel.entity.Travel;
import br.com.epicdroid.travel.fragment.PlaceFragment;
import br.com.epicdroid.travel.fragment.TravelFragment;

public class DialogCreatePlace extends DialogFragment{

    private UIHelper uiHelper;
    private SqlAdapter adapter;
    private Context context;
    private PlaceFragment fragment;
    private Place place;
    private SupportMapFragment mMapFragment;
    private View view;

    public DialogCreatePlace() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_create_place, container);
        init();
        return view;
    }

    @Override
    public void onStart() {
        mMapFragment.getMap().setMyLocationEnabled(true);
        super.onStart();
    }

    private void init() {
        uiHelper = new UIHelper();
        adapter = Persistence.getAdapter(context);
        place = new Place();
        getDialog().setTitle("Create a new place");
        createMap();
    }

    private View.OnClickListener eventCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCreatePlace.this.dismiss();
            }
        };
    }

    private View.OnClickListener eventOK() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPlace();
//                fragment.refresh();
                DialogCreatePlace.this.dismiss();
            }
        };
    }

    private void createPlace() {
        place.setTitle(uiHelper.title.getText().toString());
        adapter.store(place);
    }

    private void createMap(){
        mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction = this.getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map_places , mMapFragment).commit();
    }


    private class UIHelper{
        EditText title;
        TextView startTravel;
        TextView finishTravel;
        EditText initialMoney;

        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper() {
//            this.title = (EditText)view.findViewById(R.id.travel_create_dialog_edt_title);
//            this.initialMoney = (EditText)view.findViewById(R.id.travel_create_dialog_edt_money);
//            this.finishTravel = (TextView)view.findViewById(R.id.travel_create_dialog_edt_finish_travel);
//            this.startTravel = (TextView)view.findViewById(R.id.travel_create_dialog_edt_start_travel);
//
            this.btnOK = (LinearLayout) view.findViewById(R.id.travel_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout) view.findViewById(R.id.travel_create_dialog_btn_cancel);
        }
    }



}
