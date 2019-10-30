package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Categories")
public class Categories {

    @Id
    @Column(name = "CategoryID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int categoryID;

    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<ServiceCategory> serviceCategory;

    public Categories() { }

    public Categories(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ServiceCategory> getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(List<ServiceCategory> serviceCategory) {
        this.serviceCategory = serviceCategory;
    }
}
