package jestesmy.glodni.cateringi.repositories;

import jestesmy.glodni.cateringi.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {
}
