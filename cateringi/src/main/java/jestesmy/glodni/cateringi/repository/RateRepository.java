package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.model.Company;
import jestesmy.glodni.cateringi.model.Rate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RateRepository extends CrudRepository<Rate, Integer> {
    List<Rate> findByClient(Client client);
    List<Rate> findByCompany(Company company);
    List<Rate> findByClientAndCompany(Client client, Company company);
    boolean existsByClientAndCompany(Client client, Company company);
    int countAllByCompany(Company company);

    @Query("SELECT SUM(r.rate) from Rate r where CompanyID = ?1")
    int getRatingByCompanyID(int companyID);
}