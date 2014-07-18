package br.com.epicdroid.travel.delegate;

import com.google.android.gms.maps.model.LatLng;

public interface DelegateReturnLatLong {
    void onReturn(LatLng latLng);
}
