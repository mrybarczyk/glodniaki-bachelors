package jestesmy.glodni.cateringi.domain.model;

import jestesmy.glodni.cateringi.domain.model.Client;

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

    @Column(nullable = false)
    private int addressID;

    @Column(nullable = false)
    private Timestamp orderDate;

    private Timestamp fromDate;

    private Timestamp toDate;

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

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
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
}

