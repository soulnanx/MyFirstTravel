package br.com.epicdroid.travel.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.epicdroid.travel.delegate.DelegateReturnLatLong;

public class AddressToLatLongAsyncTask extends AsyncTask<Void, Void, LatLng> {

    private ProgressDialog progressDialog;
    private Context context;
    private String address;
    private DelegateReturnLatLong delegate;

    public AddressToLatLongAsyncTask(Context context, String address, DelegateReturnLatLong delegate) {
        this.context = context;
        this.address = address;
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context); // your activity
        progressDialog.setMessage("looking for address...");
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    @Override
    protected LatLng doInBackground(Void... params) {
        return findByAddress(address);
    }

    @Override
    protected void onPostExecute(LatLng latLng) {
        if (latLng != null){
            delegate.onReturn(latLng);
        }
        progressDialog.dismiss();
        super.onPostExecute(latLng);
    }

    private LatLng findByAddress(String sAddress){
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList = new ArrayList<Address>();
        try {
            for (int i = 0; i < 2; i++) {
                addressList = geocoder.getFromLocationName(sAddress, 1);
                if (addressList.size() > 0){
                    Address firstAddress = addressList.get(0);
                    return new LatLng(firstAddress.getLatitude(), firstAddress.getLongitude());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}