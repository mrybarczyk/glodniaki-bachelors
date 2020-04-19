package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.domain.model.Adress;
import jestesmy.glodni.cateringi.domain.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdressRepository extends CrudRepository<Adress, Integer> {
    List<Adress> findByUser(User user);
}
