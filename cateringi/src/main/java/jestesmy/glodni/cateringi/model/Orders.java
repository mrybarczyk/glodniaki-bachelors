package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @Column(name = "OrderID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int orderID;

    private Date orderDate;

    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "ServiceVariantID", referencedColumnName = "ServiceVariantID")
    private ServiceVariant serviceVariant;

    @ManyToOne
    @JoinColumn(name = "UserDataID")
    private UserData userData;

    @ManyToOne
    @JoinColumn(name = "AddressID", referencedColumnName = "AddressID")
    private Address address;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public ServiceVariant getServiceVariant() {
        return serviceVariant;
    }

    public void setServiceVariant(ServiceVariant serviceVariant) {
        this.serviceVariant = serviceVariant;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
