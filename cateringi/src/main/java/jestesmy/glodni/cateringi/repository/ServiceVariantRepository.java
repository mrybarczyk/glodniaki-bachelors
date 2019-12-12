package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.model.Service;
import jestesmy.glodni.cateringi.model.ServiceVariant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceVariantRepository extends CrudRepository<ServiceVariant, Integer> {
    List findByService(Service service);
}
