package jestesmy.glodni.cateringi.model;

import javax.persistence.*;

@Entity
@Table(name="Services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceID;

    @Column(nullable = false)
    private String serviceName;

    private String description;

    private double averageRating;

    public Service(){
        this.averageRating = 0.0;
    }

    public Service(String serviceName, String description) {
        this.serviceName = serviceName;
        this.description = description;
        this.averageRating = 0.0;
    }

    @ManyToOne
    @JoinColumn(name="CompanyID", nullable = false)
    private Company company;

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompany() { return company; }

    public void setCompany(Company company) { this.company = company; }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
