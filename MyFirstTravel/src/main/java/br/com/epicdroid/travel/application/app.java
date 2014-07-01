package br.com.epicdroid.travel.application;

import android.app.Application;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.PersistenceConfig;

import br.com.epicdroid.travel.entity.Note;
import br.com.epicdroid.travel.entity.Debit;

public class app extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseSpec database = PersistenceConfig.registerSpec(/**db version**/2);
        database.match(Note.class, Debit.class);
    }
}
