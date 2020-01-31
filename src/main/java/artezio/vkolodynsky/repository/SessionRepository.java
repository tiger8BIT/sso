package artezio.vkolodynsky.repository;
import artezio.vkolodynsky.model.Session;
import artezio.vkolodynsky.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session, Integer> {
    Optional<Session> findByUser(User user);
}
