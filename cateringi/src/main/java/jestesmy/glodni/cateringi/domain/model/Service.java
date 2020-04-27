package jestesmy.glodni.cateringi.domain.model;

import javax.persistence.*;

@Entity
@Table(name="Services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceID;

    @Column(nullable = false)
    private String serviceName;

    @ManyToOne
    @JoinColumn(name="categoryID", nullable = false)
    private Category category;

    private String description;

    @ManyToOne
    @JoinColumn(name="CompanyID", nullable = false)
    private Company company;

    private boolean active;

    private double minPrice;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
