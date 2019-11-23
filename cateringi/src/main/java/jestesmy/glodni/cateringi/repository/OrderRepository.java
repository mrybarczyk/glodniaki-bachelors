package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByClientID(int clientID);
}
