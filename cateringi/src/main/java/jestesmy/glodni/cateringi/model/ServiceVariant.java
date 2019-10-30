package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ServiceVariant")
public class ServiceVariant {

    @Id
    @Column(name = "ServiceVariantID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int serviceVariantID;

    private int calories;

    private float pricePerPerson;

    private int dayNumber;

    private Date deliveryTime;

    @ManyToOne
    @JoinColumn(name = "ServiceID", referencedColumnName = "ServiceID")
    private Services service;

    @OneToMany(mappedBy = "serviceVariant")
    private List<Orders> orders;

    public ServiceVariant() { }

    public ServiceVariant(int calories, float pricePerPerson, int dayNumber, Date deliveryTime) {
        this.calories = calories;
        this.pricePerPerson = pricePerPerson;
        this.dayNumber = dayNumber;
        this.deliveryTime = deliveryTime;
    }

    public int getServiceVariantID() {
        return serviceVariantID;
    }

    public void setServiceVariantID(int serviceVariantID) {
        this.serviceVariantID = serviceVariantID;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public float getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(float pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
