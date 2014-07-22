package br.com.epicdroid.travel.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public abstract class SimpleAlertDialog {

    public SimpleAlertDialog(final Context context,
                             String title,
                             String message){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogReturn(false);
                    }
                }
        );

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogReturn(true);
                    }
                }
        );

        alertDialog.show();
    }

    public abstract void onDialogReturn(boolean isPositive);
}
