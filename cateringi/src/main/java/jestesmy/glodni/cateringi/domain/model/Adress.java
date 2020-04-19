package jestesmy.glodni.cateringi.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "Adress")
public class Adress {

    @Id
    private int adressID;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private int apartmentNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private int zipCode;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    public int getAdressID() {
        return adressID;
    }

    public void setAdressID(int adressID) {
        this.adressID = adressID;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartamentNumber) {
        this.apartmentNumber = apartamentNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
