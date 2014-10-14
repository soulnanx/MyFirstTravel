package br.com.epicdroid.travel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.application.App;
import br.com.epicdroid.travel.entity.Debit;
import br.com.epicdroid.travel.entity.Travel;

public class SplashScreenActivity extends Activity {

    private App application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        application = (App) getApplication();
        Travel.findAllLocal(eventFindTravelCallback());

    }

    private FindCallback<Travel> eventFindTravelCallback() {
        return new FindCallback<Travel>() {
            @Override
            public void done(List<Travel> travels, ParseException e) {
                    if (travels.isEmpty()){

                        navigateTo(CreateTravelActivity.class);
                    } else {
                        application.travel = travels.get(0);
                        Debit.findAllLocal(findAllDebitCallback());

                    }
            }
        };
    }

    private FindCallback<Debit> findAllDebitCallback() {
        return new FindCallback<Debit>() {
            @Override
            public void done(List<Debit> debits, ParseException e) {
                application.debitList = debits;
                navigateTo(DrawerLayoutMain.class);
            }
        };
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
