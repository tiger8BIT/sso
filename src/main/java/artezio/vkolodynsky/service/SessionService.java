package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Session;
import java.util.List;

public interface SessionService {
    List<Session> findAll();
    Session save(Session value);
    void deleteByID(long id);
    Session findByID(long id);
}
