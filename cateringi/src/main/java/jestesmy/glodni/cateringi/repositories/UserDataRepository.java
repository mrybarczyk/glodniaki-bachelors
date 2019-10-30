package jestesmy.glodni.cateringi.repositories;

import jestesmy.glodni.cateringi.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
}
