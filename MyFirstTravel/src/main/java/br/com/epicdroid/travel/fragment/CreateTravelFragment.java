package br.com.epicdroid.travel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.activity.MainActivity;
import br.com.epicdroid.travel.components.DialogDatePicker;
import br.com.epicdroid.travel.entity.Travel;

public class CreateTravelFragment extends Fragment {

    private View view;
    private SqlAdapter adapter;
    private UIHelper uiHelper;
    private Travel travel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_travel, container, false);

        init();
        initEvents();
        return view;
    }

    private void init() {
        uiHelper = new UIHelper(view);
        adapter = Persistence.getAdapter(this.getActivity());
        travel = new Travel();
    }

    private void initEvents() {
        uiHelper.btnOK.setOnClickListener(eventOK());
        uiHelper.startTravel.setOnClickListener(eventOpenDateDialog());
        uiHelper.finishTravel.setOnClickListener(eventOpenDateDialog());
    }

    private View.OnClickListener eventOpenDateDialog() {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new DialogDatePicker() {
                    @Override
                    public void setDate(Calendar c) {
                        ((TextView) view).setText(new SimpleDateFormat("dd MMM yyyy").format(c.getTime()).toUpperCase());
                        switch (view.getId()) {
                            case R.id.travel_create_dialog_edt_start_travel:
                                travel.setStartTravel(c.getTimeInMillis());
                            case R.id.travel_create_dialog_edt_finish_travel:
                                travel.setFinishTravel(c.getTimeInMillis());
                        }
                    }
                }.show(CreateTravelFragment.this.getActivity().getFragmentManager(), "");
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

    private void createTravel() {
        travel.setTitle(uiHelper.title.getText().toString());
        travel.setInitialMoney(uiHelper.initialMoney.getText().toString());

        adapter.store(travel);
        navigateTo(MainActivity.class);
    }

    private class UIHelper implements Validator.ValidationListener {
        Validator validator;

        @Required(order = 1, message = "it's mandatory =(")
        EditText title;

        @Required(order = 2, message = "it's mandatory =(")
        EditText initialMoney;

        @Required(order = 3, message = "it's mandatory =(")
        TextView startTravel;

        @Required(order = 4, message = "it's mandatory =(")
        TextView finishTravel;

        LinearLayout btnOK;

        public UIHelper(View view) {
            this.title = (EditText) view.findViewById(R.id.travel_create_dialog_edt_title);
            this.initialMoney = (EditText) view.findViewById(R.id.travel_create_dialog_edt_money);
            this.finishTravel = (TextView) view.findViewById(R.id.travel_create_dialog_edt_finish_travel);
            this.startTravel = (TextView) view.findViewById(R.id.travel_create_dialog_edt_start_travel);

            this.btnOK = (LinearLayout) view.findViewById(R.id.travel_create_dialog_btn_ok);
            validator = new Validator(this);
            validator.setValidationListener(this);
        }

        public void onValidationSucceeded() {
            createTravel();
        }

        public void onValidationFailed(View failedView, Rule<?> failedRule) {
            String message = failedRule.getFailureMessage();

            if (failedView instanceof EditText) {
                failedView.requestFocus();
                ((EditText) failedView).setError(message);
            } else if (failedView instanceof TextView) {
                failedView.requestFocus();
                ((TextView) failedView).setError("it's mandatory =(");
            } else {
                Toast.makeText(CreateTravelFragment.this.getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateTo(final Class c) {
        Intent intent = new Intent(CreateTravelFragment.this.getActivity(), c);
        startActivity(intent);
    }


}
