package br.com.epicdroid.travel.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Place;
import br.com.epicdroid.travel.fragment.PlaceFragment;

public class DialogCreatePlace extends DialogFragment {

    private UIHelper uiHelper;
    private SqlAdapter adapter;
    private Context context;
    private PlaceFragment fragment;
    private Place place;
    private SupportMapFragment mMapFragment;
    private View view;

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
                DialogCreatePlace.this.dismiss();
            }
        };
    }

    private void createPlace() {
        savePlace();
        DialogCreatePlace.this.dismiss();
    }

    private void createMap() {
        mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction = this.getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map_places, mMapFragment).commit();
    }

    private void savePlace() {
        place.setTitle(uiHelper.title.getText().toString());
        adapter.store(place);
    }


    private class UIHelper implements Validator.ValidationListener {
        Validator validator;

        @Required(order = 1, message = "it's mandatory =(")
        EditText title;
        EditText address;

        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper() {
            this.title = (EditText) view.findViewById(R.id.place_create_dialog_title);
            this.address = (EditText) view.findViewById(R.id.place_create_dialog_address);
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
