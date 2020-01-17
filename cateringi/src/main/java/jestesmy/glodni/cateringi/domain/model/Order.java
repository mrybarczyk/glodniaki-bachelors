package jestesmy.glodni.cateringi.domain.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @ManyToOne
    @JoinColumn(name = "ClientID", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "CompanyID", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "ServiceVariantID", nullable = false)
    private ServiceVariant serviceVariant;

    private String address;

    private String serviceName;

    private String serviceDescription;

    @Column(nullable = false)
    private Timestamp orderDate;

    private Timestamp fromDate;

    private Timestamp toDate;

    private boolean isPaid;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) { this.orderID = orderID; }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public ServiceVariant getServiceVariant() {
        return serviceVariant;
    }

    public void setServiceVariant(ServiceVariant serviceVariant) {
        this.serviceVariant = serviceVariant;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
