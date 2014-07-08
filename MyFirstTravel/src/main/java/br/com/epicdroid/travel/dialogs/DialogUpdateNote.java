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

public class DialogUpdateNote extends Dialog{

    private UIHelper uiHelper;
    private SqlAdapter adapter;
    private Context context;
    private NoteFragment fragment;
    private Note note;

    public DialogUpdateNote(Context context, NoteFragment fragment, Note note) {
        super(context);
        this.context = context;
        this.fragment = fragment;
        this.note = note;
        init();
        initEvents();
    }

    private void init() {
        this.setContentView(R.layout.dialog_update_note);
        uiHelper = new UIHelper(this);
        this.setTitle(context.getString(R.string.dialog_update_note_title));
        adapter = Persistence.getAdapter(context);
        setValues();
    }

    private void initEvents() {
        uiHelper.btnOK.setOnClickListener(eventOK());
        uiHelper.btnCancel.setOnClickListener(eventCancel());
    }

    private void setValues(){
        uiHelper.title.setText(note.getTitle());
        uiHelper.description.setText(note.getDescription());
    }

    private View.OnClickListener eventCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUpdateNote.this.dismiss();
            }
        };
    }

    private View.OnClickListener eventOK() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note noteUpdated = new Note();
                noteUpdated.setId(note.getId());
                noteUpdated.setDescription(uiHelper.description.getText().toString());
                noteUpdated.setTitle(uiHelper.title.getText().toString());

                adapter.update(noteUpdated, note);
                fragment.setList();
                DialogUpdateNote.this.dismiss();
            }
        };
    }

    private class UIHelper{
        EditText title;
        EditText description;
        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper(DialogUpdateNote view) {
            this.title = (EditText)view.findViewById(R.id.note_update_dialog_edt_title);
            this.description = (EditText)view.findViewById(R.id.note_update_dialog_edt_description);
            this.btnOK = (LinearLayout)view.findViewById(R.id.note_update_dialog_btn_ok);
            this.btnCancel = (LinearLayout)view.findViewById(R.id.note_update_dialog_btn_cancel);
        }
    }

}
