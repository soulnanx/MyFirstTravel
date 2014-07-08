package br.com.epicdroid.travel.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Note;

public class DialogShowNote extends Dialog{

    private UIHelper uiHelper;
    private Context context;
    private Note note;

    public DialogShowNote(Context context, Note note) {
        super(context);
        this.note = note;
        this.context = context;
        init();
        initEvents();
    }

    private void init() {
        this.setContentView(R.layout.dialog_show_note);
        uiHelper = new UIHelper(this);
        this.setTitle(note.getTitle());
        setValues();
    }

    private void initEvents() {
        uiHelper.btnOK.setOnClickListener(eventOK());
    }

    private View.OnClickListener eventOK() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogShowNote.this.dismiss();
            }
        };
    }

    private void setValues(){
        uiHelper.description.setText(note.getDescription());
    }

    private class UIHelper{
        TextView description;
        LinearLayout btnOK;

        public UIHelper(DialogShowNote view) {
            this.description = (TextView)view.findViewById(R.id.note_show_dialog_txt_description);
            this.btnOK = (LinearLayout)view.findViewById(R.id.note_show_dialog_btn_ok);
        }
    }

}
