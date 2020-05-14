package jestesmy.glodni.cateringi.domain.model;

import javax.persistence.*;

@Entity
@Table(name="Adresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressID;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    private String apartmentNumber;

    @Column(nullable = false)
    private String postalCode;

    private String companyName;

    @ManyToOne
    @JoinColumn(name="UserID", nullable = false)
    private User user;

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int adressID) {
        this.addressID = adressID;
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

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
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
