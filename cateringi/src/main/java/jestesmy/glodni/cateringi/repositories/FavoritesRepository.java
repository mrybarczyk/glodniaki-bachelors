package jestesmy.glodni.cateringi.repositories;

import jestesmy.glodni.cateringi.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {
}
