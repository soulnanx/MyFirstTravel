package br.com.epicdroid.travel.dialogs;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.components.DialogDatePicker;
import br.com.epicdroid.travel.entity.Travel;
import br.com.epicdroid.travel.fragment.TravelFragment;

public class DialogCreateTravel extends Dialog{

    private UIHelper uiHelper;
    private SqlAdapter adapter;
    private Context context;
    private TravelFragment fragment;
    private Travel travel;

    public DialogCreateTravel(Context context, TravelFragment fragment) {
        super(context);
        this.context = context;
        this.fragment = fragment;
        init();
        initEvents();
    }

    private void init() {
        this.setContentView(R.layout.fragment_create_travel);
        uiHelper = new UIHelper(this);
        this.setTitle(context.getString(R.string.dialog_create_debit_title));
        adapter = Persistence.getAdapter(context);
        travel = new Travel();
    }

    private void initEvents() {
        uiHelper.btnOK.setOnClickListener(eventOK());
        uiHelper.btnCancel.setOnClickListener(eventCancel());
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
                        ((TextView)view).setText(new SimpleDateFormat("dd MMM yyyy").format(c.getTime()).toUpperCase());
                        switch (view.getId()){
                            case R.id.travel_create_dialog_edt_start_travel:
                                travel.setStartTravel(c.getTimeInMillis());
                            case R.id.travel_create_dialog_edt_finish_travel:
                                travel.setFinishTravel(c.getTimeInMillis());
                        }
                    }
                }.show(fragment.getActivity().getFragmentManager(), "");
            }
        };
    }

    private View.OnClickListener eventCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCreateTravel.this.dismiss();
            }
        };
    }

    private View.OnClickListener eventOK() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDebit();
                fragment.refresh();
                DialogCreateTravel.this.dismiss();
            }
        };
    }

    private void createDebit() {
        travel.setTitle(uiHelper.title.getText().toString());
        travel.setInitialMoney(uiHelper.initialMoney.getText().toString());

        adapter.store(travel);
    }

    private class UIHelper{
        EditText title;
        TextView startTravel;
        TextView finishTravel;
        EditText initialMoney;

        LinearLayout btnOK;
        LinearLayout btnCancel;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public UIHelper(DialogCreateTravel view) {
            this.title = (EditText)view.findViewById(R.id.travel_create_dialog_edt_title);
            this.initialMoney = (EditText)view.findViewById(R.id.travel_create_dialog_edt_money);
            this.finishTravel = (TextView)view.findViewById(R.id.travel_create_dialog_edt_finish_travel);
            this.startTravel = (TextView)view.findViewById(R.id.travel_create_dialog_edt_start_travel);

            this.btnOK = (LinearLayout)view.findViewById(R.id.travel_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout)view.findViewById(R.id.travel_create_dialog_btn_cancel);
        }
    }

}
