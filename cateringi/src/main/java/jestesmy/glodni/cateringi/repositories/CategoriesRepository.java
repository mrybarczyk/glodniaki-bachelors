package jestesmy.glodni.cateringi.repositories;

import jestesmy.glodni.cateringi.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
}
