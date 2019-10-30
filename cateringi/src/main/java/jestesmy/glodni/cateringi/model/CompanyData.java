package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CompanyData")
public class CompanyData {

    @Id
    @Column(name = "CompanyDataID")
    private int companyDataID;

    private String name;

    private String nip;

    private String regon;

    private String websiteAddress;

    private float averageRating;

    @OneToOne(cascade = CascadeType.REFRESH)
    @MapsId
    @JoinColumn(name = "CompanyDataID")
    private Accounts account;

    @OneToMany(mappedBy = "companyData")
    private List<Messages> messages;

    @OneToMany(mappedBy = "companyData")
    private List<Services> services;

    @OneToMany(mappedBy = "companyData")
    private List<Rates> rates;

    public CompanyData() { }

    public CompanyData(String name, String nip, String regon) {
        this.name = name;
        this.nip = nip;
        this.regon = regon;
    }

    public int getCompanyDataID() {
        return companyDataID;
    }

    public void setCompanyDataID(int companyDataID) {
        this.companyDataID = companyDataID;
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

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

//    public Accounts getAccount() {
//        return account;
//    }
//
//    public void setAccount(Accounts account) {
//        this.account = account;
//    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    public List<Rates> getRates() {
        return rates;
    }

    public void setRates(List<Rates> rates) {
        this.rates = rates;
    }
}
