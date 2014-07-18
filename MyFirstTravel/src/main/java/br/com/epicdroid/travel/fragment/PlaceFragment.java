package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.application.app;
import br.com.epicdroid.travel.asyncTask.AddressToLatLongAsyncTask;
import br.com.epicdroid.travel.asyncTask.LatLongToAddressAsyncTask;
import br.com.epicdroid.travel.delegate.DelegateReturnAddress;
import br.com.epicdroid.travel.delegate.DelegateReturnLatLong;
import br.com.epicdroid.travel.dialogs.DialogCreatePlace;
import br.com.epicdroid.travel.entity.Place;
import br.com.epicdroid.travel.utils.AddressDTO;
import br.com.epicdroid.travel.utils.KeyboardUtils;

public class PlaceFragment extends Fragment {

    public static final int POSITION = 4;
    public static final String NAME_TAB = "places";
    private View view;
    private SupportMapFragment mMapFragment;
    private app application;
    private GoogleMap map;
    private PlaceFragment fragment;
    private static final String BUNDLE_PLACE = "place";
    public static final float ZOOM_DEFAULT = 15.0f;
    public static final float ZOOM_CLOSE = 18.0f;
    public static final float ZOOM_TO_OPEN_PLACE = 20.0f;
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
        uiHelper.address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (application.isInternetConnection(fragment.getActivity())
                            && application.isGPSEnable(fragment.getActivity())) {
                        findByAddress(uiHelper.address.getText().toString());
                        KeyboardUtils.closeSoftKeyBoard(PlaceFragment.this.getActivity(), uiHelper.address);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void findByAddress(String sAddress){
        new AddressToLatLongAsyncTask(
                fragment.getActivity(),
                sAddress,
                new DelegateReturnLatLong() {
                    @Override
                    public void onReturn(LatLng latLng) {
                        moveCameraMap(latLng, ZOOM_CLOSE);
                    }
                }).execute();
    }

    private void findByLatLong(LatLng latLng, final Place newPlace){
        new LatLongToAddressAsyncTask(
                fragment.getActivity(),
                latLng,
                new DelegateReturnAddress() {
                    @Override
                    public void onReturn(AddressDTO address) {
                        newPlace.setAddress(address.toString());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showDialog(newPlace);
                            }
                        }, 500);


                    }
                }).execute();
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
        }
    }

    public void removePinNewPlace(){
        if (marker != null){
            marker.remove();
        }
    }

    @Override
    public void onStart() {
        map = mMapFragment.getMap();
        map.setMyLocationEnabled(true);
        map.setOnMapLongClickListener(eventOnMapClick());
        map.setOnInfoWindowClickListener(eventOnInfoViewClick());

        if (application.isInternetConnection(this.getActivity())
                && application.isGPSEnable(this.getActivity())) {
            moveCameraMap(new LatLng(application.gps.getLatitude(), application.gps.getLongitude()), ZOOM_DEFAULT);
        }

        putPinsMap();
        super.onStart();
    }

    private GoogleMap.OnInfoWindowClickListener eventOnInfoViewClick() {
        return new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (application.isInternetConnection(fragment.getActivity())
                        && application.isGPSEnable(fragment.getActivity())) {
//                    Place newPlace = new Place();
//                    newPlace.setLatitude(marker.getPosition().latitude);
//                    newPlace.setLongitde(marker.getPosition().longitude);
//                    newPlace.setAddress(findAddressByMarker(marker));
//                    marker.remove();
//                    showDialog(newPlace);
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

                    marker = map.addMarker(createPinMap(latLng, false));
                    moveCameraMap(moveUpPin(latLng), ZOOM_TO_OPEN_PLACE);

                    final Place newPlace = newPlace(marker);
                    findByLatLong(latLng, newPlace);



                }
            }
        };
    }

    private Place newPlace(Marker marker){
        Place newPlace = new Place();
        newPlace.setLatitude(this.marker.getPosition().latitude);
        newPlace.setLongitde(this.marker.getPosition().longitude);
        return newPlace;
    }

    private LatLng moveUpPin(LatLng latLng){
        return new LatLng(latLng.latitude + 0.000200d, latLng.longitude);
    }

    public void moveCameraMap(LatLng latLng, float zoom) {
        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        map.animateCamera(center);
    }


    private MarkerOptions createPinMap(LatLng latLng, boolean isDraggable, String title) {
        return new MarkerOptions()
                .position(latLng)
                .draggable(isDraggable)
                .title(title);
    }

    private MarkerOptions createPinMap(LatLng latLng, boolean isDraggable) {
        return new MarkerOptions()
                .position(latLng)
                .draggable(isDraggable);
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
//        LinearLayout btnOK;

        public UIHelper() {
            this.address = (EditText) view.findViewById(R.id.place_edt_search);
//            this.btnOK = (LinearLayout) view.findViewById(R.id.place_btn_search);
        }

    }
}
