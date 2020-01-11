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

    @Column(nullable = false)
    private int addressID;

    @Column(nullable = false)
    private Timestamp orderDate;

    @Column(nullable = false)
    private Timestamp deliveryDate;

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

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}

