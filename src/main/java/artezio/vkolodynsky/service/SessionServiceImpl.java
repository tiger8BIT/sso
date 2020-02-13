package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.RemoteAddress;
import artezio.vkolodynsky.model.Session;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.SessionData;
import artezio.vkolodynsky.repository.RemoteAddressRepository;
import artezio.vkolodynsky.repository.SessionRepository;
import artezio.vkolodynsky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepository repository;
    @Autowired
    private RemoteAddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Session> findAll() {
        return (List<Session>) repository.findAll();
    }

    @Override
    @Transactional
    public Session save(SessionData data, String ipAddress) {
        Session session = Session.from(data);
        User user = userRepository.findById(data.getUserId()).orElseThrow();
        session.setUser(user);
        session = repository.save(session);
        RemoteAddress address = addressRepository.save(new RemoteAddress(ipAddress, session));
        session.getRemoteAddresses().add(address);
        return session;
    }

    @Override
    public void deleteByID(int id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Session> findByID(int id) {
        return repository.findById(id);
    }
}
