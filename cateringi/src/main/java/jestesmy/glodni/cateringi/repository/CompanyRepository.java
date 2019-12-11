package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.model.Company;
import jestesmy.glodni.cateringi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
    List<Company> findByName(String name);

    Company findByUser(User user);
}
