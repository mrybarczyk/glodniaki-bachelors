package jestesmy.glodni.cateringi.model.util;

import jestesmy.glodni.cateringi.model.Company;
import jestesmy.glodni.cateringi.model.User;

public class CompanyWithUser{
    private String userName;
    private String email;
    private String companyName;
    private String websiteAddress;
    private String phoneNumber;
    private double averageRating;

    public CompanyWithUser(Company company, User user) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.companyName = company.getName();
        this.websiteAddress = company.getWebsiteAddress();
        this.averageRating = company.getAverageRating();
    }

    public CompanyWithUser() {}

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
