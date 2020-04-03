package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.domain.model.Category;
import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
    List<Service> findByServiceName(String name);

    List<Service> findByCompany(Company company);

    List<Service> findByCompanyAndActiveIsTrue(Company company);

    List<Service> findAllByActiveIsTrue();

    int countAllByCategory(Category category);
}
