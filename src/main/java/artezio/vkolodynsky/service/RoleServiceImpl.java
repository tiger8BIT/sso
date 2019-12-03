package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repository;

    @Override
    public List<Role> findAll() {
        return (List<Role>) repository.findAll();
    }

    @Override
    public Role save(Role value) {
        return repository.save(value);
    }

    @Override
    public void deleteByID(int id) {
        repository.deleteById(id);
    }

    @Override
    public Role findByID(int id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Role> findByApp(App app) {
        return repository.findByApp(app);
    }

    @Override
    public List<Role> findByUserAndApp(User user, App app) {
        return repository.findByUsersAndApp(List.of(user), app);
    }
}
