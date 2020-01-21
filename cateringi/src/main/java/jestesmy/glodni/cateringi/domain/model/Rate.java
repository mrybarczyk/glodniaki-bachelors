package jestesmy.glodni.cateringi.domain.model;

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
    @JoinColumn(name = "CompanyID", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "ClientID", nullable = false)
    private Client client;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

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


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}