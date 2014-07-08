package br.com.epicdroid.travel.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Note;
import br.com.epicdroid.travel.fragment.NoteFragment;

public class DialogCreateNote extends Dialog{

    UIHelper uiHelper;
    SqlAdapter adapter;
    Context context;
    NoteFragment fragment;

    public DialogCreateNote(Context context, NoteFragment fragment) {
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
                DialogCreateNote.this.dismiss();
            }
        };
    }

    private View.OnClickListener eventOK() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note();
                note.setDescription(uiHelper.description.getText().toString());
                note.setTitle(uiHelper.title.getText().toString());

                adapter.store(note);
                fragment.setList();
                DialogCreateNote.this.dismiss();
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
            this.title = (EditText)view.findViewById(R.id.note_create_dialog_edt_title);
            this.description = (EditText)view.findViewById(R.id.note_create_dialog_edt_description);
            this.btnOK = (LinearLayout)view.findViewById(R.id.note_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout)view.findViewById(R.id.note_create_dialog_btn_cancel);
        }
    }

}
