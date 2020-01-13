package jestesmy.glodni.cateringi.model;

import javax.persistence.*;

@Entity
@Table(name = "Rates")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rateID;

    @Column(nullable = false)
    private int rating;

    private String description;

    @ManyToOne
    @JoinColumn(name = "ServiceID", nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "ClientID", nullable = false)
    private Client client;

    public Rate() {
    }

    public Rate(int rating) {
        this.rating = rating;
    }

    public int getRateID() {
        return rateID;
    }

    public void setRateID(int rateID) {
        this.rateID = rateID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}