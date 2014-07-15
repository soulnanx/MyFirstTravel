package br.com.epicdroid.travel.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Note;
import br.com.epicdroid.travel.fragment.NoteFragment;

public class DialogCreateNote extends Dialog {

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
        initValidation();
    }

    private void initValidation() {
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
                uiHelper.validator.validate();
            }
        };
    }

    private void saveNote(){
        adapter.store(new Note(uiHelper.title.getText().toString(), uiHelper.description.getText().toString()));
        fragment.setList();
        DialogCreateNote.this.dismiss();
    }

    private void init() {
        this.setContentView(R.layout.dialog_create_note);
        this.setTitle(context.getString(R.string.dialog_create_note_title));
        uiHelper = new UIHelper(this);
        adapter = Persistence.getAdapter(context);
    }

    private class UIHelper implements Validator.ValidationListener {
        final Validator validator;
        @Required(order = 1, message = "it's incorrect =P")
        EditText title;


        EditText description;
        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper(DialogCreateNote view) {
            this.title = (EditText)view.findViewById(R.id.note_create_dialog_edt_title);
            this.description = (EditText)view.findViewById(R.id.note_create_dialog_edt_description);
            this.btnOK = (LinearLayout)view.findViewById(R.id.note_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout)view.findViewById(R.id.note_create_dialog_btn_cancel);

            validator = new Validator(this);
            validator.setValidationListener(this);
        }

        public void onValidationSucceeded() {
            saveNote();
        }

        public void onValidationFailed(View failedView, Rule<?> failedRule) {
            String message = failedRule.getFailureMessage();

            if (failedView instanceof EditText) {
                failedView.requestFocus();
                ((EditText) failedView).setError(message);
            } else {
                Toast.makeText(DialogCreateNote.this.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
