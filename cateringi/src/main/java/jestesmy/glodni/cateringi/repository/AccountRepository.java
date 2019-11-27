package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    public Account findByEmail(String email);
}
