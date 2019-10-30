package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Services")
public class Services {

    @Id
    @Column(name = "ServiceID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int serviceID;

    private String serviceName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "ServiceTypeID", referencedColumnName = "ServiceTypeID")
    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "CompanyDataID")
    private CompanyData companyData;

    @OneToMany(mappedBy = "service")
    private List<Favorites> favorites;

    @OneToMany(mappedBy = "service")
    private List<ServiceVariant> serviceVariant;

    @OneToMany(mappedBy = "service")
    private List<ServiceCategory> serviceCategory;

    public Services() { }

    public Services(String serviceName, String description) {
        this.serviceName = serviceName;
        this.description = description;
    }

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

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public CompanyData getCompanyData() {
        return companyData;
    }

    public void setCompanyData(CompanyData companyData) {
        this.companyData = companyData;
    }

    public List<Favorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorites> favorites) {
        this.favorites = favorites;
    }

    public List<ServiceVariant> getServiceVariant() {
        return serviceVariant;
    }

    public void setServiceVariant(List<ServiceVariant> serviceVariant) {
        this.serviceVariant = serviceVariant;
    }

    public List<ServiceCategory> getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(List<ServiceCategory> serviceCategory) {
        this.serviceCategory = serviceCategory;
    }
}
