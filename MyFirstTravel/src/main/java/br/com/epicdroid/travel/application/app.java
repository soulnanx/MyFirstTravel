package br.com.epicdroid.travel.application;

import android.app.Application;

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

public class app extends Application {

    public SqlAdapter adapter;
    public List<Debit> debitList;
    public Travel travel;

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
}
