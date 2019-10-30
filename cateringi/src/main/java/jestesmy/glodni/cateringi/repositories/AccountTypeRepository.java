package jestesmy.glodni.cateringi.repositories;

import jestesmy.glodni.cateringi.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
}
