package br.com.epicdroid.travel.entity;

import java.util.Date;

public class Travel {
    private long id;
    private String title;
    private String initialMoney;
    private long startTravel;
    private long finishTravel;

    public Travel() {}

    public Travel(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInitialMoney() {
        return initialMoney;
    }

    public void setInitialMoney(String initialMoney) {
        this.initialMoney = initialMoney;
    }

    public long getStartTravel() {
        return startTravel;
    }

    public void setStartTravel(long startTravel) {
        this.startTravel = startTravel;
    }

    public long getFinishTravel() {
        return finishTravel;
    }

    public void setFinishTravel(long finishTravel) {
        this.finishTravel = finishTravel;
    }
}
