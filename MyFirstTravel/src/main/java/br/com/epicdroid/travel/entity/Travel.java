package br.com.epicdroid.travel.entity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.math.BigDecimal;
import java.util.Date;

@ParseClassName("Travel")
public class Travel extends ParseObject{

    private static final String TRAVEL_LOCAL = "travel_local";
    private static final int LIMIT_RESULT = 100;

    private static final String TITLE = "title";
    private static final String INITIAL_MONEY = "initialMoney";
    private static final String START_TRAVEL = "startTravel";
    private static final String FINISH_TRAVEL = "finishTravel";

    public void saveLocal(SaveCallback callback){
        pinInBackground(TRAVEL_LOCAL, callback);
    }

    public static ParseQuery findAllLocal(FindCallback<Travel> callback){
        ParseQuery<Travel> query = ParseQuery.getQuery(Travel.class);
        query.fromLocalDatastore();
        query.setLimit(LIMIT_RESULT);
        query.findInBackground(callback);
        return query;
    }

    public static ParseQuery findFirstLocal(GetCallback<Travel> callback){
        ParseQuery<Travel> query = ParseQuery.getQuery(Travel.class);
        query.fromLocalDatastore();
        query.setLimit(1);
        query.getFirstInBackground(callback);
        return query;
    }

    public String getTitle() {
        return getString(TITLE);
    }

    public BigDecimal getInitialMoney() {
        return new BigDecimal(getDouble(INITIAL_MONEY));
    }

    public Date getStartTravel() {
        return getDate(START_TRAVEL);
    }

    public Date getFinishTravel() {
        return getDate(FINISH_TRAVEL);
    }

    public void setTitle(String title) {
        put(TITLE, title);
    }

    public void setInitialMoney(Double initialMoney) {
        put(INITIAL_MONEY, initialMoney);
    }

    public void setStartTravel(long startTravel) {
        put(START_TRAVEL, new Date(startTravel));
    }

    public void setFinishTravel(long finishTravel) {
        put(FINISH_TRAVEL, new Date(finishTravel));
    }

    public void setInitialMoney(String money) {
        setInitialMoney(new Double(money));
    }
}
