package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.adapter.NoteAdapter;
import br.com.epicdroid.travel.adapter.PlaceAdapter;
import br.com.epicdroid.travel.application.app;
import br.com.epicdroid.travel.asyncTask.AddressToLatLongAsyncTask;
import br.com.epicdroid.travel.asyncTask.LatLongToAddressAsyncTask;
import br.com.epicdroid.travel.delegate.DelegateReturnAddress;
import br.com.epicdroid.travel.delegate.DelegateReturnLatLong;
import br.com.epicdroid.travel.dialogs.DialogCreatePlace;
import br.com.epicdroid.travel.dialogs.DialogShowPlace;
import br.com.epicdroid.travel.entity.Place;
import br.com.epicdroid.travel.utils.AddressDTO;
import br.com.epicdroid.travel.utils.KeyboardUtils;

public class PlaceFragment extends Fragment {

    public static final int POSITION = 3;
    public static final String NAME_TAB = "places";
    private View view;
    private SupportMapFragment mMapFragment;
    private app application;
    private GoogleMap map;
    private PlaceFragment fragment;
    private static final String BUNDLE_NEW_PLACE = "place";
    private static final String VIEW_MAP = "map";
    private static final String VIEW_LIST = "list";
    private static String CURRENT_VIEW;
    public static final float ZOOM_DEFAULT = 15.0f;
    public static final float ZOOM_CLOSE = 18.0f;
    public static final float ZOOM_TO_OPEN_PLACE = 20.0f;
    private Marker marker;
    private List<Place> placeList;
    private UIHelper uiHelper;
    private Place placeSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_places, container, false);
        init();
        initEvents();
        createMap();

        return view;
    }

    private void init() {
        uiHelper = new UIHelper();
        CURRENT_VIEW = VIEW_MAP;
        setHasOptionsMenu(true);
        application = (app) this.getActivity().getApplication();
        uiHelper.placeListView.setOnItemLongClickListener(eventOnLongClickPlace());
        uiHelper.placeListView.setOnItemClickListener(eventOnClickPlace());
        fragment = this;
        configureActionMode();
    }

    private AdapterView.OnItemLongClickListener eventOnLongClickPlace() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                PlaceFragment.this.placeSelected = ((PlaceAdapter.ItemHolder) view.getTag()).place;

                if (uiHelper.mMode != null) {
                    return false;
                } else {
                    uiHelper.mMode = getActivity().startActionMode(uiHelper.mCallback);
                }

                return false;
            }
        };
    }

    private AdapterView.OnItemClickListener eventOnClickPlace() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Place place = ((PlaceAdapter.ItemHolder) view.getTag()).place;
                changeView();
                moveCameraMap(new LatLng(place.getLatitude(), place.getLongitde()), ZOOM_CLOSE);
            }
        };
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

        uiHelper.address.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    uiHelper.btnErase.setVisibility(View.VISIBLE);
                } else {
                    uiHelper.btnErase.setVisibility(View.INVISIBLE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        uiHelper.btnErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uiHelper.address.getText().clear();
            }
        });
    }

    private void configureActionMode() {

        uiHelper.mCallback = new ActionMode.Callback() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                uiHelper.mMode = null;
            }


            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("1 selected");
                getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.item_menu_delete:
                        deletePlace();
                        mode.finish();
                        break;
                    case R.id.item_menu_edit:
                        updatePlace();
                        mode.finish();
                        break;
                }
                return false;
            }
        };
    }

    private void updatePlace() {
    }

    private void deletePlace() {
        Place p = new Place();
        p.setId(placeSelected.getId());
        Toast.makeText(getActivity().getBaseContext(), placeSelected.getTitle() + " was deleted! (" +
                application.adapter.delete(p) + ")"
                , Toast.LENGTH_LONG).show();
        updatePlaceList();
        putPinsMap();
    }


    private void findByAddress(String sAddress) {
        new AddressToLatLongAsyncTask(
                fragment.getActivity(),
                sAddress,
                new DelegateReturnLatLong() {
                    @Override
                    public void onReturn(LatLng latLng) {
                        moveCameraMap(latLng, ZOOM_CLOSE);
                    }
                }
        ).execute();
    }

    private void updatePlaceList() {
        uiHelper.placeListView.setAdapter(
                new PlaceAdapter(
                        fragment.getActivity(),
                        R.layout.item_place,
                        application.adapter.findAll(Place.class))
        );
    }

    private void findByLatLong(LatLng latLng, final Place newPlace) {
        new LatLongToAddressAsyncTask(
                fragment.getActivity(),
                latLng,
                new DelegateReturnAddress() {
                    @Override
                    public void onReturn(AddressDTO address) {
                        newPlace.setAddress(address.toString());
                        waitAndShowDialog(newPlace);
                    }
                }
        ).execute();
    }

    private void waitAndShowDialog(final Place place) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialogCreatePlace(place);
            }
        }, 500);
    }


    public void putPinsMap() {
        map.clear();
        placeList = application.adapter.findAll(Place.class);
        if (!placeList.isEmpty()) {
            for (Place placeItem : placeList) {
                map.addMarker(createPinMap(new LatLng(placeItem.getLatitude(), placeItem.getLongitde()),
                        false,
                        placeItem.getTitle()));
            }
        }
    }

    public void removePinNewPlace() {
        if (marker != null) {
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
        map.setPadding(0, 120, 0, 0);
        putPinsMap();
        super.onStart();
    }

    private GoogleMap.OnInfoWindowClickListener eventOnInfoViewClick() {
        return new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

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
                    findByLatLong(latLng, newPlace(marker));

                }
            }
        };
    }

    private Place newPlace(Marker marker) {
        Place newPlace = new Place();
        newPlace.setLatitude(this.marker.getPosition().latitude);
        newPlace.setLongitde(this.marker.getPosition().longitude);
        return newPlace;
    }

    private LatLng moveUpPin(LatLng latLng) {
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
            changeView();
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeView() {
        if (CURRENT_VIEW.equals(VIEW_MAP)) {
            CURRENT_VIEW = VIEW_LIST;
            uiHelper.rlList.setVisibility(View.VISIBLE);
            uiHelper.rlMap.setVisibility(View.GONE);
            updatePlaceList();
        } else if (CURRENT_VIEW.equals(VIEW_LIST)) {
            CURRENT_VIEW = VIEW_MAP;
            uiHelper.rlMap.setVisibility(View.VISIBLE);
            uiHelper.rlList.setVisibility(View.GONE);
        }
    }

    private void showDialogCreatePlace(Place newPlace) {
        application.placeFragment = this;
        FragmentManager fm = getFragmentManager();
        DialogCreatePlace dialogCreatePlace = new DialogCreatePlace();
        Bundle b = new Bundle();
        b.putSerializable(BUNDLE_NEW_PLACE, newPlace);
        dialogCreatePlace.setArguments(b);
        dialogCreatePlace.setRetainInstance(true);
        dialogCreatePlace.show(fm, "fragment_name");
    }

    private class UIHelper {
        EditText address;
        ListView placeListView;
        RelativeLayout rlMap;
        RelativeLayout rlList;
        ActionMode.Callback mCallback;
        ActionMode mMode;
        LinearLayout btnErase;

        public UIHelper() {
            this.address = (EditText) view.findViewById(R.id.place_edt_search);
            this.placeListView = (ListView) view.findViewById(R.id.place_list);
            this.rlMap = (RelativeLayout) view.findViewById(R.id.rl_map);
            this.rlList = (RelativeLayout) view.findViewById(R.id.rl_list);
            this.rlList = (RelativeLayout) view.findViewById(R.id.rl_list);
            this.btnErase = (LinearLayout) view.findViewById(R.id.place_btn_erase);
        }

    }
}
