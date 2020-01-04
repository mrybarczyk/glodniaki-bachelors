package jestesmy.glodni.cateringi.repository;


import jestesmy.glodni.cateringi.model.Admin;
import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    List<Admin> findByLastName(String lastName);

    Admin findByUser(User user);
}
