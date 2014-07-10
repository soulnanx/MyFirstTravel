package br.com.epicdroid.travel.application;

import android.app.Application;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.PersistenceConfig;

import br.com.epicdroid.travel.entity.Note;
import br.com.epicdroid.travel.entity.Debit;
import br.com.epicdroid.travel.entity.Place;
import br.com.epicdroid.travel.entity.Task;
import br.com.epicdroid.travel.entity.Travel;

public class app extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseSpec database = PersistenceConfig.registerSpec(7);
        database.match(Note.class, Debit.class, Place.class, Task.class, Travel.class);
    }
}
