package jestesmy.glodni.cateringi.model;

import javax.persistence.*;

@Entity
@Table(name = "ServiceCategory")
public class ServiceCategory {

    @Id
    @Column(name = "ServiceCategoryID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int serviceCategoryID;

    @ManyToOne
    @JoinColumn(name = "CategoryID", referencedColumnName = "CategoryID")
    private Categories category;

    @ManyToOne
    @JoinColumn(name = "ServiceID", referencedColumnName = "ServiceID")
    private Services service;

    public Categories getCategory() {
        return category;
    }

    public int getServiceCategoryID() {
        return serviceCategoryID;
    }

    public void setServiceCategoryID(int serviceCategoryID) {
        this.serviceCategoryID = serviceCategoryID;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }
}
