package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.domain.model.Client;
import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.Rate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RateRepository extends CrudRepository<Rate, Integer> {
    List<Rate> findByClient(Client client);
    List<Rate> findByCompany(Company company);
    List<Rate> findByClientAndCompany(Client client, Company company);
    boolean existsByClientAndCompany(Client client, Company company);
    int countAllByCompany(Company company);

    @Query("SELECT coalesce(SUM(r.rating), 0) from Rate r where companyID = ?1")
    int getRatingByCompanyID(int serviceID);

    @Query("SELECT COUNT(r.rating) from Rate r where companyID = ?1 and r.rating != 0")
    int countByCompanyNotZero(int companyID);
}