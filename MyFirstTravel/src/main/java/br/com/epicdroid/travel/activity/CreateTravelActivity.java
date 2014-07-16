package br.com.epicdroid.travel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.fragment.CreateTravelFragment;

public class CreateTravelActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_travel);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CreateTravelFragment())
                    .commit();
        }
    }

}
