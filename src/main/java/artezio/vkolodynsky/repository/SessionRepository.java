package artezio.vkolodynsky.repository;
import artezio.vkolodynsky.model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {

}
