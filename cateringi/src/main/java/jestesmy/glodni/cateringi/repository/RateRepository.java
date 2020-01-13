package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.model.Rate;
import jestesmy.glodni.cateringi.model.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RateRepository extends CrudRepository<Rate, Integer> {
    List<Rate> findByClient(Client client);
    List<Rate> findByService(Service service);
    List<Rate> findByClientAndService(Client client, Service service);
    boolean existsByClientAndService(Client client, Service service);
    int countAllByService(Service service);

    @Query("SELECT coalesce(SUM(r.rating), 0) from Rate r where serviceID = ?1")
    int getRatingByServiceID(int serviceID);
}