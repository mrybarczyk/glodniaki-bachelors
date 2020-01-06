package jestesmy.glodni.cateringi.model;

import javax.persistence.*;

@Entity
@Table(name = "Rates")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rateID;

    @Column(nullable = false)
    private int rate;

    @ManyToOne
    @JoinColumn(name = "CompanyID", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "ClientID", nullable = false)
    private Client client;

    public Rate() {
    }

    public Rate(int rate) {
        this.rate = rate;
    }

    public int getRateID() {
        return rateID;
    }

    public void setRateID(int rateID) {
        this.rateID = rateID;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}