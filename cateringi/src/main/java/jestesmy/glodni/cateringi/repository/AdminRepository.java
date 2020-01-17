package jestesmy.glodni.cateringi.repository;


import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.domain.model.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    List<Admin> findByLastName(String lastName);

    Admin findByUser(User user);
}
