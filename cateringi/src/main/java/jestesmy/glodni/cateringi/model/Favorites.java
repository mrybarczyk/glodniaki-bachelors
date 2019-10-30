package jestesmy.glodni.cateringi.model;

import javax.persistence.*;

@Entity
@Table(name = "Favorites")
public class Favorites {

    @Id
    @Column(name = "FavoriteID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int favoriteID;

    @ManyToOne
    @JoinColumn(name = "UserDataID")
    private UserData userData;

    @ManyToOne
    @JoinColumn(name = "ServiceID", referencedColumnName = "ServiceID")
    private Services service;

    public int getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(int favoriteID) {
        this.favoriteID = favoriteID;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }
}
