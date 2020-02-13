package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Session;
import artezio.vkolodynsky.model.data.SessionData;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    List<Session> findAll();
    Session save(SessionData data, String ipAddress);
    void deleteByID(int id);
    Optional<Session> findByID(int id);
}
