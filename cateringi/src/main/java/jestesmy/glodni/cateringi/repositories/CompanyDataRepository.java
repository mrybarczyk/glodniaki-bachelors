package jestesmy.glodni.cateringi.repositories;

import jestesmy.glodni.cateringi.model.CompanyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDataRepository extends JpaRepository<CompanyData, Integer> {
}
