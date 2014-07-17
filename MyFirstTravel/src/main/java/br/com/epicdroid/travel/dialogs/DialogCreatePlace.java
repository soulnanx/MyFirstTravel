package br.com.epicdroid.travel.dialogs;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import java.io.IOException;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.application.app;
import br.com.epicdroid.travel.entity.Place;
import br.com.epicdroid.travel.fragment.PlaceFragment;
import br.com.epicdroid.travel.utils.AddressDTO;

public class DialogCreatePlace extends DialogFragment {

    private UIHelper uiHelper;
    private SqlAdapter adapter;
    private DialogCreatePlace fragment;
    private static final String BUNDLE_PLACE = "place";
    private Place place;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_create_place, container);
        init();
        initEvents();
        return view;
    }

    private void init() {
        place = (Place)getArguments().getSerializable(BUNDLE_PLACE);
        fragment = this;
        uiHelper = new UIHelper();
        adapter = Persistence.getAdapter(fragment.getActivity());
        getDialog().setTitle("Create a new place");
        setFields();
    }

    private void setFields(){
        uiHelper.address.setText(place.getAddress());
    }

    private void initEvents() {
        uiHelper.btnOK.setOnClickListener(eventOK());
        uiHelper.btnCancel.setOnClickListener(eventCancel());
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
                uiHelper.validator.validate();
            }
        };
    }

    private void createPlace() {
        savePlace();
        ((app)getActivity().getApplication()).placeFragment.putPinsMap();
        DialogCreatePlace.this.dismiss();
    }

    private void savePlace() {
        place.setTitle(uiHelper.title.getText().toString());
        adapter.store(place);
    }


    private class UIHelper implements Validator.ValidationListener {
        Validator validator;

        @Required(order = 1, message = "it's mandatory =(")
        TextView address;

        @Required(order = 2, message = "it's mandatory =(")
        EditText title;

        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper() {
            this.title = (EditText) view.findViewById(R.id.place_create_dialog_title);
            this.address = (TextView) view.findViewById(R.id.place_create_dialog_address);
            this.btnOK = (LinearLayout) view.findViewById(R.id.travel_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout) view.findViewById(R.id.travel_create_dialog_btn_cancel);

            validator = new Validator(this);
            validator.setValidationListener(this);
        }

        public void onValidationSucceeded() {
            createPlace();
        }

        public void onValidationFailed(View failedView, Rule<?> failedRule) {
            String message = failedRule.getFailureMessage();

            if (failedView instanceof EditText) {
                failedView.requestFocus();
                ((EditText) failedView).setError(message);
            } else {
                Toast.makeText(DialogCreatePlace.this.getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
