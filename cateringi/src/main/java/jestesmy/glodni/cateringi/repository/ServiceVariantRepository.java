package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.domain.model.Service;
import jestesmy.glodni.cateringi.domain.model.ServiceVariant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceVariantRepository extends CrudRepository<ServiceVariant, Integer> {
    List findByService(Service service);
}
