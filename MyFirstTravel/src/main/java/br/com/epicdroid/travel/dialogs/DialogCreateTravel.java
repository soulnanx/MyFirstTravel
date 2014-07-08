package br.com.epicdroid.travel.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Travel;
import br.com.epicdroid.travel.fragment.TravelFragment;

public class DialogCreateTravel extends Dialog{

    UIHelper uiHelper;
    SqlAdapter adapter;
    Context context;
    TravelFragment fragment;

    public DialogCreateTravel(Context context, TravelFragment fragment) {
        super(context);
        this.context = context;
        this.fragment = fragment;
        init();
        initEvents();
    }

    private void initEvents() {
        uiHelper.btnOK.setOnClickListener(eventOK());
        uiHelper.btnCancel.setOnClickListener(eventCancel());
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
                DialogCreateTravel.this.dismiss();
            }
        };
    }

    private void createDebit() {
        Travel debit = new Travel();
        debit.setTitle(uiHelper.title.getText().toString());
        debit.setInitialMoney(uiHelper.initialMoney.getText().toString());
//        debit.setStartTravel(uiHelper.startTravel.getText().toString());
//        debit.setFinishTravel(uiHelper.finishTravel.getText().toString());
//
        adapter.store(debit);
    }

    private void init() {
        this.setContentView(R.layout.dialog_create_travel);
        uiHelper = new UIHelper(this);
        this.setTitle(context.getString(R.string.dialog_create_debit_title));
        adapter = Persistence.getAdapter(context);
    }

    private class UIHelper{
        EditText title;
        TimePicker startTravel;
        TimePicker finishTravel;
        EditText initialMoney;

        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper(DialogCreateTravel view) {
            this.title = (EditText)view.findViewById(R.id.travel_create_dialog_edt_title);
            this.initialMoney = (EditText)view.findViewById(R.id.travel_create_dialog_edt_money);
            this.finishTravel = (TimePicker)view.findViewById(R.id.travel_create_dialog_edt_finish_travel);
            this.startTravel = (TimePicker)view.findViewById(R.id.travel_create_dialog_edt_start_travel);

            this.btnOK = (LinearLayout)view.findViewById(R.id.travel_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout)view.findViewById(R.id.travel_create_dialog_btn_cancel);
        }
    }



}
