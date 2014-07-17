package br.com.epicdroid.travel.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.location.Address;

@SuppressWarnings("serial")
public class AddressDTO implements Serializable {

    private String street;
    private String number;
    private String district;
    private String city;
    private String state;
    private String cep;
    private String country;
    private double latitude;
    private double longitude;

    public AddressDTO() {
        super();
    }

    public static AddressDTO fromAddressToAddressDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        if (address.getThoroughfare() != null) {
            dto.setStreet(address.getThoroughfare());
        }
        if (address.getFeatureName() != null) {
            dto.setNumber(address.getFeatureName());
        }
        if (address.getSubLocality() != null) {
            dto.setDistrict(address.getSubLocality());
        }
        if (address.getSubAdminArea() != null) {
            dto.setCity(address.getSubAdminArea());
        }
        if (address.getAdminArea() != null) {
            dto.setState(address.getAdminArea());
        }
        if (address.getPostalCode() != null) {
            dto.setCep(address.getPostalCode());
        }
        if (address.getCountryName() != null) {
            dto.setCountry(address.getCountryName());
        }
        dto.setLatitude(address.getLatitude());
        dto.setLongitude(address.getLongitude());
        return dto;
    }

    public static List<AddressDTO> fromListAddressToListAddressDTO(List<Address> address) {
        List<AddressDTO> list = new ArrayList<AddressDTO>();
        for (Address item : address) {
            list.add(fromAddressToAddressDTO(item));
        }
        return list;
    }

    public String getStreet() {
        if (street == null) {
            street = "";
        }
        return street;
    }

    public String getNumber() {
        String numberFormated = "";
        if (number != null) {
            numberFormated = ", " + number;
        }
        return numberFormated;
    }

    public String getState() {
        String stateFormated = "";
        if (state != null) {
            stateFormated = ", " + state;
        }
        return stateFormated;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        String districtFormated = "";
        if (district != null) {
            districtFormated = ", " + district;
        }
        return districtFormated;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        String cityFormated = "";
        if (city != null) {
            cityFormated = ", " + city;
        }
        return cityFormated;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCep() {
        String cepFormated = "";
        if (cep != null) {
            cepFormated = ", " + cep;
        }
        return cepFormated;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCountry() {
        String countryFormated = "";
        if (country != null) {
            countryFormated = ", " + country;
        }
        return countryFormated;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return getStreet() +
                getDistrict() +
                getCity() +
                getState() +
                getCep() +
                getCountry();
    }
}
