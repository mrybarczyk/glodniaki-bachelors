package jestesmy.glodni.cateringi.repositories;

import jestesmy.glodni.cateringi.model.Rates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatesRepository extends JpaRepository<Rates, Integer> {
}
