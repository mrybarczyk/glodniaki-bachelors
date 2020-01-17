package jestesmy.glodni.cateringi.domain.model;

import javax.persistence.*;

@Entity
@Table(name="ServiceVariant")
public class ServiceVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceVariantID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceID", nullable = false)
    private Service service;

    @Column(nullable = false)
    private int calories;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int dayNumber;

    private boolean active;

    public int getServiceVariantID() {
        return serviceVariantID;
    }

    public void setServiceVariantID(int serviceVariantID) {
        this.serviceVariantID = serviceVariantID;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
