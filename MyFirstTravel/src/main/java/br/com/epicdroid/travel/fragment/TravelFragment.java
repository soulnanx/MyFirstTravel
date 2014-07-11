package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.dialogs.DialogCreateTravel;
import br.com.epicdroid.travel.entity.Debit;
import br.com.epicdroid.travel.entity.Travel;

public class TravelFragment extends Fragment {

    public static final int POSITION = 0;
    public static final String NAME_TAB = "travel";
    private View view;
    private SqlAdapter adapter;
    private List<Debit> debitList;
    private Travel travel;
    private UIHelper uiHelper;

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
        uiHelper = new UIHelper(view);
        findTravel();
        findDebits();
        setFields();
    }

    private void setFields() {
        uiHelper.title.setText(travel.getTitle());
        uiHelper.startTravel.setText(formatDateToField(travel.getStartTravel()));
        uiHelper.finishTravel.setText(formatDateToField(travel.getFinishTravel()));
        uiHelper.daysRemaining.setText(calculateRemainingDays());
        uiHelper.initialMoney.setText(travel.getInitialMoney());
        uiHelper.totalDebits.setText(calculateTotalDebits());
        uiHelper.currentMoney.setText(calculateCurrentMoney());
    }

    private String calculateCurrentMoney() {
        return "";
    }

    private String calculateTotalDebits() {
        return "";
    }

    private String calculateRemainingDays() {
        return "";
    }

    private String formatDateToField(long timeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeMillis);
        return (new SimpleDateFormat("dd MMM yyyy").format(c.getTime()).toUpperCase());
    }

    private void findDebits(){
        debitList = adapter.findAll(Debit.class);
    }

    private void findTravel(){
        travel = adapter.findFirst(new Travel(1));
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
