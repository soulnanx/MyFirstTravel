package br.com.epicdroid.travel.entity;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.math.BigDecimal;

@ParseClassName("Debit")
public class Debit extends ParseObject {

    private static final String DEBIT_LOCAL = "debit_local";
    private static final int LIMIT_RESULT = 100;

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String VALUE = "value";

    public static ParseQuery findAllLocal(FindCallback<Debit> callback){
        ParseQuery<Debit> query = ParseQuery.getQuery(Debit.class);
        query.fromLocalDatastore();
        query.setLimit(LIMIT_RESULT);
        query.findInBackground(callback);
        return query;
    }

    public void saveLocal(SaveCallback callback){
        pinInBackground(DEBIT_LOCAL, callback);
    }


    public String getTitle() {
        return getString(TITLE);
    }

    public void setTitle(String title) {
        put(TITLE, title);
    }

    public BigDecimal getValue() {
        return new BigDecimal(getDouble(VALUE));
    }

    public void setValue(String value) {
        put(VALUE, value);
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public void setDescription(String description) {
        put(DESCRIPTION, description);
    }
}
