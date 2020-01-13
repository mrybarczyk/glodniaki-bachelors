package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByClient(Client client);

    List<Order> findByCompany(Company company);

    @Query("SELECT COUNT(orderID) FROM Order")
    int countAll();
}
