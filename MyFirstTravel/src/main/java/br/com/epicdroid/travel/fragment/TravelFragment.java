package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.application.app;
import br.com.epicdroid.travel.dialogs.DialogCreateTravel;
import br.com.epicdroid.travel.entity.Debit;
import br.com.epicdroid.travel.entity.Travel;
import br.com.epicdroid.travel.utils.TextFormatUtils;

public class TravelFragment extends Fragment {

    public static final int POSITION = 0;
    public static final String NAME_TAB = "travel";
    private View view;
    private UIHelper uiHelper;
    private app application;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        application = (app) getActivity().getApplication();

        if (application.isNotTravelSet()){
            view = inflater.inflate(R.layout.fragment_no_travel, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_travel, container, false);
            init();
        }

        return view;
    }

    private void init() {
        uiHelper = new UIHelper(view);
        findTravel();
        findDebits();
        setFields();
    }

    private void setFields() {
        uiHelper.title.setText(application.travel.getTitle());
        uiHelper.startTravel.setText(TextFormatUtils.formatDateToField(application.travel.getStartTravel()));
        uiHelper.finishTravel.setText(TextFormatUtils.formatDateToField(application.travel.getFinishTravel()));
        uiHelper.daysRemaining.setText(TextFormatUtils.calculateRemainingDays(application.travel.getFinishTravel(), application.travel.getStartTravel()));
        uiHelper.initialMoney.setText(TextFormatUtils.showAsMoney(new BigDecimal(application.travel.getInitialMoney())));
        uiHelper.totalDebits.setText(TextFormatUtils.showAsMoney(application.calculateTotalDebits()));
        uiHelper.currentMoney.setText(TextFormatUtils.showAsMoney(application.calculateCurrentMoney()));
    }

    private void findDebits(){
        application.debitList = application.adapter.findAll(Debit.class);
    }

    private void findTravel(){
        application.travel = application.adapter.findFirst(new Travel(1));
    }

    public void refresh(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }

    private class UIHelper{
        TextView title;
        TextView startTravel;
        TextView finishTravel;
        TextView daysRemaining;
        TextView initialMoney;
        TextView totalDebits;
        TextView currentMoney;

        public UIHelper(View view) {
            this.title = (TextView)view.findViewById(R.id.travel_show_title);
            this.startTravel = (TextView)view.findViewById(R.id.travel_show_start_travel);
            this.finishTravel = (TextView)view.findViewById(R.id.travel_show_finish_travel);
            this.daysRemaining = (TextView)view.findViewById(R.id.travel_show_days_remaining);
            this.initialMoney = (TextView)view.findViewById(R.id.travel_show_initial_money);
            this.totalDebits = (TextView)view.findViewById(R.id.travel_show_total_debits);
            this.currentMoney = (TextView)view.findViewById(R.id.travel_show_current_money);
        }
    }
}
