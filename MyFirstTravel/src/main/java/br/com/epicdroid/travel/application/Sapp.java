package br.com.epicdroid.travel.application;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.PersistenceConfig;
import com.codeslap.persistence.SqlAdapter;
import com.parse.Parse;
import com.parse.ParseObject;

import java.math.BigDecimal;
import java.util.List;

import br.com.epicdroid.travel.constants.ParseConstants;
import br.com.epicdroid.travel.entity.Document;
import br.com.epicdroid.travel.entity.Note;
import br.com.epicdroid.travel.entity.Debit;
import br.com.epicdroid.travel.entity.Place;
import br.com.epicdroid.travel.entity.Task;
import br.com.epicdroid.travel.entity.Travel;
import br.com.epicdroid.travel.fragment.DocumentFragment;
import br.com.epicdroid.travel.fragment.PlaceFragment;
import br.com.epicdroid.travel.utils.AlertDialogManagerUtils;
import br.com.epicdroid.travel.utils.ConnectionDetectorUtils;
import br.com.epicdroid.travel.utils.GPSTrackerUtils;

public class Sapp extends Application {

    public SqlAdapter adapter;
    public List<Debit> debitList;
    public Travel travel;
    public GPSTrackerUtils gps;
    public PlaceFragment placeFragment;
    public DocumentFragment documentFragment;

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseSpec database = PersistenceConfig.registerSpec(7);
        database.match(Note.class, Debit.class, Place.class, Task.class, Travel.class, Document.class);
        init();
    }

    private void init() {
        adapter = Persistence.getAdapter(this);
    }

    private void initParse(){
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Travel.class);
        Parse.initialize(this, ParseConstants.PARSE_APP_ID, ParseConstants.PARSE_CLIENT_KEY);
    }

    public BigDecimal calculateCurrentMoney() {
        BigDecimal totalDebits = calculateTotalDebits();
        BigDecimal initialMoney = new BigDecimal(travel.getInitialMoney());

        return initialMoney.subtract(totalDebits);
    }

    public BigDecimal calculateTotalDebits() {
        BigDecimal totalDebits = new BigDecimal(0);
        for (Debit debit: debitList){
            if(debit.getValue().isEmpty()
                    || null == debit.getValue()
                    || debit.getValue().equals("0")
                    || debit.getValue().equals("")){
                continue;
            }
            totalDebits = totalDebits.add(new BigDecimal(debit.getValue())) ;
        }
        return totalDebits;
    }

    public void findDebits() {
        debitList = adapter.findAll(Debit.class);
    }

    public boolean isNotTravelSet() {
        try {
            if (adapter.findAll(Travel.class).isEmpty()){
                return true;
            }
        } catch (Exception e){return true;}
        return false;
    }

    public boolean isInternetConnection(Activity activity){
        ConnectionDetectorUtils cd = new ConnectionDetectorUtils(this);
        if (!cd.isConnectingToInternet()) {
            new AlertDialogManagerUtils().showAlertDialog(activity, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            return false;
        }
        return true;
    }

    public boolean isGPSEnable(Activity activity){
        gps = new GPSTrackerUtils(activity);

        if (gps.canGetLocation()) {
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {
            new AlertDialogManagerUtils().showAlertDialog(activity, "GPS Status",
                    "Couldn't get location information. Please enable GPS",
                    false);
            return false;
        }

        return true;
    }
}
