package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
    public User addRole(int userId, Role role) throws NonTransientDataAccessException {
        Optional<User> user = repository.findById(userId);
        if (user.isPresent()) {
            List<Role> roles = user.get().getUserRoles();
            if (roles != null) {
                roles.add(role);
            } else {
                user.get().setUserRoles(new ArrayList<>(List.of(role)));
            }
            return repository.save(user.get());
        }
        else throw new NonTransientDataAccessException("User not found by id " + userId){};
    }
    @Override
    @Transactional
    public User deleteRole(int userId, Role role) throws NonTransientDataAccessException, NullPointerException {
        Optional<User> user = repository.findById(userId);
        if (user.isPresent()) {
            List<Role> roles = user.get().getUserRoles();
            if (roles != null) {
                if(roles.remove(role)) return repository.save(user.get());
                else throw new NonTransientDataAccessException("User haven't role with id " + role.getId()){};
            } else {
                throw new NullPointerException("User roles is null");
            }
        }
        else throw new NonTransientDataAccessException("User not found by id " + userId){};
    }

    @Override
    public Boolean existsById(int id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional
    public Boolean containsRole(int userId, Role role) throws NonTransientDataAccessException, NullPointerException {
        Optional<User> user = repository.findById(userId);
        if (user.isPresent()) {
            List<Role> roles = user.get().getUserRoles();
            if (roles != null) {
                return roles.contains(role);
            } else {
                throw new NullPointerException("User roles is null");
            }
        }
        else throw new NonTransientDataAccessException("User not found by id " + userId){};
    }


}