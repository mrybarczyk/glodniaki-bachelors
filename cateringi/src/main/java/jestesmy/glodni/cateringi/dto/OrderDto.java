package jestesmy.glodni.cateringi.dto;

import java.sql.Timestamp;

public class OrderDto {

    private int orderID;

    private int clientID;

    private int addressID;

    private Timestamp orderDate;

    private Timestamp deliveryDate;

    public OrderDto() { }

    public OrderDto(int orderID, int clientID, int addressID, Timestamp orderDate, Timestamp deliveryDate) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.addressID = addressID;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) { this.orderID = orderID; }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
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
