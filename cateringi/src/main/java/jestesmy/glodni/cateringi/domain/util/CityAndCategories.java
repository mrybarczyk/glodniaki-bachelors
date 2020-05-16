package jestesmy.glodni.cateringi.domain.util;

import jestesmy.glodni.cateringi.domain.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

public class CityAndCategories {

    private String city;

    private List<Integer> categories;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
}
