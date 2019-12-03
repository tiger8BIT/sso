package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Session;
import artezio.vkolodynsky.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepository repository;

    @Override
    public List<Session> findAll() {
        return (List<Session>) repository.findAll();
    }

    @Override
    public Session save(Session value) {
        return repository.save(value);
    }

    @Override
    public void deleteByID(int id) {
        repository.deleteById(id);
    }

    @Override
    public Session findByID(int id) {
        return repository.findById(id).get();
    }
}
