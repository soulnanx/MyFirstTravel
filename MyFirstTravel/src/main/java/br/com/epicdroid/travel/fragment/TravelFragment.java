package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.application.Sapp;
import br.com.epicdroid.travel.entity.Travel;
import br.com.epicdroid.travel.utils.TextFormatUtils;

public class TravelFragment extends Fragment {

    public static final int POSITION = 0;
    public static final String NAME_TAB = "travel";
    private View view;
    private UIHelper uiHelper;
    private Sapp application;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        application = (Sapp) getActivity().getApplication();

        if (application.isNotTravelSet()) {
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
        application.findDebits();
        setFields();
    }

    private void setFields() {
        uiHelper.title.setText(application.travel.getTitle());

        uiHelper.startTravelDay.setText(TextFormatUtils.formatDateToField(application.travel.getStartTravel(), "dd"));
        uiHelper.startTravelDayWeek.setText(TextFormatUtils.formatDateToField(application.travel.getStartTravel(), "E"));
        uiHelper.startTravelMonth.setText(TextFormatUtils.formatDateToField(application.travel.getStartTravel(), "MMM"));
        uiHelper.startTravelYear.setText(TextFormatUtils.formatDateToField(application.travel.getStartTravel(), "yyyy"));

        uiHelper.finishTravelDay.setText(TextFormatUtils.formatDateToField(application.travel.getFinishTravel(), "dd"));
        uiHelper.finishTravelDayWeek.setText(TextFormatUtils.formatDateToField(application.travel.getFinishTravel(), "E"));
        uiHelper.finishTravelMonth.setText(TextFormatUtils.formatDateToField(application.travel.getFinishTravel(), "MMM"));
        uiHelper.finishTravelYear.setText(TextFormatUtils.formatDateToField(application.travel.getFinishTravel(), "yyyy"));

        uiHelper.daysRemaining.setText(TextFormatUtils.calculateRemainingDays(application.travel.getStartTravel(), application.travel.getFinishTravel()));
        uiHelper.initialMoney.setText(TextFormatUtils.showAsMoney(new BigDecimal(application.travel.getInitialMoney())));
        uiHelper.totalDebits.setText(TextFormatUtils.showAsMoney(application.calculateTotalDebits()));
        uiHelper.currentMoney.setText(TextFormatUtils.showAsMoney(application.calculateCurrentMoney()));
    }

    private void findTravel() {
        application.travel = application.adapter.findFirst(new Travel(1));
    }

    public void refresh() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }

    private class UIHelper {
        TextView title;

        TextView startTravelDay;
        TextView startTravelDayWeek;
        TextView startTravelMonth;
        TextView startTravelYear;

        TextView finishTravelDay;
        TextView finishTravelDayWeek;
        TextView finishTravelMonth;
        TextView finishTravelYear;

        TextView daysRemaining;
        TextView initialMoney;
        TextView totalDebits;
        TextView currentMoney;

        public UIHelper(View view) {
            this.title = (TextView) view.findViewById(R.id.travel_show_title);

            this.startTravelDay = (TextView) view.findViewById(R.id.travel_show_start_travel_txt_day);
            this.startTravelDayWeek = (TextView) view.findViewById(R.id.travel_show_start_travel_txt_day_week);
            this.startTravelMonth = (TextView) view.findViewById(R.id.travel_show_start_travel_txt_month);
            this.startTravelYear = (TextView) view.findViewById(R.id.travel_show_start_travel_txt_year);

            this.finishTravelDay = (TextView) view.findViewById(R.id.travel_show_finish_travel_txt_day);
            this.finishTravelDayWeek = (TextView) view.findViewById(R.id.travel_show_finish_travel_txt_day_week);
            this.finishTravelMonth = (TextView) view.findViewById(R.id.travel_show_finish_travel_txt_month);
            this.finishTravelYear = (TextView) view.findViewById(R.id.travel_show_finish_travel_txt_year);

            this.daysRemaining = (TextView) view.findViewById(R.id.travel_show_days_remaining);
            this.initialMoney = (TextView) view.findViewById(R.id.travel_show_initial_money);
            this.totalDebits = (TextView) view.findViewById(R.id.travel_show_total_debits);
            this.currentMoney = (TextView) view.findViewById(R.id.travel_show_current_money);
        }
    }
}
