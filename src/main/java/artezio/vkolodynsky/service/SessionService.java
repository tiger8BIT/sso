package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Session;
import java.util.List;
import java.util.Optional;

public interface SessionService {
    List<Session> findAll();
    Session save(Session value);
    void deleteByID(int id);
    Optional<Session> findByID(int id);
}
