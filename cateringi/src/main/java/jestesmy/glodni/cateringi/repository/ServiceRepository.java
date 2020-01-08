package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
    List<Service> findByServiceName(String name);

    List findByCompany(Company company);
}
