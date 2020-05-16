package jestesmy.glodni.cateringi.domain.util;

import jestesmy.glodni.cateringi.domain.model.Category;

import java.util.List;

public class CityAndCategories {

    private String city;

    private List<Category> categories;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
