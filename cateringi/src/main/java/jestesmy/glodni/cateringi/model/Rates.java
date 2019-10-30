package jestesmy.glodni.cateringi.model;

import javax.persistence.*;

@Entity
@Table(name = "Rates")
public class Rates {

    @Id
    @Column(name = "RateID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int rateID;

    private int rate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "CompanyDataID")
    private CompanyData companyData;

    @ManyToOne
    @JoinColumn(name = "UserDataID")
    private UserData userData;

    public Rates() {}

    public Rates(int rate, String description) {
        this.rate = rate;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompanyData getCompanyData() {
        return companyData;
    }

    public void setCompanyData(CompanyData companyData) {
        this.companyData = companyData;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
