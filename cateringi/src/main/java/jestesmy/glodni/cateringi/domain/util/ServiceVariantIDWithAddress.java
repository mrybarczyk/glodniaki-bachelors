package jestesmy.glodni.cateringi.domain.util;

import javax.persistence.Column;

public class ServiceVariantIDWithAddress {

    private int selectedServiceVariantID;

    private String address;

    private String city;

    private String street;

    private String apartmentNumber;

    private String postalCode;

    private String companyName;

    private boolean save;

    public int getSelectedServiceVariantID() {
        return selectedServiceVariantID;
    }

    public void setSelectedServiceVariantID(int selectedServiceVariantID) {
        this.selectedServiceVariantID = selectedServiceVariantID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public String fullAddress() {
        String fullAddress;
        if(apartmentNumber != ""){
            fullAddress = street + "/" + apartmentNumber + ", " + postalCode + " " + city;
        } else {
            fullAddress = street + ", " + postalCode + " " + city;
        }

        if(companyName != ""){
            fullAddress += " Firma:" + companyName;
        }
        return fullAddress;
    }
}
