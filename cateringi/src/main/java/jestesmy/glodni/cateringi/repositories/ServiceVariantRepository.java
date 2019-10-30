package jestesmy.glodni.cateringi.repositories;

import jestesmy.glodni.cateringi.model.ServiceVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceVariantRepository extends JpaRepository<ServiceVariant, Integer> {
}
