package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByEmail(String email);

    public User findByUserName(String accountName);
}
