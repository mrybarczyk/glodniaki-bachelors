package jestesmy.glodni.cateringi.repositories;

import jestesmy.glodni.cateringi.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer> {
}
