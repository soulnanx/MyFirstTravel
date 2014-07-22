package br.com.epicdroid.travel.dialogs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Document;
import br.com.epicdroid.travel.fragment.DocumentFragment;

public class DialogCreateDocument extends DialogFragment {

    private static final int RESULT_OK = 1;
    private static final int REQUEST_DETALHE_NOTIFICACAO = 0;
    private static final int RESULT_LOAD_IMAGE = 2;
    UIHelper uiHelper;
    SqlAdapter adapter;
    Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_create_document, container);
        init();
        initEvents();
        return view;
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

    private void saveDocument() {
        adapter.store(new Document());
//        fragment.setList();
        DialogCreateDocument.this.dismiss();
    }

    private void init() {
        uiHelper = new UIHelper();
        adapter = Persistence.getAdapter(context);
    }

    private void onClickSearchOnGallery() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (data != null) {
                try {
                    String path = searchImageByQuery(data);
                    drawableFromUrl(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String searchImageByQuery(Intent data) throws Exception {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = DialogCreateDocument.this.getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String pathImage = cursor.getString(columnIndex);
        cursor.close();

        return pathImage;
    }

    public void drawableFromUrl(String url) throws IOException {
//        String sdcardPath = Environment.getExternalStorageDirectory().toString();
        File imgFile = new  File(url);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            uiHelper.imageDoc.setImageBitmap(myBitmap);
        }
    }

    private class UIHelper implements Validator.ValidationListener {
        final Validator validator;

        @Required(order = 1, message = "it's mandatory =(")
        EditText title;

        @Required(order = 2, message = "it's mandatory =(")
        EditText description;

        ImageView imageDoc;

        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper() {
            this.title = (EditText) view.findViewById(R.id.document_create_dialog_edt_title);
            this.description = (EditText) view.findViewById(R.id.document_create_dialog_edt_description);
            this.imageDoc = (ImageView) view.findViewById(R.id.document_create_dialog_img);
            this.btnOK = (LinearLayout) view.findViewById(R.id.document_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout) view.findViewById(R.id.document_create_dialog_btn_cancel);

            validator = new Validator(this);
            validator.setValidationListener(this);
        }

        public void onValidationSucceeded() {
            saveDocument();
        }

        public void onValidationFailed(View failedView, Rule<?> failedRule) {
            String message = failedRule.getFailureMessage();

            if (failedView instanceof EditText) {
                failedView.requestFocus();
                ((EditText) failedView).setError(message);
            } else {
                Toast.makeText( DialogCreateDocument.this.getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
