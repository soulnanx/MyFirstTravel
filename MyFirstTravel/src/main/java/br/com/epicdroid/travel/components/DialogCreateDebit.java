package br.com.epicdroid.travel.components;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

import java.math.BigDecimal;
import java.text.NumberFormat;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.adapter.NoteAdapter;
import br.com.epicdroid.travel.entity.Debit;
import br.com.epicdroid.travel.entity.Note;
import br.com.epicdroid.travel.fragment.FinanceFragment;
import br.com.epicdroid.travel.fragment.NoteFragment;

public class DialogCreateDebit extends Dialog{

    UIHelper uiHelper;
    SqlAdapter adapter;
    Context context;
    FinanceFragment fragment;

    public DialogCreateDebit(Context context, FinanceFragment fragment) {
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
                DialogCreateDebit.this.dismiss();
            }
        };
    }

    private View.OnClickListener eventOK() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDebit();
                fragment.setList();
                DialogCreateDebit.this.dismiss();
            }
        };
    }

    private void createDebit() {
        Debit debit = new Debit();
        debit.setDescription(uiHelper.description.getText().toString());
        debit.setTitle(uiHelper.title.getText().toString());
        debit.setValue(uiHelper.value.getText().toString());

        adapter.store(debit);
    }

    private void init() {
        this.setContentView(R.layout.dialog_create_debit);
        uiHelper = new UIHelper(this);
        this.setTitle(context.getString(R.string.dialog_create_debit_title));
        adapter = Persistence.getAdapter(context);
    }

    private class UIHelper{
        EditText title;
        EditText description;
        EditText value;
        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper(DialogCreateDebit view) {
            this.title = (EditText)view.findViewById(R.id.debit_create_dialog_edt_title);
            this.description = (EditText)view.findViewById(R.id.debit_create_dialog_edt_description);
            this.value = (EditText)view.findViewById(R.id.debit_create_dialog_edt_value);

            this.btnOK = (LinearLayout)view.findViewById(R.id.debit_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout)view.findViewById(R.id.debit_create_dialog_btn_cancel);
        }
    }



}
