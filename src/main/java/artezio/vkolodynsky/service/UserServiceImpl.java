package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    public Optional<User> findByID(int id) {
            return repository.findById(id);
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

    @Override
    @Transactional
    public Optional<User> addRole(int userId, Role role) {
        Optional<User> user = repository.findById(userId);
        if (user.isPresent()) {
            if (user.get().getUserRoles() != null) {
                user.get().getUserRoles().add(role);
            } else {
                user.get().setUserRoles(new ArrayList<>(List.of(role)));
            }
            return Optional.of(repository.save(user.get()));
        }
        else {
            return user;
        }
    }
}
