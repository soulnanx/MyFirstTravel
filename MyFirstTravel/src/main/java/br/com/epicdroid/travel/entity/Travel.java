package br.com.epicdroid.travel.entity;

import java.util.Date;

public class Travel {
    private long id;
    private String title;
    private String initialMoney;
    private Date startTravel;
    private Date finishTravel;

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

    public Date getStartTravel() {
        return startTravel;
    }

    public void setStartTravel(Date startTravel) {
        this.startTravel = startTravel;
    }

    public Date getFinishTravel() {
        return finishTravel;
    }

    public void setFinishTravel(Date finishTravel) {
        this.finishTravel = finishTravel;
    }
}
