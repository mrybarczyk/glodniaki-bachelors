package jestesmy.glodni.cateringi.repositories;

import jestesmy.glodni.cateringi.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> {
}
