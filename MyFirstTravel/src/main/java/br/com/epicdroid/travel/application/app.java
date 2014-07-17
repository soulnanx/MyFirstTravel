package br.com.epicdroid.travel.application;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.PersistenceConfig;
import com.codeslap.persistence.SqlAdapter;

import java.math.BigDecimal;
import java.util.List;

import br.com.epicdroid.travel.entity.Note;
import br.com.epicdroid.travel.entity.Debit;
import br.com.epicdroid.travel.entity.Place;
import br.com.epicdroid.travel.entity.Task;
import br.com.epicdroid.travel.entity.Travel;
import br.com.epicdroid.travel.utils.AlertDialogManagerUtils;
import br.com.epicdroid.travel.utils.ConnectionDetectorUtils;
import br.com.epicdroid.travel.utils.GPSTrackerUtils;

public class app extends Application {

    public SqlAdapter adapter;
    public List<Debit> debitList;
    public Travel travel;
    public  GPSTrackerUtils gps;

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseSpec database = PersistenceConfig.registerSpec(7);
        database.match(Note.class, Debit.class, Place.class, Task.class, Travel.class);
        init();
    }

    private void init() {
        adapter = Persistence.getAdapter(this);
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
