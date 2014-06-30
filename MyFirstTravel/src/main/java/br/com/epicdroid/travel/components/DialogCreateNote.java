package br.com.epicdroid.travel.components;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Note;

public class DialogCreateNote extends Dialog{

    UIHelper uiHelper;
    SqlAdapter adapter;
    Context context;

    public DialogCreateNote(Context context) {
        super(context);
        this.context = context;
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
                DialogCreateNote.this.dismiss();
            }
        };
    }

    private View.OnClickListener eventOK() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note annotation = new Note();
                annotation.setDescription(uiHelper.description.getText().toString());
                annotation.setTitle(uiHelper.title.getText().toString());

                adapter.store(annotation);
            }
        };
    }

    private void init() {
        this.setContentView(R.layout.dialog_create_note);
        uiHelper = new UIHelper(this);
        this.setTitle(context.getString(R.string.dialog_create_note_title));
        adapter = Persistence.getAdapter(context);
    }

    private class UIHelper{
        EditText title;
        EditText description;
        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper(DialogCreateNote view) {
            this.title = (EditText)view.findViewById(R.id.edt_title);
            this.description = (EditText)view.findViewById(R.id.edt_description);
            this.btnOK = (LinearLayout)view.findViewById(R.id.dialog_btn_ok);
            this.btnCancel = (LinearLayout)view.findViewById(R.id.dialog_btn_cancel);
        }
    }

}
