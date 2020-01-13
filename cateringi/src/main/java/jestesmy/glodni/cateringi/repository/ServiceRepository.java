package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.model.Company;
import jestesmy.glodni.cateringi.model.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
    List<Service> findByServiceName(String name);

    List<Service> findByCompany(Company company);

    @Query("SELECT COUNT(s.averageRating) from Service s where companyID = ?1 and s.averageRating != 0")
    int countByCompanyNotZero(int companyID);

    @Query("SELECT SUM(s.averageRating) from Service s where companyID = ?1")
    int getRatingByCompanyID(int companyID);
}
