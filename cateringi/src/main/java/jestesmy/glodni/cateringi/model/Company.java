package jestesmy.glodni.cateringi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="Companies")
public class Company {
    @Id
    private int companyID;

    private String name;

    @Column(nullable = false)
    private String nip;

    @Column(nullable = false)
    private String regon;

    private String websiteAddress;

    private double averageRating;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="companyID")
    @MapsId
    @JsonIgnore
    User user;

    public Company(){
        this.averageRating = 0;
    }

    public Company(String name, String nip, String regon, String websiteAddress) {
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.websiteAddress = websiteAddress;
        this.averageRating = 0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
