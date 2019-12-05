package jestesmy.glodni.cateringi.dto;

public class CompanyDto {

    private int companyID;

    private String name;

    private String NIP;

    private String REGON;

    private String websiteAddress;

    private double averageRating;

    private int userID;

    public CompanyDto() { }

    public CompanyDto(int companyID, String name, String NIP, String REGON, String websiteAddress, double averageRating, int userID) {
        this.companyID = companyID;
        this.name = name;
        this.NIP = NIP;
        this.REGON = REGON;
        this.websiteAddress = websiteAddress;
        this.averageRating = averageRating;
        this.userID = userID;
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

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getREGON() {
        return REGON;
    }

    public void setREGON(String REGON) {
        this.REGON = REGON;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
