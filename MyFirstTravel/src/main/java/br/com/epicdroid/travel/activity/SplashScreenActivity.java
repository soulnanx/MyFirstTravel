package br.com.epicdroid.travel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.codeslap.persistence.SqlAdapter;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.application.app;
import br.com.epicdroid.travel.entity.Travel;

public class SplashScreenActivity extends Activity {

    private app application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        application = (app) getApplication();

        if (application.isNotTravelSet()){
            navigateTo(CreateTravelActivity.class);
        } else {
            navigateTo(MainActivity.class);
        }
    }

    private void navigateTo(final Class c) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, c);
                startActivity(intent);
            }
        }, 2000);
    }

}
