package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Role;
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
    public void deleteByID(int id) {
        repository.deleteById(id);
    }

    @Override
    public User findByID(int id) {
        return repository.findById(id).get();
    }

    @Override
    public User getByLogin(String login) {
        return repository.getByLogin(login);
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        return repository.getByLoginAndPassword(login,password);
    }

    @Override
    public List<User> findByUserRole(Role role) {
        return repository.findByUserRoles(List.of(role));
    }
}
