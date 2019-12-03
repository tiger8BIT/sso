package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Session;
import java.util.List;

public interface SessionService {
    List<Session> findAll();
    Session save(Session value);
    void deleteByID(int id);
    Session findByID(int id);
}
