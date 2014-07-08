package br.com.epicdroid.travel.entity;

public class Place {
    private long id;
    private String title;
    private String address;
    private long latitude;
    private long longitde;

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

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitde() {
        return longitde;
    }

    public void setLongitde(long longitde) {
        this.longitde = longitde;
    }
}
