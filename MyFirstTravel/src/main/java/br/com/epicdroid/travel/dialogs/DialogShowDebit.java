package br.com.epicdroid.travel.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Debit;

public class DialogShowDebit extends Dialog{

    private UIHelper uiHelper;
    private Context context;
    private Debit debit;

    public DialogShowDebit(Context context, Debit debit) {
        super(context);
        this.debit = debit;
        this.context = context;
        init();
        initEvents();
    }

    private void init() {
        this.setContentView(R.layout.dialog_show_debit);
        uiHelper = new UIHelper(this);
        this.setTitle(debit.getTitle());
        setValues();
    }

    private void initEvents() {
        uiHelper.btnOK.setOnClickListener(eventOK());
    }

    private View.OnClickListener eventOK() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogShowDebit.this.dismiss();
            }
        };
    }

    private void setValues(){
        uiHelper.description.setText(debit.getDescription());
    }

    private class UIHelper{
        TextView description;
        LinearLayout btnOK;

        public UIHelper(DialogShowDebit view) {
            this.description = (TextView)view.findViewById(R.id.note_show_dialog_txt_description);
            this.btnOK = (LinearLayout)view.findViewById(R.id.note_show_dialog_btn_ok);
        }
    }

}
