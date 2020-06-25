package jestesmy.glodni.cateringi.repository;

import jestesmy.glodni.cateringi.domain.model.Message;
import jestesmy.glodni.cateringi.domain.model.Order;
import jestesmy.glodni.cateringi.domain.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findByFrom(User user);

    List<Message> findByTo(User user);

    List<Message> findByFromAndDeletedFrom(User user,boolean deleted);

    List<Message> findByToAndDeletedTo(User user,boolean deleted);
}
