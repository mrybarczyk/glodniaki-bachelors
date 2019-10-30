package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ServiceType")
public class ServiceType {

    @Id
    @Column(name = "ServiceTypeID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int serviceTypeID;

    private String serviceTypeName;

    @OneToMany(mappedBy = "serviceType")
    private List<Services> services;

    public ServiceType() { }

    public ServiceType(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public int getServiceTypeID() {
        return serviceTypeID;
    }

    public void setServiceTypeID(int serviceTypeID) {
        this.serviceTypeID = serviceTypeID;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }
}
