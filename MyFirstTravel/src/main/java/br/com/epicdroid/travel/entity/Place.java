package br.com.epicdroid.travel.entity;

public class Place {
    private long id;
    private String title;
    private String address;
    private double latitude;
    private double longitde;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitde() {
        return longitde;
    }

    public void setLongitde(double longitde) {
        this.longitde = longitde;
    }
}
