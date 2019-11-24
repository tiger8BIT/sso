package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    public User save(User value) {
        return repository.save(value);
    }

    @Override
    public void deleteByID(long id) {
        repository.deleteById(id);
    }

    @Override
    public User findByID(long id) {
        return repository.findById(id).get();
    }
}
