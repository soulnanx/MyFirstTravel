package br.com.epicdroid.travel.fragment;


import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.application.app;
import br.com.epicdroid.travel.dialogs.DialogCreatePlace;
import br.com.epicdroid.travel.entity.Place;
import br.com.epicdroid.travel.utils.AddressDTO;

public class PlaceFragment extends Fragment {

    public static final int POSITION = 4;
    public static final String NAME_TAB = "places";
    private View view;
    private SupportMapFragment mMapFragment;
    private app application;
    private GoogleMap map;
    private PlaceFragment fragment;
    private static final String BUNDLE_PLACE = "place";
    private static final float ZOOM_DEFAULT = 15.0f;
    private static final float ZOOM_CLOSE = 18.0f;
    private Marker marker;
    private List<Place> placeList;
    private UIHelper uiHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_places, container, false);
        init();
        initEvents();

        return view;
    }

    private void initEvents() {
        uiHelper.btnOK.setOnClickListener(eventOnClickSearch());
    }

    private View.OnClickListener eventOnClickSearch() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (application.isInternetConnection(fragment.getActivity())
                        && application.isGPSEnable(fragment.getActivity())) {
                    new FindPlaceAsyncTask().execute();
                }
            }
        };
    }


    private void init() {
        uiHelper = new UIHelper();
        setHasOptionsMenu(true);
        application = (app) this.getActivity().getApplication();
        fragment = this;
        createMap();
    }

    public void putPinsMap() {
        placeList = application.adapter.findAll(Place.class);
        if (!placeList.isEmpty()) {
            for (Place placeItem : placeList) {
                map.addMarker(createPinMap(new LatLng(placeItem.getLatitude(), placeItem.getLongitde()),
                        false,
                        placeItem.getTitle()));
            }
            moveCameraMap(new LatLng(placeList.get(0).getLatitude(), placeList.get(0).getLongitde()), ZOOM_DEFAULT);
        }
    }

    @Override
    public void onStart() {
        map = mMapFragment.getMap();
        map.setMyLocationEnabled(true);
        map.setOnMapLongClickListener(eventOnMapClick());
        map.setOnInfoWindowClickListener(eventOnInfoViewClick());
        putPinsMap();
        super.onStart();
    }

    private GoogleMap.OnInfoWindowClickListener eventOnInfoViewClick() {
        return new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (application.isInternetConnection(fragment.getActivity())
                        && application.isGPSEnable(fragment.getActivity())) {
                    Place newPlace = new Place();
                    newPlace.setLatitude(marker.getPosition().latitude);
                    newPlace.setLongitde(marker.getPosition().longitude);
                    newPlace.setAddress(findAddressByMarker(marker));
                    marker.remove();
                    showDialog(newPlace);
                }
            }
        };
    }

    private GoogleMap.OnMapLongClickListener eventOnMapClick() {
        return new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (application.isInternetConnection(fragment.getActivity())
                        && application.isGPSEnable(fragment.getActivity())) {
                    if (marker != null) {
                        marker.remove();
                    }

                    marker = map.addMarker(createPinMap(latLng, true, "Create a new place?"));
                    marker.showInfoWindow();
                    moveCameraMap(latLng, ZOOM_CLOSE);
                }
            }
        };
    }

    private void moveCameraMap(LatLng latLng, float zoom) {
        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        map.animateCamera(center);
    }

    private String findAddressByMarker(Marker marker) {
        String address = "";
        Geocoder geocoder = new Geocoder(fragment.getActivity());
        try {
            AddressDTO dto = AddressDTO.fromAddressToAddressDTO(
                    geocoder.getFromLocation(
                            marker.getPosition().latitude,
                            marker.getPosition().longitude, 1).get(0)
            );
            if (dto != null) {
                address = dto.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    private LatLng findByAddress(String name) {
        Geocoder geocoder = new Geocoder(fragment.getActivity());
        try {
            Address address = geocoder.getFromLocationName(name, 1).get(0);
            if (address != null) {
                return new LatLng(address.getLatitude(), address.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private MarkerOptions createPinMap(LatLng latLng, boolean isDraggable, String title) {
        return new MarkerOptions()
                .position(latLng)
                .draggable(isDraggable)
                .title(title);
    }


    private void createMap() {
        mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.mapas, mMapFragment).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_new_note:

                if (application.isInternetConnection(this.getActivity())
                        && application.isGPSEnable(this.getActivity())) {
//                    showDialog(null);
                }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog(Place newPlace) {
        application.placeFragment = this;
        FragmentManager fm = getFragmentManager();
        DialogCreatePlace dialogCreatePlace = new DialogCreatePlace();
        Bundle b = new Bundle();
        b.putSerializable(BUNDLE_PLACE, newPlace);
        dialogCreatePlace.setArguments(b);
        dialogCreatePlace.setRetainInstance(true);
        dialogCreatePlace.show(fm, "fragment_name");
    }

    private class UIHelper {
        EditText address;
        LinearLayout btnOK;

        public UIHelper() {
            this.address = (EditText) view.findViewById(R.id.place_edt_search);
            this.btnOK = (LinearLayout) view.findViewById(R.id.place_btn_search);
        }

    }

    public class FindPlaceAsyncTask extends AsyncTask<Void, Void, LatLng> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(fragment.getActivity()); // your activity
            progressDialog.setMessage("looking for address...");
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected LatLng doInBackground(Void... params) {
            return findByAddress(uiHelper.address.getText().toString());
        }

        @Override
        protected void onPostExecute(LatLng latLng) {
            if (latLng != null){
                moveCameraMap(latLng, ZOOM_CLOSE);
            }
            progressDialog.dismiss();
            super.onPostExecute(latLng);
        }

    }


}
