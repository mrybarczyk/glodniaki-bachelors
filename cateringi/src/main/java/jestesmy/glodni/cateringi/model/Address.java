package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Address")
public class Address {

    @Id
    @Column(name = "AddressID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int addressID;

    private String city;

    private String street;

    private String postalCode;

    private String localNumber;

    @ManyToOne
    @JoinColumn(name = "AccountID", referencedColumnName = "AccountID")
    private Accounts accounts;

    @OneToMany(mappedBy = "address")
    private List<Orders> orders;

    public Address() { }

    public Address(String city, String street, String postalCode, String localNumber) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.localNumber = localNumber;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
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

    public String getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(String localNumber) {
        this.localNumber = localNumber;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
