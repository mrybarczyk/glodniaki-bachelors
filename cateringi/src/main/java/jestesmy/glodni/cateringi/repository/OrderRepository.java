package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.domain.model.Client;
import jestesmy.glodni.cateringi.domain.model.Company;
import jestesmy.glodni.cateringi.domain.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByClient(Client client);

    List<Order> findByCompany(Company company);
}
