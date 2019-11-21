package jestesmy.glodni.cateringi.repository;


import jestesmy.glodni.cateringi.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    List<Client> findByLastName(String lastName);
}
