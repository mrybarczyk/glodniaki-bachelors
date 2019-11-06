package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "UserData")
public class UserData {

    @Id
    @Column(name = "UserDataID")
    private int userDataID;

    private String name;

    private String lastName;

    @OneToOne(cascade = CascadeType.REFRESH)
    @MapsId
    @JoinColumn(name = "UserDataID")
    private Accounts account;

    @OneToMany(mappedBy = "userData")
    private List<Favorites> favorites;

    @OneToMany(mappedBy = "userData")
    private List<Rates> rates;

    @OneToMany(mappedBy = "userData")
    private List<Orders> orders;

    public UserData() { }

    public UserData(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public int getUserDataID() {
        return userDataID;
    }

    public void setUserDataID(int userDataID) {
        this.userDataID = userDataID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public List<Favorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorites> favorites) {
        this.favorites = favorites;
    }

    public List<Rates> getRates() {
        return rates;
    }

    public void setRates(List<Rates> rates) {
        this.rates = rates;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
