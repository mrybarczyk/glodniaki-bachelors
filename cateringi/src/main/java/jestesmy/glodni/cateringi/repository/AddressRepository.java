package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.domain.model.Address;
import jestesmy.glodni.cateringi.domain.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    List<Address> findByUser(User user);
}
