package br.com.epicdroid.travel.entity;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.math.BigDecimal;

@ParseClassName("Debit")
public class Debit extends ParseObject {

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String VALUE = "value";


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
