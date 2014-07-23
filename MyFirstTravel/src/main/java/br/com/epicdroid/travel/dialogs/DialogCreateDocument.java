package br.com.epicdroid.travel.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.application.app;
import br.com.epicdroid.travel.entity.Document;
import br.com.epicdroid.travel.utils.ImageUtils;

public class DialogCreateDocument extends DialogFragment {

    private static final int RESULT_LOAD_IMAGE = 2;
    private UIHelper uiHelper;
    private View view;
    private app application;
    private Document document;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_create_document, container);
        init();
        initEvents();
        return view;
    }

    private void init() {
        document = new Document();
        application = (app) this.getActivity().getApplication();
        uiHelper = new UIHelper();
        getDialog().setTitle("Create new document");
    }

    private void initEvents() {
        uiHelper.btnOK.setOnClickListener(eventOK());
        uiHelper.btnCancel.setOnClickListener(eventCancel());
        uiHelper.imageDoc.setOnClickListener(eventClickImg());
    }


    private View.OnClickListener eventClickImg() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSearchOnGallery();
            }
        };
    }

    private View.OnClickListener eventCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCreateDocument.this.dismiss();
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

    private void createDocument() {
        saveDocument();
        application.documentFragment.setList();
        DialogCreateDocument.this.dismiss();
    }

    private void saveDocument() {
        document.setTitle(uiHelper.title.getText().toString());
        document.setDescription(uiHelper.description.getText().toString());
        application.adapter.store(document);
//
    }

    private void onClickSearchOnGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (data != null) {
                try {
                    String path = ImageUtils.searchImageByQuery(DialogCreateDocument.this.getActivity(), data);
                    document.setImagePath(path);
                    uiHelper.imageDoc.setImageBitmap(ImageUtils.getBitmapFromFilePath(path));
                    uiHelper.imgValidation.setText(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class UIHelper implements Validator.ValidationListener {
        final Validator validator;

        @Required(order = 1, message = "it's mandatory =(")
        TextView imgValidation;

        @Required(order = 2, message = "it's mandatory =(")
        EditText title;

        @Required(order = 3, message = "it's mandatory =(")
        EditText description;

        ImageView imageDoc;

        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper() {
            this.title = (EditText) view.findViewById(R.id.document_create_dialog_edt_title);
            this.imgValidation = (TextView) view.findViewById(R.id.document_create_dialog_img_txt);
            this.description = (EditText) view.findViewById(R.id.document_create_dialog_edt_description);
            this.imageDoc = (ImageView) view.findViewById(R.id.document_create_dialog_img);
            this.btnOK = (LinearLayout) view.findViewById(R.id.document_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout) view.findViewById(R.id.document_create_dialog_btn_cancel);

            validator = new Validator(this);
            validator.setValidationListener(this);
        }

        public void onValidationSucceeded() {
            createDocument();
        }

        public void onValidationFailed(View failedView, Rule<?> failedRule) {
            String message = failedRule.getFailureMessage();

            if (failedView instanceof EditText) {
                failedView.requestFocus();
                ((EditText) failedView).setError(message);
            } else if (failedView instanceof TextView) {
                failedView.requestFocus();
                ((TextView) failedView).setError(message);
            } else {
                Toast.makeText(DialogCreateDocument.this.getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
