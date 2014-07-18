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

import br.com.epicdroid.travel.delegate.DelegateReturnAddress;
import br.com.epicdroid.travel.utils.AddressDTO;

public class LatLongToAddressAsyncTask extends AsyncTask<Void, Void, AddressDTO> {

    private ProgressDialog progressDialog;
    private Context context;
    private LatLng latLng;
    private DelegateReturnAddress delegate;

    public LatLongToAddressAsyncTask(Context context, LatLng latLng, DelegateReturnAddress delegate) {
        this.context = context;
        this.latLng = latLng;
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
    protected AddressDTO doInBackground(Void... params) {
        return findByLatLong(latLng);
    }

    @Override
    protected void onPostExecute(AddressDTO address) {
        if (address != null){
            delegate.onReturn(address);
        }
        progressDialog.dismiss();
        super.onPostExecute(address);
    }

    private AddressDTO findByLatLong(LatLng latLng) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList = new ArrayList<Address>();
        try {
            for (int i = 0; i < 2; i++) {
                addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                if (addressList.size() > 0){
                    return AddressDTO.fromAddressToAddressDTO(
                            geocoder.getFromLocation(
                                    latLng.latitude,
                                    latLng.longitude, 1).get(0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}